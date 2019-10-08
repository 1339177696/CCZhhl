package com.hulian.oa.net;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hulian.oa.APP;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 创建： zb
 * 描述：专门处理JSON数据的回调响应
 */

public class CommonJsonCallback implements Callback {

  /**
   * errorCode是根据接口返回的标识 实际根据自己接口返回为准
   */
  protected final String RESULT_CODE = "code";
  protected final int RESULT_CODE_VALUE = 0;
  /**
   * errorMsg字段提示信息，实际根据自己接口返回为准
   */
  protected final String ERROR_MSG = "errorMsg";

  protected final String NETWORK_MSG = "请求失败";
  protected final String JSON_MSG = "解析失败";

  /**
   * 自定义异常类型
   */
  protected final int NETWORK_ERROR = -1; //网络失败
  protected final int JSON_ERROR = -2; //解析失败
  protected final int OTHER_ERROR = -3; //未知错误
  protected final int TIMEOUT_ERROR = -4; //请求超时


  private Handler mDeliveryHandler; //进行消息的转发
  private ResponseCallback mListener;
  private Class<?> mClass;

  public CommonJsonCallback(ResposeDataHandle handle) {
    this.mListener = handle.mListener;
    this.mClass = handle.mClass;
    this.mDeliveryHandler = new Handler(Looper.getMainLooper());
  }

  /**
   * 请求失败的处理
   * @param call
   * @param e
   */
  @Override
  public void onFailure(@NonNull Call call, @NonNull final IOException e) {
    Log.e("TAG","请求失败=" + e.getMessage());
    mDeliveryHandler.post(new Runnable() {
      @Override
      public void run() {
        if (!Utils.isConnected(APP.context)) {
          mListener.onFailure(new OkHttpException(NETWORK_ERROR, "请检查网络"));
        } else if (e instanceof SocketTimeoutException) {
          //判断超时异常
          mListener.onFailure(new OkHttpException(TIMEOUT_ERROR, "请求超时"));
        } else if (e instanceof ConnectException) {
          //判断超时异常
          mListener.onFailure(new OkHttpException(OTHER_ERROR, "请求服务器失败"));
        } else {
          mListener.onFailure(new OkHttpException(NETWORK_ERROR, e.getMessage()));
        }

      }
    });
  }

  /**
   * 请求成功的处理
   * 回调在主线程
   */
  @Override
  public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
    final String result = response.body().string();
    mDeliveryHandler.post(new Runnable() {
      @Override
      public void run() {
        handleResponse(result);
      }
    });
  }

  /**
   * 处理Http成功的响应
   * @param responseObj
   */
  private void handleResponse(Object responseObj) {
    if (responseObj == null && responseObj.toString().trim().equals("")) {
      mListener.onFailure(new OkHttpException(NETWORK_ERROR, NETWORK_MSG));
      return;
    }

    try {
      JSONObject result = new JSONObject(responseObj.toString());
      if (result.has(RESULT_CODE)) {
        //从JSON对象中取出我们的响应码，如果为0，则是正确的响应 (实际情况按你们接口文档)
        if (result.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {
          if (mClass == null) {
            mListener.onSuccess(responseObj);
          } else {
            //需要转化为实体对象
            Gson gson = new GsonBuilder().serializeNulls().create();

            Object json = new JSONTokener(result.getString("data")).nextValue();
            Object obj;
            if(json instanceof JSONObject) {
               obj = gson.fromJson(String.valueOf(result.getJSONObject("data")), mClass);
            }
            else {
//              obj = gson.fromJson(String.valueOf(result.getJSONArray("data")),   new TypeToken<List<mClass>>() {
//              }.getType());
              obj=null;
            }
            if (obj != null) {
              mListener.onSuccess(obj);
            } else {
              mListener.onFailure(new OkHttpException(JSON_ERROR, JSON_MSG));
            }
          }

        }
        else if(result.getInt(RESULT_CODE)==500){
          mListener.onSuccess(responseObj);
        }
        else { //将服务端返回的异常回调到应用层去处理
          mListener.onFailure(new OkHttpException(OTHER_ERROR, result.get(ERROR_MSG) + ""));
          Log.e("TAG","onResponse处理失败");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
      Log.e("TAG","onResponse处理失败=" + e.getMessage());
    }
  }

}
