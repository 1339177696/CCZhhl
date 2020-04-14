package com.hulian.oa.iac.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulian.oa.R;
import com.hulian.oa.iac.bean.MyMediaType;
import com.hulian.oa.iac.config.UrlConfig;

import java.util.ArrayList;
import java.util.List;

public class GridImageSecAdapter extends
        RecyclerView.Adapter<GridImageSecAdapter.ViewHolder> {
    public static final String TAG = "GridImageSecAdapter";
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<MyMediaType> list = new ArrayList<>();
    private int selectMax = 5;
    private boolean isAndroidQ;
    private OnDelete onDelete;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public GridImageSecAdapter(Context context, onAddPicClickListener mOnAddPicClickListener, OnDelete onDelete) {
        this.mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        this.onDelete = onDelete;
//        this.isAndroidQ = SdkVersionUtils.checkedAndroid_Q();
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<MyMediaType> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        ImageView mIvDel;
        TextView tvDuration;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.fiv);
            mIvDel = view.findViewById(R.id.iv_del);
//            tvDuration = view.findViewById(R.id.tv_duration);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.iac_girdview_image,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.drawable.addimg);
            viewHolder.mImg.setOnClickListener(v -> mOnAddPicClickListener.onAddPicClick());
            viewHolder.mIvDel.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mIvDel.setVisibility(View.VISIBLE);
            viewHolder.mIvDel.setOnClickListener(view -> {
                int index = viewHolder.getAdapterPosition();
                if (index != RecyclerView.NO_POSITION && list.size() > index) {
                    list.remove(index);
                    onDelete.onItemDelete(list.size());
                    notifyItemRemoved(index);
                    notifyItemRangeChanged(index, list.size());
                }
            });
            String id = list.get(position).getId();
            String type = list.get(position).getType();
            String path = list.get(position).getPath();
            System.out.println("输出获取的图片路径："+path);
            if (path!=null && path.startsWith("/storage/")){
                Glide.with(viewHolder.itemView.getContext())
                        .load(path).into(viewHolder.mImg);;
            }else{
                Glide.with(viewHolder.itemView.getContext())
                        .load(UrlConfig.PAHT_SHOW_IMG+"?mogondbId="+id+"&mediatype="+type)
//                    .load(UrlConfig.PAHT_SHOW_IMG+"?mogondbId="+list.get(position).getPath()+"mediatype="+list.get(position).getType())
                        .into(viewHolder.mImg);
            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(v -> {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mItemClickListener.onItemClick(adapterPosition, v);
                });
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }
    public interface OnDelete {
        void onItemDelete(int mark);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
