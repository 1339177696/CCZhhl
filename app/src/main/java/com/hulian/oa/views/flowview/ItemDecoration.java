package com.hulian.oa.views.flowview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hulian.oa.utils.gallery.DisplayUtils;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    public  ItemDecoration(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = space;
    }
}
