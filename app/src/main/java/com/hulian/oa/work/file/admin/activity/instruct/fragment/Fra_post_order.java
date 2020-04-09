package com.hulian.oa.work.file.admin.activity.instruct.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hulian.oa.BuildConfig;
import com.hulian.oa.R;
import com.hulian.oa.bean.Fab;
import com.hulian.oa.bean.Fab2;
import com.hulian.oa.bean.People_x;
import com.hulian.oa.pad.PAD_zhiling_XF;
import com.hulian.oa.utils.SPUtils;
import com.hulian.oa.views.fabVIew.FabAlphaAnimate;
import com.hulian.oa.views.fabVIew.FabAttributes;
import com.hulian.oa.views.fabVIew.OnFabClickListener;
import com.hulian.oa.views.fabVIew.SuspensionFab;
import com.hulian.oa.work.file.admin.activity.PostOrderActivity;
import com.hulian.oa.work.file.admin.activity.SecondInstructActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class Fra_post_order extends Fragment  {
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    Unbinder unbinder;
    SuspensionFab fabTop;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_order_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        if (SPUtils.get(getActivity(), "isLead", "").equals("0")) {
            llOrder.setVisibility(View.VISIBLE);
        } else {
            llOrder.setVisibility(View.GONE);
        }

         fabTop = (SuspensionFab) view.findViewById(R.id.fab_top);
//构建展开按钮属性
        FabAttributes collection = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#5B32BB"))
                .setSrc(getResources().getDrawable(R.mipmap.zhiling_gz_icon))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();
        FabAttributes email = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#00B5B9"))
                .setSrc(getResources().getDrawable(R.mipmap.zhiling_fs_icon))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();
//添加菜单
        fabTop.addFab(collection, email);
    //    fabTop.setAnimationManager(new FabAlphaAnimate(fabTop));
//设置菜单点击事件
        fabTop.setFabClickListener(new OnFabClickListener() {
            @Override
            public void onFabClick(FloatingActionButton fab, Object tag) {
                if(Integer.parseInt(tag+"")==1){
                    fabTop.closeAnimate();
               //     llOrder.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(), SecondInstructActivity.class);
                    getActivity().startActivity(intent);
                }
                else {
                    fabTop.closeAnimate();
                    llOrder.setVisibility(View.GONE);
                    View dView = getActivity().getWindow().getDecorView();
                    dView.destroyDrawingCache();
                    dView.setDrawingCacheEnabled(false);
                    dView.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
                    if (bitmap != null) {
                        try {
                            // 获取内置SD卡路径
                            String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                            // 图片文件路径
                            String filePath = sdCardPath + File.separator + System.currentTimeMillis() + "screenshot.png";
                            File file = new File(filePath);
                            FileOutputStream os = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                            os.flush();
                            os.close();
                            llOrder.setVisibility(View.VISIBLE);
//                            Intent intent1 = new Intent(getActivity(), PostOrderActivity.class);
                            if (BuildConfig.IsPad)
                            {
                                Intent intent1 = new Intent(getActivity(), PAD_zhiling_XF.class);
                                intent1.putExtra("file", filePath);
                                getActivity().startActivity(intent1);
                            }
                            else
                            {
                                Intent intent1 = new Intent(getActivity(), PostOrderActivity.class);
                                intent1.putExtra("file", filePath);
                                getActivity().startActivity(intent1);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });

        return view;
    }
    public void onEventMainThread(Fab2 event_x) {
        fabTop.closeAnimate();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}
