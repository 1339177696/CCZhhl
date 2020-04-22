package com.hulian.oa.work.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.hulian.oa.R;
import com.hulian.oa.work.activity.file.FileFollowActivity;
import com.hulian.oa.work.activity.file.MasterAuthInfoActivity;
import com.shehuan.niv.NiceImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Mr.peng on 2017/3/8.
 */

public class Fileadapter extends BaseAdapter {
    private String changeStr = "";
    private List<String> mlistdatas;
    private LayoutInflater mInflater;
    private Context mcontext;
    public Fileadapter(Context mcontext, List<String> mlistdatas) {
        this.mlistdatas = mlistdatas;
        mInflater = LayoutInflater.from(mcontext);
        this.mcontext=mcontext;
    }

    @Override
    public int getCount() {
        return mlistdatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mlistdatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_file, null);
            viewHolder = new ViewHolder(view);//这句变化
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(i%2==0){
            viewHolder.btGoAuth.setText("授权");
            viewHolder.btGoAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mcontext.startActivity(new Intent(mcontext, MasterAuthInfoActivity.class));
                }
            });
        }
        else {
            viewHolder.btGoAuth.setText("跟踪");
            viewHolder.btGoAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mcontext.startActivity(new Intent(mcontext, FileFollowActivity.class));
                }
            });
        }



        return view;
    }

    static
    class ViewHolder {
        @BindView(R.id.iv_image)
        NiceImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.bt_goAuth)
        Button btGoAuth;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
