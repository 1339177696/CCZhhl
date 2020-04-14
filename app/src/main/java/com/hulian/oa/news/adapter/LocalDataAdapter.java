package com.hulian.oa.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hulian.oa.R;

import java.util.ArrayList;
import java.util.List;


public class LocalDataAdapter extends RecyclerView.Adapter<LocalDataAdapter.ViewHolder> {
    private Context mContext;
    private List<String> list_path;
    public LocalDataAdapter(Context context, ArrayList<String> list_path) {
        mContext = context;
        this.list_path=list_path;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_over, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          holder.imageView.setImageURI(list_path.get(position));
    }

    @Override
    public int getItemCount() {
        return list_path.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
