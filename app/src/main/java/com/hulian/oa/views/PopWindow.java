package com.hulian.oa.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.hulian.oa.R;
import com.hulian.oa.message.session.AddFriendActivity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.team.helper.TeamHelper;

/**
 * <p>Title:PopWindow</p>
 * <p>Description: 自定义PopupWindow</p>
 * @author syz
 * @date 2016-3-14
 */
public class PopWindow extends PopupWindow{
    private View conentView;
    public PopWindow(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_window, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w /3);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        conentView.findViewById(R.id.tv_add).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                context.startActivity(new Intent(context, AddFriendActivity.class));
                PopWindow.this.dismiss();
            }
        });
        conentView.findViewById(R.id.ability_logout).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // do something before signing out
                context.finish();
                PopWindow.this.dismiss();
            }
        });
        conentView.findViewById(R.id.tv_qun).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ContactSelectActivity.Option option = TeamHelper.getCreateContactSelectOption(null, 50);
                NimUIKit.startContactSelector(context, option, 1);

                PopWindow.this.dismiss();
            }
        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 30);
        } else {
            this.dismiss();
        }
    }
}
