package com.hulian.oa.pad;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.DocumentImage;
import com.hulian.oa.bean.People_x;
import com.hulian.oa.me.SelPeopleActivity_x;
import com.hulian.oa.net.HttpRequest;
import com.hulian.oa.net.OkHttpException;
import com.hulian.oa.net.RequestParams;
import com.hulian.oa.net.ResponseCallback;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.utils.ToastHelper;
import com.hulian.oa.work.file.admin.activity.PostOrderActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * Created by qgl on 2019/8/31 13:52.
 */
public class PAD_zhiling_XF extends BaseActivity implements View.OnClickListener
{
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
    private static Bitmap bitmap;

    String filePath;
    private TextView tvSelPeo;
    private RelativeLayout rl_sel_people;
    private Button bt_post;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pad_zhiling_xiafa);
        EventBus.getDefault().register(this);
        filePath=getIntent().getStringExtra("file");
        Log.e("aaa",filePath);


        mFrameLayout = (FrameLayout)findViewById(R.id.doodle_container);

        doodle_btn_back = (MaskImageView) findViewById(R.id.doodle_btn_back);  /*返回箭头*/
        doodle_txt_title = (STextView) findViewById(R.id.doodle_txt_title);
        doodle_btn_rotate = (MaskImageView)findViewById(R.id.doodle_btn_rotate);
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
        btn_line = (ImageView)findViewById(R.id.btn_line);
        btn_holl_circle = (ImageView) findViewById(R.id.btn_holl_circle);
        btn_fill_circle = (ImageView) findViewById(R.id.btn_fill_circle);
        btn_holl_rect = (ImageView) findViewById(R.id.btn_holl_rect);
        btn_fill_rect = (ImageView) findViewById(R.id.btn_fill_rect);
        doodle_btn_brush_edit = (ImageView) findViewById(R.id.doodle_btn_brush_edit);
        doodle_color_container = (LinearLayout) findViewById(R.id.doodle_color_container);
        btn_set_color_container = (FrameLayout) findViewById(R.id.btn_set_color_container);
        mBtnColor = (ImageView) findViewById(R.id.btn_set_color);
        mEditSizeSeekBar = (SeekBar) findViewById(R.id.doodle_seekbar_size);
//        mEditSizeSeekBar.setProgress(1);
//        mEditSizeSeekBar.setMax(1);
        mPaintSizeView = (TextView)findViewById(R.id.paint_size_text);
        mPaintSizeView.setText( mEditSizeSeekBar.getProgress()+1+"");
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
                        && DoodleParams.getDialogInterceptor().onShow(PAD_zhiling_XF.this, mDoodle, DoodleParams.DialogType.CLEAR_ALL))) {
                    DialogController.showEnterCancelDialog(PAD_zhiling_XF.this,
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
        mEditSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= 0)
                {
                    mEditSizeSeekBar.setProgress(0);
                 //   return;
                }
                if ((int) mDoodle.getSize() == progress)
                {
                   // return;
                }
                if(mDoodle!=null) {
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

        tvSelPeo = findViewById(R.id.tv_sel_peo);
        rl_sel_people = findViewById(R.id.rl_sel_people);
        rl_sel_people.setOnClickListener(this);
        bt_post = findViewById(R.id.bt_post);
        bt_post.setOnClickListener(this);
        if (filePath!="")
        {
            startData();
        }
    }

    private void startData() {
        mDoodleParams = new DoodleParams();
        mDoodleParams.mIsFullScreen = true;
        // 图片路径
//       mDoodleParams.mImagePath = mImagePath;
        // 初始画笔大小
        mDoodleParams.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), flag);  //获得图片
//        Bitmap bitmap = BitmapFactory.decodeFile(bundle.getString("data"));

        // 替换了
//        Bitmap bitmap = getBitmap(bundle.getString("flag"));
        Log.e("222",filePath);
        try
        {
            FileInputStream fis = new FileInputStream(filePath);
            bitmap  = BitmapFactory.decodeStream(fis);
        }catch (IOException e)
        {

        }


        mDoodle = mDoodleView = new DoodelViewWrapper(this, bitmap, new IDoodleListener() {
            @Override
            public void onSaved(IDoodle doodle, Bitmap bitmap, Runnable callback) { // 保存图片为jpg格式
                File doodleFile = null;
                File file = null;
                File savePathInternal = new File(PAD_zhiling_XF.this.getFilesDir(), "saved");
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
                    ImageUtils.addImage(PAD_zhiling_XF.this.getContentResolver(), file.getAbsolutePath());
                    Intent intent = new Intent();
                    intent.putExtra(KEY_IMAGE_PATH, file.getAbsolutePath());
                    Log.e("图片路径", file.getAbsolutePath());
                    urlStr = file.getAbsolutePath();
                    intent.putExtra("flag", approveflag);
                    intent.putExtra("message", rejectedText);
                    //intent.putExtra("position",bundle.getInt("position"));
                    //intent.putExtra("whichTab",bundle.getInt("whichTab"));
                    intent.putExtra("filepath",file.getAbsolutePath());
                    PAD_zhiling_XF.this.setResult(Activity.RESULT_OK, intent);
                    PAD_zhiling_XF.this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(DoodleView.ERROR_SAVE, e.getMessage());
                } finally {
                    Util.closeQuietly(outputStream);
                }
            }

            public void onError(int i, String msg) {
                PAD_zhiling_XF.this.setResult(RESULT_ERROR);
                PAD_zhiling_XF.this.finish();
            }

            @Override
            public void onReady(IDoodle doodle) {
                // mEditSizeSeekBar.setMax(Math.min(mDoodleView.getWidth(), mDoodleView.getHeight()));
//                mEditSizeSeekBar.setMax(3); /*设置画笔的最大宽度*/
            /*    float size = mDoodleParams.mPaintUnitSize > 0 ? mDoodleParams.mPaintUnitSize * mDoodle.getUnitSize() : 0;
                if (size <= 0) {
                    size = mDoodleParams.mPaintPixelSize > 0 ? mDoodleParams.mPaintPixelSize : mDoodle.getSize();
                }*/
                // 设置初始值
                mDoodle.setSize(0);  /*画笔的初试值*/
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
        IDoodleTouchDetector detector = new DoodleTouchDetector(PAD_zhiling_XF.this.getApplicationContext(), mTouchGestureListener);
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
        if (PAD_zhiling_XF.this.isFinishing()) {
            return;
        }

        DialogController.showInputTextDialog(PAD_zhiling_XF.this, doodleText == null ? null : doodleText.getText(), new View.OnClickListener() {
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
        DialogController.showSelectImageDialog(PAD_zhiling_XF.this, new ImageSelectorView.ImageSelectorListener() {
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

    boolean flagClick;

    @Override
    public void onClick(final View v)
    {
        if (v.getId() == R.id.btn_pen_hand)
        {
            mEditSizeSeekBar.setProgress(0);
            mEditSizeSeekBar.setMax(4);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.BRUSH);
            mEditSizeSeekBar.setProgress(0);
            mEditSizeSeekBar.setMax(4);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.BRUSH);
            mPaintSizeView.setText(1+ mEditSizeSeekBar.getProgress()+"");
            mDoodle.setSize(mEditSizeSeekBar.getProgress());
            if (mTouchGestureListener.getSelectedItem() != null) {
                mTouchGestureListener.getSelectedItem().setSize(mEditSizeSeekBar.getProgress());
            }
        } else if (v.getId() == R.id.btn_pen_copy) {
            mDoodle.setPen(DoodlePen.COPY);
        } else if (v.getId() == R.id.btn_pen_eraser)
        {
            mEditSizeSeekBar.setProgress(50);
            mEditSizeSeekBar.setMax(100);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.ERASER);
            mEditSizeSeekBar.setProgress(50);
            mEditSizeSeekBar.setMax(100);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.ERASER);
            mDoodle.setSize(mEditSizeSeekBar.getProgress());
            mPaintSizeView.setText( mEditSizeSeekBar.getProgress()+1+"");
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
                    && DoodleParams.getDialogInterceptor().onShow(PAD_zhiling_XF.this, mDoodle, DoodleParams.DialogType.COLOR_PICKER))) {
                boolean fullScreen = (PAD_zhiling_XF.this.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
                int themeId;
                if (fullScreen) {
                    themeId = android.R.style.Theme_Translucent_NoTitleBar_Fullscreen;
                } else {
                    themeId = android.R.style.Theme_Translucent_NoTitleBar;
                }
                new ColorPickerDialog(PAD_zhiling_XF.this,
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
                    && DoodleParams.getDialogInterceptor().onShow(PAD_zhiling_XF.this, mDoodle, DoodleParams.DialogType.SAVE))) {
                DialogController.showEnterCancelDialog(PAD_zhiling_XF.this, getString(cn.hzw.doodle.R.string.doodle_saving_picture), null, new View.OnClickListener() {
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
        }

        else if (v.getId() == R.id.iv_back)
        {
            // 返回键
            finish();
        }

        else if (v.getId() == R.id.rl_sel_people)
        {
            Intent intent=new Intent(PAD_zhiling_XF.this, SelPeopleActivity_x.class);
            intent.putExtra("partId", SPUtils.get(mContext, "deptId", "").toString());
            startActivity(intent);
        }

        else if (v.getId() == R.id.bt_post)
        {
            if(tvSelPeo.getTag().equals("")){
                ToastHelper.showToast(mContext,"请选择接收人");
                return;
            }
            RequestParams params = new RequestParams();
            params.put("createBy",  SPUtils.get(mContext, "userId", "").toString());
            params.put("receiver", tvSelPeo.getTag().toString());
            List<File> fils = new ArrayList<>();
            fils.add(new File(filePath));
            HttpRequest.postInstructSendApi(params, fils, new ResponseCallback()
            {
                @Override
                public void onSuccess(Object responseObj) {

                    try {
                        JSONObject result = new JSONObject(responseObj.toString());
                        ToastHelper.showToast(mContext, result.getString("msg"));
                        finish();
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
            mPaintSizeView.setText((int) paintSize+1+"");
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
                Toast.makeText(PAD_zhiling_XF.this, "x" + mDoodleParams.mZoomerScale, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PAD_zhiling_XF.this, cn.hzw.doodle.R.string.doodle_edit_mode, Toast.LENGTH_SHORT).show();
            } else {
                mTouchGestureListener.setSelectedItem(null);
            }
        }

        private void setSingleSelected(Collection<Integer> ids, int selectedId) {
            for (int id : ids)
            {
                if (id == selectedId) {
                    PAD_zhiling_XF.this.findViewById(id).setSelected(true);
                } else {
//                    mView.findViewById(id).setSelected(false);
                    PAD_zhiling_XF.this.findViewById(id).setSelected(false);

                }
            }
        }
    }


    public void onEventMainThread(People_x event) {
        tvSelPeo.setText(event.getUserName());
        tvSelPeo.setTag(event.getUserId()+"");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public static Bitmap returnBitMap(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fis = new FileInputStream(url);
                    bitmap  = BitmapFactory.decodeStream(fis);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }



}
