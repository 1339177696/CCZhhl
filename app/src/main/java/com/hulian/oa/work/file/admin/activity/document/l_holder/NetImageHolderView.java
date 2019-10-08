package com.hulian.oa.work.file.admin.activity.document.l_holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.hulian.oa.R;

public class NetImageHolderView extends Holder<String> {

    private ImageView mImageView;

    private Context mContext;

    public NetImageHolderView(View itemView, Context context) {
        super(itemView);
        mContext = context;
    }

    @Override
    protected void initView(View itemView) {
        mImageView = itemView.findViewById(R.id.item_image);
    }

    @Override
    public void updateUI(String data) {
        Glide.with(mContext).load(data).into(mImageView);
    }

}
