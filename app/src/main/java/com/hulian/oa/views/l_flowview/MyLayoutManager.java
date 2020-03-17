package com.hulian.oa.views.l_flowview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * 自动换行布局管理
 * Created by chengxiakuan on 2016/10/14.
 */
public class MyLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        int sumWidth = getWidth();//recyclerview的宽度

        int topOffset = getPaddingTop()+10;//布局时的上偏移
        int leftOffset = getPaddingLeft()+10;//布局时的左偏移


        int curLineWidth = 0, curLineTop = 0;//当前所有view的累加和   当前view的高度
        int lastLineMaxHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);

            addView(view);
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);

            curLineWidth += width;
            if (curLineWidth <= sumWidth) {//不需要换行
                layoutDecorated(view, curLineWidth - width, curLineTop, curLineWidth, curLineTop + height);
//                layoutDecoratedWithMargins(view, curLineWidth, curLineTop, curLineWidth, curLineTop + height);
                //比较当前行多有item的最大高度
                lastLineMaxHeight = Math.max(lastLineMaxHeight, height);
            } else {//换行
                curLineWidth = width;
                if (lastLineMaxHeight == 0) {
                    lastLineMaxHeight = height;
                }
                //记录当前行top
                curLineTop += lastLineMaxHeight;

                layoutDecorated(view, 0, curLineTop, width, curLineTop + height);
//                layoutDecoratedWithMargins(view, 0, curLineTop, width, curLineTop + height);
                lastLineMaxHeight = height;
            }
        }

    }

}
