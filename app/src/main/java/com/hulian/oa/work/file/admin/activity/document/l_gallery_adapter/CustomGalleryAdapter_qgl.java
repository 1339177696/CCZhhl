package com.hulian.oa.work.file.admin.activity.document.l_gallery_adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hulian.oa.R;
import com.hulian.oa.bean.DocumentImage;
import com.hulian.oa.utils.gallery.CollectionsUtils;
import com.hulian.oa.utils.gallery.DisplayUtils;

import java.util.List;

public class CustomGalleryAdapter_qgl extends RecyclerView.Adapter<CustomGalleryAdapter_qgl.ViewHolder> {
    private static final float SCALE = 1.2f;
    private List<String> data;
    private Context context;

    public CustomGalleryAdapter_qgl(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            return null;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (context == null || holder == null || CollectionsUtils.isEmptyList(data)) {
            return;
        }
        ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) holder.itemContent.getLayoutParams());
        if(data.size()==1) {
            if (position == 0) {
                lp.width = DisplayUtils.getItemWidth(context);
                lp.height = DisplayUtils.getItemHeight(context);
                holder.itemContent.setLayoutParams(lp);
            } else {
                lp.width = (int) (DisplayUtils.getItemWidth(context) * SCALE);
                lp.height = (int) (DisplayUtils.getItemHeight(context) * SCALE);
                holder.itemContent.setLayoutParams(lp);
            }
        }
        else {
            if (position == 0) {
                lp.width = DisplayUtils.getItemWidth2(context);
                lp.height = DisplayUtils.getItemHeight(context);
                holder.itemContent.setLayoutParams(lp);
            } else {
                lp.width = (int) (DisplayUtils.getItemWidth2(context) * SCALE);
                lp.height = (int) (DisplayUtils.getItemHeight(context) * SCALE);
                holder.itemContent.setLayoutParams(lp);
            }
        }
        Glide.with(context)
                .load(data.get(position))
         //       .load(Urls.commUrls+"profile/"+data.get(position).getFileName())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView itemContent;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemContent = itemView.findViewById(R.id.itemContent);
            imageView = itemView.findViewById(R.id.headView);
        }
    }
}
