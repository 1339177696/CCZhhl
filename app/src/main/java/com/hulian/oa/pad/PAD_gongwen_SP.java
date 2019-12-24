package com.hulian.oa.pad;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.agency.l_fragment.L_UpcomFragment;
import com.hulian.oa.bean.DocumentImage;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.document.DocumentLotusActivity;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_ApprovedFragment;
import com.hulian.oa.work.file.admin.activity.document.l_fragment.L_PendFragment;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import cn.forward.androids.utils.ImageUtils;
import cn.forward.androids.utils.Util;
import cn.forward.androids.views.MaskImageView;
import cn.forward.androids.views.STextView;
import cn.hzw.doodle.DoodleBitmap;
import cn.hzw.doodle.DoodleColor;
import cn.hzw.doodle.DoodleOnTouchGestureListener;
import cn.hzw.doodle.DoodleParams;
import cn.hzw.doodle.DoodlePen;
import cn.hzw.doodle.DoodleShape;
import cn.hzw.doodle.DoodleText;
import cn.hzw.doodle.DoodleTouchDetector;
import cn.hzw.doodle.DoodleView;
import cn.hzw.doodle.IDoodleListener;
import cn.hzw.doodle.core.IDoodle;
import cn.hzw.doodle.core.IDoodleColor;
import cn.hzw.doodle.core.IDoodlePen;
import cn.hzw.doodle.core.IDoodleSelectableItem;
import cn.hzw.doodle.core.IDoodleShape;
import cn.hzw.doodle.core.IDoodleTouchDetector;
import cn.hzw.doodle.dialog.ColorPickerDialog;
import cn.hzw.doodle.dialog.DialogController;
import cn.hzw.doodle.imagepicker.ImageSelectorView;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

import static com.netease.nim.uikit.common.util.media.BitmapUtil.calculateInSampleSize;

/**
 * Created by qgl on 2019/8/31 9:11.
 */
public class PAD_gongwen_SP extends BaseActivity implements View.OnClickListener {
    private FrameLayout mFrameLayout;
    private DoodleView mDoodleView;
    private IDoodle mDoodle;
    public final static int DEFAULT_COPY_SIZE = 20; // 默认仿制大小
    public final static int DEFAULT_TEXT_SIZE = 17; // 默认文字大小
    public final static int DEFAULT_BITMAP_SIZE = 80; // 默认贴图大小

    public static final int RESULT_ERROR = -111; // 出现错误
    public static final String KEY_PARAMS = "key_doodle_params";
    public static final String KEY_IMAGE_PATH = "key_image_path";


    public String urlStr;
//    private TextView mPaintSizeView;

//    private View mBtnHidePanel, mSettingsPanel;
//    private View mSelectedTextEditContainer;
//    private View mBtnColor;
//    private SeekBar mEditSizeSeekBar;

    private AlphaAnimation mViewShowAnimation, mViewHideAnimation; // view隐藏和显示时用到的渐变动画

    private DoodleParams mDoodleParams;

    // 触摸屏幕超过一定时间才判断为需要隐藏设置面板
    private Runnable mHideDelayRunnable;
    // 触摸屏幕超过一定时间才判断为需要显示设置面板
    private Runnable mShowDelayRunnable;

    private DoodleOnTouchGestureListener mTouchGestureListener;
    private Map<IDoodlePen, Float> mPenSizeMap = new HashMap<>(); //保存每个画笔对应的最新大小


    ////////
    private MaskImageView doodle_btn_back;
    private STextView doodle_txt_title;
    private MaskImageView doodle_btn_rotate;
    private MaskImageView mBtnHidePanel;
    private MaskImageView doodle_btn_finish;
    //////////////////
    private RelativeLayout mSettingsPanel;
    private ImageView btn_pen_hand;
    private ImageView btn_pen_copy;
    private ImageView btn_pen_eraser;
    private ImageView btn_pen_text;
    private ImageView btn_pen_bitmap;
    private ImageView btn_clear;
    private ImageView btn_undo;
    private ImageView btn_zoomer;  //放大缩小
/////////


    private LinearLayout bar_shape_mode;
    private ImageView btn_hand_write;
    private ImageView btn_arrow;
    private ImageView btn_line;
    private ImageView btn_holl_circle;
    private ImageView btn_fill_circle;
    private ImageView btn_holl_rect;
    private ImageView btn_fill_rect;
    private ImageView doodle_btn_brush_edit;
    /////////////
    private LinearLayout doodle_color_container;
    private FrameLayout btn_set_color_container;
    private ImageView mBtnColor;
    private SeekBar mEditSizeSeekBar;

    private TextView mPaintSizeView;

    private LinearLayout mSelectedTextEditContainer;
    private TextView doodle_selectable_edit;
    private TextView doodle_selectable_bottom;
    private TextView doodle_selectable_top;
    private TextView doodle_selectable_remove;
    private int approveflag;
    private String rejectedText;

    private ImageView iv_back;
    private String offId;
    List<DocumentImage> memberList = new ArrayList<>();


    private RadioButton tv_disagree;
    private RadioButton tv_agree;

    private TextView tv_pd_filename;
    private TextView tv_pd_number;
    private String path_x = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final OkHttpClient client = new OkHttpClient();


    private ImageView lift;
    private ImageView riht;
    private int page = 0;
    private static Bitmap bitmap;

    private List<Padbean>padbeans = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pad_gongwen_shenpi);
        loadDialog.show();
        lift = findViewById(R.id.lift);
        riht = findViewById(R.id.riht);
        lift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDoodle.save();
                if (page >0) {
                    page--;
                    startData();
                } else {
                    Toast.makeText(PAD_gongwen_SP.this, "第一张图片", Toast.LENGTH_SHORT).show();
                }

            }
        });
        riht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDoodle.save();
                if (page < padbeans.size() - 1) {
                    page++;
                    Log.e("页数", page + "");
                    startData();
                } else {
                    Log.e("页数没了", page + "");
                    Toast.makeText(PAD_gongwen_SP.this, "没图了", Toast.LENGTH_SHORT).show();
                }

            }
        });

        offId = getIntent().getStringExtra("offId");
        mFrameLayout = (FrameLayout) findViewById(R.id.doodle_container);
        tv_pd_filename = (TextView) findViewById(R.id.tv_pd_filename);
        tv_pd_number = (TextView) findViewById(R.id.tv_pd_number);
        tv_pd_filename.setOnClickListener(this);

        doodle_btn_back = (MaskImageView) findViewById(R.id.doodle_btn_back);  /*返回箭头*/
        doodle_txt_title = (STextView) findViewById(R.id.doodle_txt_title);
        doodle_btn_rotate = (MaskImageView) findViewById(R.id.doodle_btn_rotate);
        mBtnHidePanel = (MaskImageView) findViewById(R.id.doodle_btn_hide_panel);
        doodle_btn_finish = (MaskImageView) findViewById(R.id.doodle_btn_finish);
        mSettingsPanel = (RelativeLayout) findViewById(R.id.doodle_panel);
        btn_pen_hand = (ImageView) findViewById(R.id.btn_pen_hand);
        btn_pen_copy = (ImageView) findViewById(R.id.btn_pen_copy);
        btn_pen_eraser = (ImageView) findViewById(R.id.btn_pen_eraser);
        btn_pen_text = (ImageView) findViewById(R.id.btn_pen_text);
        btn_pen_bitmap = (ImageView) findViewById(R.id.btn_pen_bitmap);
        btn_clear = (ImageView) findViewById(R.id.btn_clear);
        btn_undo = (ImageView) findViewById(R.id.btn_undo);
        btn_zoomer = (ImageView) findViewById(R.id.btn_zoomer);
        bar_shape_mode = (LinearLayout) findViewById(R.id.bar_shape_mode);
        btn_hand_write = (ImageView) findViewById(R.id.btn_hand_write);
        btn_arrow = (ImageView) findViewById(R.id.btn_arrow);
        btn_line = (ImageView) findViewById(R.id.btn_line);
        btn_holl_circle = (ImageView) findViewById(R.id.btn_holl_circle);
        btn_fill_circle = (ImageView) findViewById(R.id.btn_fill_circle);
        btn_holl_rect = (ImageView) findViewById(R.id.btn_holl_rect);
        btn_fill_rect = (ImageView) findViewById(R.id.btn_fill_rect);
        doodle_btn_brush_edit = (ImageView) findViewById(R.id.doodle_btn_brush_edit);
        doodle_color_container = (LinearLayout) findViewById(R.id.doodle_color_container);
        btn_set_color_container = (FrameLayout) findViewById(R.id.btn_set_color_container);
        mBtnColor = (ImageView) findViewById(R.id.btn_set_color);
        mEditSizeSeekBar = (SeekBar) findViewById(R.id.doodle_seekbar_size);
//        mEditSizeSeekBar.setProgress(4);
//        mEditSizeSeekBar.setMax(4);
        mPaintSizeView = (TextView) findViewById(R.id.paint_size_text);
        mPaintSizeView.setText(mEditSizeSeekBar.getProgress() + 1 + "");
        mSelectedTextEditContainer = (LinearLayout) findViewById(R.id.doodle_selectable_edit_container);
        doodle_selectable_edit = (TextView) findViewById(R.id.doodle_selectable_edit);
        doodle_selectable_bottom = (TextView) findViewById(R.id.doodle_selectable_bottom);
        doodle_selectable_top = (TextView) findViewById(R.id.doodle_selectable_top);
        doodle_selectable_remove = (TextView) findViewById(R.id.doodle_selectable_remove);
        doodle_selectable_edit.setOnClickListener(this);
        doodle_selectable_bottom.setOnClickListener(this);
        doodle_selectable_top.setOnClickListener(this);
        doodle_selectable_remove.setOnClickListener(this);
        btn_pen_hand.setOnClickListener(this);
//        btn_pen_hand.callOnClick();
        btn_pen_eraser.setOnClickListener(this);
        btn_holl_circle.setOnClickListener(this);
        doodle_btn_brush_edit.setOnClickListener(this);
        doodle_btn_finish.setOnClickListener(this);
        btn_arrow.setOnClickListener(this);
        btn_holl_rect.setOnClickListener(this);
        btn_undo.setOnClickListener(this);
        btn_hand_write.setOnClickListener(this);
        doodle_btn_rotate.setOnClickListener(this);
        mBtnHidePanel.setOnClickListener(this);
        btn_set_color_container.setOnClickListener(this);
        btn_undo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!(DoodleParams.getDialogInterceptor() != null
                        && DoodleParams.getDialogInterceptor().onShow(PAD_gongwen_SP.this, mDoodle, DoodleParams.DialogType.CLEAR_ALL))) {
                    DialogController.showEnterCancelDialog(PAD_gongwen_SP.this,
                            getString(R.string.doodle_clear_screen), getString(R.string.doodle_cant_undo_after_clearing),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDoodle.clear();
                                }
                            }, null
                    );
                }
                return true;
            }
        });
        mEditSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= 0) {
                    mEditSizeSeekBar.setProgress(0);
                    //     return;
                }
//                if ((int) mDoodle.getSize() == progress)
//                {
//                //    return;
//                }
                if (mDoodle != null) {
                    mDoodle.setSize(progress);
                    if (mTouchGestureListener.getSelectedItem() != null) {
                        mTouchGestureListener.getSelectedItem().setSize(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_disagree = findViewById(R.id.tv_disagree);
        tv_agree = findViewById(R.id.tv_agree);
        tv_disagree.setOnClickListener(this);
        tv_agree.setOnClickListener(this);
        getNetData();
    }

    private void getNetData() {
        RequestParams params = new RequestParams();
        params.put("id", offId);
        HttpRequest.postDocumentInfoApi(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                //需要转化为实体对象
                Gson gson = new GsonBuilder().serializeNulls().create();
                try {
                    JSONObject result = new JSONObject(responseObj.toString());
                    JSONObject data = result.getJSONObject("data");
                    JSONObject lotus = data.getJSONObject("lotus");
                    String aa = lotus.getString("files");
                    tv_pd_number.setText(lotus.getString("symbol"));
                    padbeans.clear();
                    if (aa != null && aa != "") {
                        List<String> c = Arrays.asList(aa.split(","));
                       Padbean padbean;
                        for (int i = 0; i < c.size(); i++) {
                            if (getMIMEType(c.get(i)).equals("image/jpeg") || getMIMEType(c.get(i)).equals("image/png") || getMIMEType(c.get(i)).equals("image/gif")) {
                                padbean = new Padbean();
                                padbean.setUrl(c.get(i));
                                padbeans.add(padbean);
                            } else {
                                path_x = c.get(i);
                                tv_pd_filename.setText(lotus.getString("filesName"));
                            }
                        }

                    }

                    startData();
                    getfile();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    转File
    private void getfile(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int j = 0;j<padbeans.size();j++){
                    padbeans.get(j).setFile(getFile(getBitmap(padbeans.get(j).getUrl())));
                }
                Message message = new Message();
                message.what = 1000;
                handler.sendMessage(message);
            }
        }.start();

    }

    private void startData() {
        mDoodleParams = new DoodleParams();
        mDoodleParams.mIsFullScreen = true;
        // 初始画笔大小
        mDoodleParams.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
        if (padbeans.get(page).getBitmap()==null){
            returnBitMap(padbeans.get(page).getUrl());
        }else {
            bitmap = padbeans.get(page).getBitmap();
            handler.sendMessage(new Message());
        }


    }

    private void setViewWrapper() {
        mDoodle = mDoodleView = new DoodelViewWrapper(this, bitmap, new IDoodleListener() {
            @Override
            public void onSaved(IDoodle doodle, Bitmap bitmap, Runnable callback) { // 保存图片为jpg格式
                File doodleFile = null;
                File file = null;
                File savePathInternal = new File(PAD_gongwen_SP.this.getFilesDir(), "saved");
                savePathInternal.mkdirs();
                String savePath = /*mDoodleParams.mSavePath*/savePathInternal.getAbsolutePath();  //初试时默认值为null
                boolean isDir = /*mDoodleParams.mSavePathIsDir*/true; //初始时默认值为false
                if (TextUtils.isEmpty(savePath)) {  //如果savePath 是null ,将图片保存在"DCIM/Doodle"目录下
                    File dcimFile = new File(Environment.getExternalStorageDirectory(), "DCIM");
                    doodleFile = new File(dcimFile, "Doodle");
                    //　保存的路径
                    file = new File(doodleFile, System.currentTimeMillis() + ".jpg");
                } else {
                    if (isDir) {
                        doodleFile = new File(savePath);
                        //　保存的路径
                        file = new File(doodleFile, System.currentTimeMillis() + ".jpg");
                    } else {
                        file = new File(savePath);
                        doodleFile = file.getParentFile();
                    }
                }
                doodleFile.mkdirs();
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream);
                    ImageUtils.addImage(PAD_gongwen_SP.this.getContentResolver(), file.getAbsolutePath());
                    padbeans.get(page).setFile(file);
                    padbeans.get(page).setBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(DoodleView.ERROR_SAVE, e.getMessage());
                } finally {
                    Util.closeQuietly(outputStream);
                }

            }

            public void onError(int i, String msg) {
                PAD_gongwen_SP.this.setResult(RESULT_ERROR);
                PAD_gongwen_SP.this.finish();
            }

            @Override
            public void onReady(IDoodle doodle) {
                // mEditSizeSeekBar.setMax(Math.min(mDoodleView.getWidth(), mDoodleView.getHeight()));
//                mEditSizeSeekBar.setMax(3); /*设置画笔的最大宽度*/
                float size = mDoodleParams.mPaintUnitSize > 0 ? mDoodleParams.mPaintUnitSize * mDoodle.getUnitSize() : 0;
                if (size <= 0) {
                    size = mDoodleParams.mPaintPixelSize > 0 ? mDoodleParams.mPaintPixelSize : mDoodle.getSize();
                }
                // 设置初始值
                mDoodle.setSize(1);  /*画笔的初试值*/
                // 选择画笔
                mDoodle.setPen(DoodlePen.BRUSH);  //画笔
                mDoodle.setShape(DoodleShape.HAND_WRITE); //手绘
                if (mDoodleParams.mZoomerScale <= 0) {
                    btn_zoomer.setVisibility(View.GONE);
                }
                mDoodle.setZoomerScale(mDoodleParams.mZoomerScale);

                // 每个画笔的初始值
                mPenSizeMap.put(DoodlePen.BRUSH, mDoodle.getSize());
                mPenSizeMap.put(DoodlePen.COPY, DEFAULT_COPY_SIZE * mDoodle.getUnitSize());
                mPenSizeMap.put(DoodlePen.ERASER, mDoodle.getSize());
                mPenSizeMap.put(DoodlePen.TEXT, DEFAULT_TEXT_SIZE * mDoodle.getUnitSize());
                mPenSizeMap.put(DoodlePen.BITMAP, DEFAULT_BITMAP_SIZE * mDoodle.getUnitSize());
            }
        }, null);
        mTouchGestureListener = new DoodleOnTouchGestureListener(mDoodleView, new DoodleOnTouchGestureListener.ISelectionListener() {
            @Override
            public void onSelectedItem(IDoodle doodle, IDoodleSelectableItem selectableItem, boolean selected) {
                if (selected) {
                    mDoodle.setColor(selectableItem.getColor());
                    mDoodle.setSize(selectableItem.getSize());
                    mEditSizeSeekBar.setProgress((int) selectableItem.getSize());
                    mSelectedTextEditContainer.setVisibility(View.VISIBLE);
                    if (doodle.getPen() == DoodlePen.TEXT || doodle.getPen() == DoodlePen.BITMAP) {
                        doodle_selectable_edit.setVisibility(View.VISIBLE);
                    } else {
                        doodle_selectable_edit.setVisibility(View.GONE);
                    }
                } else {
                    mSelectedTextEditContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCreateSelectableItem(IDoodle doodle, float x, float y) {
                if (mDoodle.getPen() == DoodlePen.TEXT) {
                    createDoodleText(null, x, y);
                } else if (mDoodle.getPen() == DoodlePen.BITMAP) {
                    createDoodleBitmap(null, x, y);
                }
            }
        });
        IDoodleTouchDetector detector = new DoodleTouchDetector(PAD_gongwen_SP.this.getApplicationContext(), mTouchGestureListener);
        mDoodleView.setDefaultTouchDetector(detector);
        mDoodle.setIsDrawableOutside(mDoodleParams.mIsDrawableOutside);
        mFrameLayout.addView(mDoodleView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDoodle.setDoodleMinScale(mDoodleParams.mMinScale);
        mDoodle.setDoodleMaxScale(mDoodleParams.mMaxScale);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //    添加文字
    private void createDoodleText(final DoodleText doodleText, final float x, final float y) {
        if (PAD_gongwen_SP.this.isFinishing()) {
            return;
        }

        DialogController.showInputTextDialog(PAD_gongwen_SP.this, doodleText == null ? null : doodleText.getText(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (v.getTag() + "").trim();
                if (TextUtils.isEmpty(text)) {
                    return;
                }
                if (doodleText == null) {
                    IDoodleSelectableItem item = new DoodleText(mDoodle, text, mDoodle.getSize(), mDoodle.getColor().copy(), x, y);
                    mDoodle.addItem(item);
                    mTouchGestureListener.setSelectedItem(item);
                } else {
                    doodleText.setText(text);
                }
                mDoodle.refresh();
            }
        }, null);
        if (doodleText == null) {
            mSettingsPanel.removeCallbacks(mHideDelayRunnable);
        }
    }

    // 添加贴图
    private void createDoodleBitmap(final DoodleBitmap doodleBitmap, final float x, final float y) {
        DialogController.showSelectImageDialog(PAD_gongwen_SP.this, new ImageSelectorView.ImageSelectorListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onEnter(List<String> pathList) {
                Bitmap bitmap = ImageUtils.createBitmapFromPath(pathList.get(0), mDoodleView.getWidth() / 4, mDoodleView.getHeight() / 4);

                if (doodleBitmap == null) {
                    IDoodleSelectableItem item = new DoodleBitmap(mDoodle, bitmap, mDoodle.getSize(), x, y);
                    mDoodle.addItem(item);
                    mTouchGestureListener.setSelectedItem(item);
                } else {
                    doodleBitmap.setBitmap(bitmap);
                }
                mDoodle.refresh();
            }
        });
    }

    private ValueAnimator mRotateAnimator;

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btn_pen_hand) {
            mEditSizeSeekBar.setProgress(0);
            mEditSizeSeekBar.setMax(4);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.BRUSH);
            mEditSizeSeekBar.setProgress(0);
            mEditSizeSeekBar.setMax(4);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.BRUSH);
            mPaintSizeView.setText("" + mEditSizeSeekBar.getProgress());
            mDoodle.setSize(1+mEditSizeSeekBar.getProgress());
            if (mTouchGestureListener.getSelectedItem() != null) {
                mTouchGestureListener.getSelectedItem().setSize(mEditSizeSeekBar.getProgress());
            }
        } else if (v.getId() == R.id.btn_pen_copy) {
            mDoodle.setPen(DoodlePen.COPY);
        } else if (v.getId() == R.id.btn_pen_eraser) {
            mEditSizeSeekBar.setProgress(50);
            mEditSizeSeekBar.setMax(100);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.ERASER);
            mEditSizeSeekBar.setProgress(50);
            mEditSizeSeekBar.setMax(100);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.ERASER);
            mDoodle.setSize(mEditSizeSeekBar.getProgress());
            mPaintSizeView.setText(mEditSizeSeekBar.getProgress() + 1 + "");
            if (mTouchGestureListener.getSelectedItem() != null) {
                mTouchGestureListener.getSelectedItem().setSize(mEditSizeSeekBar.getProgress());
            }

        } else if (v.getId() == R.id.btn_pen_text) {
            mDoodle.setPen(DoodlePen.TEXT);
        } else if (v.getId() == R.id.btn_pen_bitmap) {
            mDoodle.setPen(DoodlePen.BITMAP);
        } else if (v.getId() == R.id.doodle_btn_brush_edit) {
            mDoodleView.setEditMode(!mDoodleView.isEditMode());
        } else if (v.getId() == R.id.btn_undo) {
            mDoodle.undo();
        } else if (v.getId() == R.id.btn_zoomer) {
            mDoodleView.enableZoomer(!mDoodleView.isEnableZoomer());
        } else if (v.getId() == R.id.btn_set_color_container) {
            DoodleColor color = null;
            if (mDoodle.getColor() instanceof DoodleColor) {
                color = (DoodleColor) mDoodle.getColor();
            }
            if (color == null) {
                return;
            }
            if (!(DoodleParams.getDialogInterceptor() != null
                    && DoodleParams.getDialogInterceptor().onShow(PAD_gongwen_SP.this, mDoodle, DoodleParams.DialogType.COLOR_PICKER))) {
                boolean fullScreen = (PAD_gongwen_SP.this.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
                int themeId;
                if (fullScreen) {
                    themeId = android.R.style.Theme_Translucent_NoTitleBar_Fullscreen;
                } else {
                    themeId = android.R.style.Theme_Translucent_NoTitleBar;
                }
                new ColorPickerDialog(PAD_gongwen_SP.this,
                        new ColorPickerDialog.OnColorChangedListener() {
                            @Override
                            public void colorChanged(int color, int size) {
                                mDoodle.setColor(new DoodleColor(color));
                                mDoodle.setSize(size);
                                if (mTouchGestureListener.getSelectedItem() != null) {
                                    IDoodleColor c = mTouchGestureListener.getSelectedItem().getColor();
                                    if (c instanceof DoodleColor) {
                                        ((DoodleColor) c).setColor(color);
                                    }
                                    mTouchGestureListener.getSelectedItem().setSize(size);
                                }
                                mPaintSizeView.setText("" + size);
                            }

                            @Override
                            public void colorChanged(Drawable color, int size) {
                                Bitmap bitmap = ImageUtils.getBitmapFromDrawable(color);
                                mDoodle.setColor(new DoodleColor(bitmap));
                                mDoodle.setSize(size);
                                if (mTouchGestureListener.getSelectedItem() != null) {
                                    IDoodleColor c = mTouchGestureListener.getSelectedItem().getColor();
                                    if (c instanceof DoodleColor) {
                                        ((DoodleColor) c).setColor(bitmap);
                                    }
                                    mTouchGestureListener.getSelectedItem().setSize(size);
                                }
                                mPaintSizeView.setText("" + size);
                            }
                        }, themeId).show(mDoodleView, mBtnColor.getBackground(), Math.min(mDoodleView.getWidth(), mDoodleView.getHeight()));
            }
        } else if (v.getId() == R.id.doodle_btn_hide_panel) {
            mSettingsPanel.removeCallbacks(mHideDelayRunnable);
            mSettingsPanel.removeCallbacks(mShowDelayRunnable);
            v.setSelected(!v.isSelected());
            if (!mBtnHidePanel.isSelected()) {
                showView(mSettingsPanel);
            } else {
                hideView(mSettingsPanel);
            }
        } else if (v.getId() == R.id.doodle_btn_finish) {
            mDoodle.save();
        } else if (v.getId() == R.id.doodle_btn_back) {
            if (mDoodle.getAllItem() == null || mDoodle.getAllItem().size() == 0) {
                //finish();
                return;
            }
            if (!(DoodleParams.getDialogInterceptor() != null
                    && DoodleParams.getDialogInterceptor().onShow(PAD_gongwen_SP.this, mDoodle, DoodleParams.DialogType.SAVE))) {
                DialogController.showEnterCancelDialog(PAD_gongwen_SP.this, getString(cn.hzw.doodle.R.string.doodle_saving_picture), null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDoodle.save();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // finish();
                    }
                });
            }
        } else if (v.getId() == R.id.doodle_btn_rotate) {
            // 旋转图片
            if (mRotateAnimator == null) {
                mRotateAnimator = new ValueAnimator();
                mRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        mDoodle.setDoodleRotation(value);
                    }
                });
                mRotateAnimator.setDuration(250);
            }
            if (mRotateAnimator.isRunning()) {
                return;
            }
            mRotateAnimator.setIntValues(mDoodle.getDoodleRotation(), mDoodle.getDoodleRotation() + 90);
            mRotateAnimator.start();
        } else if (v.getId() == R.id.doodle_selectable_edit) {


            if (mTouchGestureListener.getSelectedItem() instanceof DoodleText) {
                createDoodleText((DoodleText) mTouchGestureListener.getSelectedItem(), -1, -1);
            } else if (mTouchGestureListener.getSelectedItem() instanceof DoodleBitmap) {
                createDoodleBitmap((DoodleBitmap) mTouchGestureListener.getSelectedItem(), -1, -1);
            }


        } else if (v.getId() == R.id.doodle_selectable_remove) {
            mDoodle.removeItem(mTouchGestureListener.getSelectedItem());
            mTouchGestureListener.setSelectedItem(null);
        } else if (v.getId() == R.id.doodle_selectable_top) {
            mDoodle.topItem(mTouchGestureListener.getSelectedItem());
        } else if (v.getId() == R.id.doodle_selectable_bottom) {
            mDoodle.bottomItem(mTouchGestureListener.getSelectedItem());
        } else if (v.getId() == R.id.btn_hand_write) {
            mDoodle.setShape(DoodleShape.HAND_WRITE);
        } else if (v.getId() == R.id.btn_arrow) {
            mDoodle.setShape(DoodleShape.ARROW);
        } else if (v.getId() == R.id.btn_line) {
            mDoodle.setShape(DoodleShape.LINE);
        } else if (v.getId() == R.id.btn_holl_circle) {
            mDoodle.setShape(DoodleShape.HOLLOW_CIRCLE);
        } else if (v.getId() == R.id.btn_fill_circle) {
            mDoodle.setShape(DoodleShape.FILL_CIRCLE);
        } else if (v.getId() == R.id.btn_holl_rect) {
            mDoodle.setShape(DoodleShape.HOLLOW_RECT);
        } else if (v.getId() == R.id.btn_fill_rect) {
            mDoodle.setShape(DoodleShape.FILL_RECT);
        } else if (v.getId() == R.id.iv_back) {
            // 返回键
            finish();
        } else if (v.getId() == R.id.tv_disagree) {
            // 驳回
            RequestParams params1 = new RequestParams();
            params1.put("proid", offId);
            params1.put("aaproverId", SPUtils.get(mContext, "userId", "").toString());
            params1.put("approverOpinion", "");
            params1.put("state", "2");
            HttpRequest.postDocumentApproveApi(params1, new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    try {
                        JSONObject result = new JSONObject(responseObj.toString());
                        ToastHelper.showToast(mContext, result.getString("msg"));
                        if (result.getString("code").equals("0")) {
                            finish();
                            EventBus.getDefault().post(new L_PendFragment());
                            EventBus.getDefault().post(new L_ApprovedFragment());
                            EventBus.getDefault().post(new L_UpcomFragment());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                    Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v.getId() == R.id.tv_agree) {
            mDoodle.save();
            startData();

//            // 同意
            RequestParams params = new RequestParams();
            params.put("proid", offId);
            params.put("aaproverId", SPUtils.get(mContext, "userId", "").toString());
            params.put("approverOpinion", "");
            params.put("state", "1");
            List<File> fils = new ArrayList<>();
            for (Padbean media : padbeans){
                fils.add(media.getFile());
            }
            HttpRequest.postDocumentApproveApi_PAD(params,fils,new ResponseCallback() {
                @Override
                public void onSuccess(Object responseObj) {
                    try {
                        JSONObject result = new JSONObject(responseObj.toString());
                        ToastHelper.showToast(mContext, result.getString("msg"));
                        if (result.getString("code").equals("0")) {
                            EventBus.getDefault().post(new L_PendFragment());
                            EventBus.getDefault().post(new L_ApprovedFragment());
                            EventBus.getDefault().post(new L_UpcomFragment());
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(OkHttpException failuer) {
                    //   Log.e("TAG", "请求失败=" + failuer.getEmsg());
                    Toast.makeText(mContext, "请求失败=" + failuer.getEmsg(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v.getId() == R.id.tv_pd_filename) {
            if (path_x != "") {
                //            文件名字
                // 這裏加打開文件
                loadDialog.show();
                downloadFilea(path_x);
            } else {
                Toast.makeText(PAD_gongwen_SP.this, "没有文件", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showView(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }

        view.clearAnimation();
        view.startAnimation(mViewShowAnimation);
        view.setVisibility(View.VISIBLE);
    }

    private void hideView(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }
        view.clearAnimation();
        view.startAnimation(mViewHideAnimation);
        view.setVisibility(View.GONE);
    }

    /**
     * 包裹DoodleView，监听相应的设置接口，以改变UI状态
     */
    private class DoodelViewWrapper extends DoodleView {

        public DoodelViewWrapper(Context context, Bitmap bitmap, IDoodleListener listener) {
            super(context, bitmap, listener);
        }

        public DoodelViewWrapper(Context context, Bitmap bitmap, IDoodleListener listener, IDoodleTouchDetector defaultDetector) {
            super(context, bitmap, listener, defaultDetector);
        }

        private Map<IDoodlePen, Integer> mBtnPenIds = new HashMap<>();

        {
            mBtnPenIds.put(DoodlePen.BRUSH, R.id.btn_pen_hand);
            mBtnPenIds.put(DoodlePen.COPY, R.id.btn_pen_copy);
            mBtnPenIds.put(DoodlePen.ERASER, R.id.btn_pen_eraser);
            mBtnPenIds.put(DoodlePen.TEXT, R.id.btn_pen_text);
            mBtnPenIds.put(DoodlePen.BITMAP, R.id.btn_pen_bitmap);
        }

        @Override
        public void setPen(IDoodlePen pen) {
            mPenSizeMap.put(getPen(), getSize()); // save
            super.setPen(pen);
            Float size = mPenSizeMap.get(pen); // restore
            if (size != null) {
                mDoodle.setSize(size);
            }
            mTouchGestureListener.setSelectedItem(null);
            setSingleSelected(mBtnPenIds.values(), mBtnPenIds.get(pen));
            if (pen == DoodlePen.BRUSH) {
                Drawable colorBg = mBtnColor.getBackground();
                if (colorBg instanceof ColorDrawable) {
                    mDoodle.setColor(new DoodleColor(((ColorDrawable) colorBg).getColor()));
                } else {
                    mDoodle.setColor(new DoodleColor(((BitmapDrawable) colorBg).getBitmap()));
                }
            } else if (pen == DoodlePen.COPY) {
                mDoodle.setColor(null);
            } else if (pen == DoodlePen.ERASER) {
                mDoodle.setColor(null);
            } else if (pen == DoodlePen.TEXT) {
                Drawable colorBg = mBtnColor.getBackground();
                if (colorBg instanceof ColorDrawable) {
                    mDoodle.setColor(new DoodleColor(((ColorDrawable) colorBg).getColor()));
                } else {
                    mDoodle.setColor(new DoodleColor(((BitmapDrawable) colorBg).getBitmap()));
                }
            } else if (pen == DoodlePen.BITMAP) {
                Drawable colorBg = mBtnColor.getBackground();
                if (colorBg instanceof ColorDrawable) {
                    mDoodle.setColor(new DoodleColor(((ColorDrawable) colorBg).getColor()));
                } else {
                    mDoodle.setColor(new DoodleColor(((BitmapDrawable) colorBg).getBitmap()));
                }
            }
        }

        private Map<IDoodleShape, Integer> mBtnShapeIds = new HashMap<>();

        {
            mBtnShapeIds.put(DoodleShape.HAND_WRITE, R.id.btn_hand_write);
            mBtnShapeIds.put(DoodleShape.ARROW, R.id.btn_arrow);
            mBtnShapeIds.put(DoodleShape.LINE, R.id.btn_line);
            mBtnShapeIds.put(DoodleShape.HOLLOW_CIRCLE, R.id.btn_holl_circle);
            mBtnShapeIds.put(DoodleShape.FILL_CIRCLE, R.id.btn_fill_circle);
            mBtnShapeIds.put(DoodleShape.HOLLOW_RECT, R.id.btn_holl_rect);
            mBtnShapeIds.put(DoodleShape.FILL_RECT, R.id.btn_fill_rect);

        }

        @Override
        public void setShape(IDoodleShape shape) {
            super.setShape(shape);
            setSingleSelected(mBtnShapeIds.values(), mBtnShapeIds.get(shape));
        }


        @Override
        public void setSize(float paintSize) {
            super.setSize(paintSize);
            mEditSizeSeekBar.setProgress((int) paintSize);
            mPaintSizeView.setText((int) paintSize + 1 + "");
        }

        @Override
        public void setColor(IDoodleColor color) {
            if (getPen() == DoodlePen.COPY || getPen() == DoodlePen.ERASER) {
                if ((getColor() instanceof DoodleColor) && ((DoodleColor) getColor()).getBitmap() == mDoodle.getBitmap()) {
                } else {
                    super.setColor(new DoodleColor(mDoodle.getBitmap()));
                }
            } else {
                super.setColor(color);
            }

            DoodleColor doodleColor = null;
            if (color instanceof DoodleColor) {
                doodleColor = (DoodleColor) color;
            }
            if (doodleColor != null) {
                if (doodleColor.getType() == DoodleColor.Type.COLOR) {
                    mBtnColor.setBackgroundColor(doodleColor.getColor());
                } else if (doodleColor.getType() == DoodleColor.Type.BITMAP) {
                    mBtnColor.setBackgroundDrawable(new BitmapDrawable(doodleColor.getBitmap()));
                }
            }
        }

        @Override
        public void enableZoomer(boolean enable) {
            super.enableZoomer(enable);
            btn_zoomer.setSelected(enable);
            if (enable) {
                Toast.makeText(PAD_gongwen_SP.this, "x" + mDoodleParams.mZoomerScale, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean undo() {
            mTouchGestureListener.setSelectedItem(null);
            return super.undo();
        }

        @Override
        public void clear() {
            super.clear();
            mTouchGestureListener.setSelectedItem(null);
        }

        View mBtnEditMode = doodle_btn_brush_edit;

        @Override
        public void setEditMode(boolean editMode) {
            super.setEditMode(editMode);
            mBtnEditMode.setSelected(editMode);
            if (editMode) {
                Toast.makeText(PAD_gongwen_SP.this, cn.hzw.doodle.R.string.doodle_edit_mode, Toast.LENGTH_SHORT).show();
            } else {
                mTouchGestureListener.setSelectedItem(null);
            }
        }

        private void setSingleSelected(Collection<Integer> ids, int selectedId) {
            for (int id : ids) {
                if (id == selectedId) {
                    PAD_gongwen_SP.this.findViewById(id).setSelected(true);
                } else {
//                    mView.findViewById(id).setSelected(false);
                    PAD_gongwen_SP.this.findViewById(id).setSelected(false);

                }
            }
        }
    }

    private Bitmap getBitmap(String url){
        Bitmap bitmap = null;
        URL imageurl = null;
        try {
            imageurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void returnBitMap(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = getBitmap(url);
                handler.sendMessage( new Message());
            }
        }).start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1000){
                loadDialog.dismiss();
            }else {
                setViewWrapper();
            }
        }

    };

    //在这里抽取了一个方法   可以封装到自己的工具类中...
    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory()+"/"+System.currentTimeMillis() + ".jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //调用方法。先请求权限
    public void downloadFilea(String a) {
        verifyStoragePermissions(PAD_gongwen_SP.this);
        getFile(a);
    }

    //动态请求权限
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void getFile(String a) {
        Request request = new Request.Builder().url(a).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("myTag", "下载失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    loadDialog.dismiss();
                    writeFile(response, a);

                }
            }
        });
    }

    private void writeFile(Response response, String url) {
        Sink sink = null;
        BufferedSink bufferedSink = null;
        try {
            String mSDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();//SD卡路径
            //String appPath= getApplicationContext().getFilesDir().getAbsolutePath();//此APP的files路径
            File dest = new File(mSDCardPath, url.substring(url.lastIndexOf("/") + 1));
            sink = Okio.sink(dest);
            bufferedSink = Okio.buffer(sink);
            bufferedSink.writeAll(response.body().source());
            bufferedSink.close();
            Log.i("DOWNLOAD", "成功" + dest.getPath());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }).start();
            openFiles(dest.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("DOWNLOAD", "download failed");
        }
        Log.i("myTag", "下载成功");
    }

    /**
     * 打开文件
     *
     * @param filesPath
     */
    public void openFiles(String filesPath) {
        Uri uri = getImageContentUri(new File(filesPath));
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        String type = getMIMEType(filesPath);
        intent.setDataAndType(uri, type);
        if (!type.equals("*/*")) {
            try {
                startActivity(intent);
            } catch (Exception e) {
                startActivity(showOpenTypeDialog(filesPath));
            }
        } else {
            startActivity(showOpenTypeDialog(filesPath));
        }
    }

    public static Intent showOpenTypeDialog(String param) {
        Log.e("ViChildError", "showOpenTypeDialog");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * 转换 content:// uri
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    /**
     * --获取文件类型 --
     */
    public static String getMIMEType(String filePath) {
        String type = "*/*";
        String fName = filePath;

        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }

        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") {
            return type;
        }

        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
            }
        }
        return type;
    }

    /**
     * -- MIME 列表 --
     */
    public static final String[][] MIME_MapTable =
            {
                    // --{后缀名， MIME类型}   --
                    {".3gp", "video/3gpp"},
                    {".3gpp", "video/3gpp"},
                    {".aac", "audio/x-mpeg"},
                    {".amr", "audio/x-mpeg"},
                    {".apk", "application/vnd.android.package-archive"},
                    {".avi", "video/x-msvideo"},
                    {".aab", "application/x-authoware-bin"},
                    {".aam", "application/x-authoware-map"},
                    {".aas", "application/x-authoware-seg"},
                    {".ai", "application/postscript"},
                    {".aif", "audio/x-aiff"},
                    {".aifc", "audio/x-aiff"},
                    {".aiff", "audio/x-aiff"},
                    {".als", "audio/x-alpha5"},
                    {".amc", "application/x-mpeg"},
                    {".ani", "application/octet-stream"},
                    {".asc", "text/plain"},
                    {".asd", "application/astound"},
                    {".asf", "video/x-ms-asf"},
                    {".asn", "application/astound"},
                    {".asp", "application/x-asap"},
                    {".asx", " video/x-ms-asf"},
                    {".au", "audio/basic"},
                    {".avb", "application/octet-stream"},
                    {".awb", "audio/amr-wb"},
                    {".bcpio", "application/x-bcpio"},
                    {".bld", "application/bld"},
                    {".bld2", "application/bld2"},
                    {".bpk", "application/octet-stream"},
                    {".bz2", "application/x-bzip2"},
                    {".bin", "application/octet-stream"},
                    {".bmp", "image/bmp"},
                    {".c", "text/plain"},
                    {".class", "application/octet-stream"},
                    {".conf", "text/plain"},
                    {".cpp", "text/plain"},
                    {".cal", "image/x-cals"},
                    {".ccn", "application/x-cnc"},
                    {".cco", "application/x-cocoa"},
                    {".cdf", "application/x-netcdf"},
                    {".cgi", "magnus-internal/cgi"},
                    {".chat", "application/x-chat"},
                    {".clp", "application/x-msclip"},
                    {".cmx", "application/x-cmx"},
                    {".co", "application/x-cult3d-object"},
                    {".cod", "image/cis-cod"},
                    {".cpio", "application/x-cpio"},
                    {".cpt", "application/mac-compactpro"},
                    {".crd", "application/x-mscardfile"},
                    {".csh", "application/x-csh"},
                    {".csm", "chemical/x-csml"},
                    {".csml", "chemical/x-csml"},
                    {".css", "text/css"},
                    {".cur", "application/octet-stream"},
                    {".doc", "application/msword"},
                    {".dcm", "x-lml/x-evm"},
                    {".dcr", "application/x-director"},
                    {".dcx", "image/x-dcx"},
                    {".dhtml", "text/html"},
                    {".dir", "application/x-director"},
                    {".dll", "application/octet-stream"},
                    {".dmg", "application/octet-stream"},
                    {".dms", "application/octet-stream"},
                    {".dot", "application/x-dot"},
                    {".dvi", "application/x-dvi"},
                    {".dwf", "drawing/x-dwf"},
                    {".dwg", "application/x-autocad"},
                    {".dxf", "application/x-autocad"},
                    {".dxr", "application/x-director"},
                    {".ebk", "application/x-expandedbook"},
                    {".emb", "chemical/x-embl-dl-nucleotide"},
                    {".embl", "chemical/x-embl-dl-nucleotide"},
                    {".eps", "application/postscript"},
                    {".epub", "application/epub+zip"},
                    {".eri", "image/x-eri"},
                    {".es", "audio/echospeech"},
                    {".esl", "audio/echospeech"},
                    {".etc", "application/x-earthtime"},
                    {".etx", "text/x-setext"},
                    {".evm", "x-lml/x-evm"},
                    {".evy", "application/x-envoy"},
                    {".exe", "application/octet-stream"},
                    {".fh4", "image/x-freehand"},
                    {".fh5", "image/x-freehand"},
                    {".fhc", "image/x-freehand"},
                    {".fif", "image/fif"},
                    {".fm", "application/x-maker"},
                    {".fpx", "image/x-fpx"},
                    {".fvi", "video/isivideo"},
                    {".flv", "video/x-msvideo"},
                    {".gau", "chemical/x-gaussian-input"},
                    {".gca", "application/x-gca-compressed"},
                    {".gdb", "x-lml/x-gdb"},
                    {".gif", "image/gif"},
                    {".gps", "application/x-gps"},
                    {".gtar", "application/x-gtar"},
                    {".gz", "application/x-gzip"},
                    {".gif", "image/gif"},
                    {".gtar", "application/x-gtar"},
                    {".gz", "application/x-gzip"},
                    {".h", "text/plain"},
                    {".hdf", "application/x-hdf"},
                    {".hdm", "text/x-hdml"},
                    {".hdml", "text/x-hdml"},
                    {".htm", "text/html"},
                    {".html", "text/html"},
                    {".hlp", "application/winhlp"},
                    {".hqx", "application/mac-binhex40"},
                    {".hts", "text/html"},
                    {".ice", "x-conference/x-cooltalk"},
                    {".ico", "application/octet-stream"},
                    {".ief", "image/ief"},
                    {".ifm", "image/gif"},
                    {".ifs", "image/ifs"},
                    {".imy", "audio/melody"},
                    {".ins", "application/x-net-install"},
                    {".ips", "application/x-ipscript"},
                    {".ipx", "application/x-ipix"},
                    {".it", "audio/x-mod"},
                    {".itz", "audio/x-mod"},
                    {".ivr", "i-world/i-vrml"},
                    {".j2k", "image/j2k"},
                    {".jad", "text/vnd.sun.j2me.app-descriptor"},
                    {".jam", "application/x-jam"},
                    {".jnlp", "application/x-java-jnlp-file"},
                    {".jpe", "image/jpeg"},
                    {".jpz", "image/jpeg"},
                    {".jwc", "application/jwc"},
                    {".jar", "application/java-archive"},
                    {".java", "text/plain"},
                    {".jpeg", "image/jpeg"},
                    {".jpg", "image/jpeg"},
                    {".js", "application/x-javascript"},
                    {".kjx", "application/x-kjx"},
                    {".lak", "x-lml/x-lak"},
                    {".latex", "application/x-latex"},
                    {".lcc", "application/fastman"},
                    {".lcl", "application/x-digitalloca"},
                    {".lcr", "application/x-digitalloca"},
                    {".lgh", "application/lgh"},
                    {".lha", "application/octet-stream"},
                    {".lml", "x-lml/x-lml"},
                    {".lmlpack", "x-lml/x-lmlpack"},
                    {".log", "text/plain"},
                    {".lsf", "video/x-ms-asf"},
                    {".lsx", "video/x-ms-asf"},
                    {".lzh", "application/x-lzh "},
                    {".m13", "application/x-msmediaview"},
                    {".m14", "application/x-msmediaview"},
                    {".m15", "audio/x-mod"},
                    {".m3u", "audio/x-mpegurl"},
                    {".m3url", "audio/x-mpegurl"},
                    {".ma1", "audio/ma1"},
                    {".ma2", "audio/ma2"},
                    {".ma3", "audio/ma3"},
                    {".ma5", "audio/ma5"},
                    {".man", "application/x-troff-man"},
                    {".map", "magnus-internal/imagemap"},
                    {".mbd", "application/mbedlet"},
                    {".mct", "application/x-mascot"},
                    {".mdb", "application/x-msaccess"},
                    {".mdz", "audio/x-mod"},
                    {".me", "application/x-troff-me"},
                    {".mel", "text/x-vmel"},
                    {".mi", "application/x-mif"},
                    {".mid", "audio/midi"},
                    {".midi", "audio/midi"},
                    {".m4a", "audio/mp4a-latm"},
                    {".m4b", "audio/mp4a-latm"},
                    {".m4p", "audio/mp4a-latm"},
                    {".m4u", "video/vnd.mpegurl"},
                    {".m4v", "video/x-m4v"},
                    {".mov", "video/quicktime"},
                    {".mp2", "audio/x-mpeg"},
                    {".mp3", "audio/x-mpeg"},
                    {".mp4", "video/mp4"},
                    {".mpc", "application/vnd.mpohun.certificate"},
                    {".mpe", "video/mpeg"},
                    {".mpeg", "video/mpeg"},
                    {".mpg", "video/mpeg"},
                    {".mpg4", "video/mp4"},
                    {".mpga", "audio/mpeg"},
                    {".msg", "application/vnd.ms-outlook"},
                    {".mif", "application/x-mif"},
                    {".mil", "image/x-cals"},
                    {".mio", "audio/x-mio"},
                    {".mmf", "application/x-skt-lbs"},
                    {".mng", "video/x-mng"},
                    {".mny", "application/x-msmoney"},
                    {".moc", "application/x-mocha"},
                    {".mocha", "application/x-mocha"},
                    {".mod", "audio/x-mod"},
                    {".mof", "application/x-yumekara"},
                    {".mol", "chemical/x-mdl-molfile"},
                    {".mop", "chemical/x-mopac-input"},
                    {".movie", "video/x-sgi-movie"},
                    {".mpn", "application/vnd.mophun.application"},
                    {".mpp", "application/vnd.ms-project"},
                    {".mps", "application/x-mapserver"},
                    {".mrl", "text/x-mrml"},
                    {".mrm", "application/x-mrm"},
                    {".ms", "application/x-troff-ms"},
                    {".mts", "application/metastream"},
                    {".mtx", "application/metastream"},
                    {".mtz", "application/metastream"},
                    {".mzv", "application/metastream"},
                    {".nar", "application/zip"},
                    {".nbmp", "image/nbmp"},
                    {".nc", "application/x-netcdf"},
                    {".ndb", "x-lml/x-ndb"},
                    {".ndwn", "application/ndwn"},
                    {".nif", "application/x-nif"},
                    {".nmz", "application/x-scream"},
                    {".nokia-op-logo", "image/vnd.nok-oplogo-color"},
                    {".npx", "application/x-netfpx"},
                    {".nsnd", "audio/nsnd"},
                    {".nva", "application/x-neva1"},
                    {".oda", "application/oda"},
                    {".oom", "application/x-atlasMate-plugin"},
                    {".ogg", "audio/ogg"},
                    {".pac", "audio/x-pac"},
                    {".pae", "audio/x-epac"},
                    {".pan", "application/x-pan"},
                    {".pbm", "image/x-portable-bitmap"},
                    {".pcx", "image/x-pcx"},
                    {".pda", "image/x-pda"},
                    {".pdb", "chemical/x-pdb"},
                    {".pdf", "application/pdf"},
                    {".pfr", "application/font-tdpfr"},
                    {".pgm", "image/x-portable-graymap"},
                    {".pict", "image/x-pict"},
                    {".pm", "application/x-perl"},
                    {".pmd", "application/x-pmd"},
                    {".png", "image/png"},
                    {".pnm", "image/x-portable-anymap"},
                    {".pnz", "image/png"},
                    {".pot", "application/vnd.ms-powerpoint"},
                    {".ppm", "image/x-portable-pixmap"},
                    {".pps", "application/vnd.ms-powerpoint"},
                    {".ppt", "application/vnd.ms-powerpoint"},
                    {".pqf", "application/x-cprplayer"},
                    {".pqi", "application/cprplayer"},
                    {".prc", "application/x-prc"},
                    {".proxy", "application/x-ns-proxy-autoconfig"},
                    {".prop", "text/plain"},
                    {".ps", "application/postscript"},
                    {".ptlk", "application/listenup"},
                    {".pub", "application/x-mspublisher"},
                    {".pvx", "video/x-pv-pvx"},
                    {".qcp", "audio/vnd.qcelp"},
                    {".qt", "video/quicktime"},
                    {".qti", "image/x-quicktime"},
                    {".qtif", "image/x-quicktime"},
                    {".r3t", "text/vnd.rn-realtext3d"},
                    {".ra", "audio/x-pn-realaudio"},
                    {".ram", "audio/x-pn-realaudio"},
                    {".ras", "image/x-cmu-raster"},
                    {".rdf", "application/rdf+xml"},
                    {".rf", "image/vnd.rn-realflash"},
                    {".rgb", "image/x-rgb"},
                    {".rlf", "application/x-richlink"},
                    {".rm", "audio/x-pn-realaudio"},
                    {".rmf", "audio/x-rmf"},
                    {".rmm", "audio/x-pn-realaudio"},
                    {".rnx", "application/vnd.rn-realplayer"},
                    {".roff", "application/x-troff"},
                    {".rp", "image/vnd.rn-realpix"},
                    {".rpm", "audio/x-pn-realaudio-plugin"},
                    {".rt", "text/vnd.rn-realtext"},
                    {".rte", "x-lml/x-gps"},
                    {".rtf", "application/rtf"},
                    {".rtg", "application/metastream"},
                    {".rtx", "text/richtext"},
                    {".rv", "video/vnd.rn-realvideo"},
                    {".rwc", "application/x-rogerwilco"},
                    {".rar", "application/x-rar-compressed"},
                    {".rc", "text/plain"},
                    {".rmvb", "audio/x-pn-realaudio"},
                    {".s3m", "audio/x-mod"},
                    {".s3z", "audio/x-mod"},
                    {".sca", "application/x-supercard"},
                    {".scd", "application/x-msschedule"},
                    {".sdf", "application/e-score"},
                    {".sea", "application/x-stuffit"},
                    {".sgm", "text/x-sgml"},
                    {".sgml", "text/x-sgml"},
                    {".shar", "application/x-shar"},
                    {".shtml", "magnus-internal/parsed-html"},
                    {".shw", "application/presentations"},
                    {".si6", "image/si6"},
                    {".si7", "image/vnd.stiwap.sis"},
                    {".si9", "image/vnd.lgtwap.sis"},
                    {".sis", "application/vnd.symbian.install"},
                    {".sit", "application/x-stuffit"},
                    {".skd", "application/x-koan"},
                    {".skm", "application/x-koan"},
                    {".skp", "application/x-koan"},
                    {".skt", "application/x-koan"},
                    {".slc", "application/x-salsa"},
                    {".smd", "audio/x-smd"},
                    {".smi", "application/smil"},
                    {".smil", "application/smil"},
                    {".smp", "application/studiom"},
                    {".smz", "audio/x-smd"},
                    {".sh", "application/x-sh"},
                    {".snd", "audio/basic"},
                    {".spc", "text/x-speech"},
                    {".spl", "application/futuresplash"},
                    {".spr", "application/x-sprite"},
                    {".sprite", "application/x-sprite"},
                    {".sdp", "application/sdp"},
                    {".spt", "application/x-spt"},
                    {".src", "application/x-wais-source"},
                    {".stk", "application/hyperstudio"},
                    {".stm", "audio/x-mod"},
                    {".sv4cpio", "application/x-sv4cpio"},
                    {".sv4crc", "application/x-sv4crc"},
                    {".svf", "image/vnd"},
                    {".svg", "image/svg-xml"},
                    {".svh", "image/svh"},
                    {".svr", "x-world/x-svr"},
                    {".swf", "application/x-shockwave-flash"},
                    {".swfl", "application/x-shockwave-flash"},
                    {".t", "application/x-troff"},
                    {".tad", "application/octet-stream"},
                    {".talk", "text/x-speech"},
                    {".tar", "application/x-tar"},
                    {".taz", "application/x-tar"},
                    {".tbp", "application/x-timbuktu"},
                    {".tbt", "application/x-timbuktu"},
                    {".tcl", "application/x-tcl"},
                    {".tex", "application/x-tex"},
                    {".texi", "application/x-texinfo"},
                    {".texinfo", "application/x-texinfo"},
                    {".tgz", "application/x-tar"},
                    {".thm", "application/vnd.eri.thm"},
                    {".tif", "image/tiff"},
                    {".tiff", "image/tiff"},
                    {".tki", "application/x-tkined"},
                    {".tkined", "application/x-tkined"},
                    {".toc", "application/toc"},
                    {".toy", "image/toy"},
                    {".tr", "application/x-troff"},
                    {".trk", "x-lml/x-gps"},
                    {".trm", "application/x-msterminal"},
                    {".tsi", "audio/tsplayer"},
                    {".tsp", "application/dsptype"},
                    {".tsv", "text/tab-separated-values"},
                    {".ttf", "application/octet-stream"},
                    {".ttz", "application/t-time"},
                    {".txt", "text/plain"},
                    {".ult", "audio/x-mod"},
                    {".ustar", "application/x-ustar"},
                    {".uu", "application/x-uuencode"},
                    {".uue", "application/x-uuencode"},
                    {".vcd", "application/x-cdlink"},
                    {".vcf", "text/x-vcard"},
                    {".vdo", "video/vdo"},
                    {".vib", "audio/vib"},
                    {".viv", "video/vivo"},
                    {".vivo", "video/vivo"},
                    {".vmd", "application/vocaltec-media-desc"},
                    {".vmf", "application/vocaltec-media-file"},
                    {".vmi", "application/x-dreamcast-vms-info"},
                    {".vms", "application/x-dreamcast-vms"},
                    {".vox", "audio/voxware"},
                    {".vqe", "audio/x-twinvq-plugin"},
                    {".vqf", "audio/x-twinvq"},
                    {".vql", "audio/x-twinvq"},
                    {".vre", "x-world/x-vream"},
                    {".vrml", "x-world/x-vrml"},
                    {".vrt", "x-world/x-vrt"},
                    {".vrw", "x-world/x-vream"},
                    {".vts", "workbook/formulaone"},
                    {".wax", "audio/x-ms-wax"},
                    {".wbmp", "image/vnd.wap.wbmp"},
                    {".web", "application/vnd.xara"},
                    {".wav", "audio/x-wav"},
                    {".wma", "audio/x-ms-wma"},
                    {".wmv", "audio/x-ms-wmv"},
                    {".wi", "image/wavelet"},
                    {".wis", "application/x-InstallShield"},
                    {".wm", "video/x-ms-wm"},
                    {".wmd", "application/x-ms-wmd"},
                    {".wmf", "application/x-msmetafile"},
                    {".wml", "text/vnd.wap.wml"},
                    {".wmlc", "application/vnd.wap.wmlc"},
                    {".wmls", "text/vnd.wap.wmlscript"},
                    {".wmlsc", "application/vnd.wap.wmlscriptc"},
                    {".wmlscript", "text/vnd.wap.wmlscript"},
                    {".wmv", "video/x-ms-wmv"},
                    {".wmx", "video/x-ms-wmx"},
                    {".wmz", "application/x-ms-wmz"},
                    {".wpng", "image/x-up-wpng"},
                    {".wps", "application/vnd.ms-works"},
                    {".wpt", "x-lml/x-gps"},
                    {".wri", "application/x-mswrite"},
                    {".wrl", "x-world/x-vrml"},
                    {".wrz", "x-world/x-vrml"},
                    {".ws", "text/vnd.wap.wmlscript"},
                    {".wsc", "application/vnd.wap.wmlscriptc"},
                    {".wv", "video/wavelet"},
                    {".wvx", "video/x-ms-wvx"},
                    {".wxl", "application/x-wxl"},
                    {".x-gzip", "application/x-gzip"},
                    {".xar", "application/vnd.xara"},
                    {".xbm", "image/x-xbitmap"},
                    {".xdm", "application/x-xdma"},
                    {".xdma", "application/x-xdma"},
                    {".xdw", "application/vnd.fujixerox.docuworks"},
                    {".xht", "application/xhtml+xml"},
                    {".xhtm", "application/xhtml+xml"},
                    {".xhtml", "application/xhtml+xml"},
                    {".xla", "application/vnd.ms-excel"},
                    {".xlc", "application/vnd.ms-excel"},
                    {".xll", "application/x-excel"},
                    {".xlm", "application/vnd.ms-excel"},
                    {".xls", "application/vnd.ms-excel"},
                    {".xlsx", "application/vnd.ms-excel"},
                    {".xlt", "application/vnd.ms-excel"},
                    {".xlw", "application/vnd.ms-excel"},
                    {".xm", "audio/x-mod"},
                    {".xml", "text/xml"},
                    {".xmz", "audio/x-mod"},
                    {".xpi", "application/x-xpinstall"},
                    {".xpm", "image/x-xpixmap"},
                    {".xsit", "text/xml"},
                    {".xsl", "text/xml"},
                    {".xul", "text/xul"},
                    {".xwd", "image/x-xwindowdump"},
                    {".xyz", "chemical/x-pdb"},
                    {".yz1", "application/x-yz1"},
                    {".z", "application/x-compress"},
                    {".zac", "application/x-zaurus-zac"},
                    {".zip", "application/zip"},
                    {"", "*/*"}
            };


}
