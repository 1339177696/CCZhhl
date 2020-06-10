package com.hulian.oa.socket.Receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hulian.oa.activity.MainActivity;

/**
 * Created by qgl on 2019/12/4 17:20.
 */
public class MyReceiver extends BroadcastReceiver {
    private Message message;

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收MainActivity传过来的数据
        Toast.makeText(context, intent.getStringExtra("hello"), Toast.LENGTH_SHORT).show();
        //调用Message接口的方法
        message.getMsg(" world");
    }

    interface Message {
        public void getMsg(String str);
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}