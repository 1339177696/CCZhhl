package com.hulian.oa.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.SortModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：qgl 时间： 2020/5/15 14:19
 * Describe:
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private Context mContext;
    public static final int TYPE_One = 0;
    public static final int TYPE_Two = 1;
    public static final int TYPE_Three = 2;
    List<SortModel>a  = null;


    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.a = list;
        SortModel sortModel = new SortModel();
        SortModel sortModel1 = new SortModel();
        List<SortModel>b = new ArrayList<>();
        b.add(sortModel);
        b.add(sortModel1);
        b.addAll(list);
        this.a = b;
    }

    public void updateListView(List<SortModel> list) {
        this.a = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.a.size();
    }

    public Object getItem(int position) {
        return a.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    /**
     * 得到首字母的ascii值
     */
    public int getSectionForPosition(int position) {
        return a.get(position).getSortLetters().charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 2; i < getCount(); i++) {
            String sortStr = a.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_One;
        } else if (position == 1){
            return TYPE_Two;
        } else {
            return TYPE_Three;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleViewHolder titleHolder;
        ComViewHolder comHolder;
        final SortModel mContent = a.get(position);
        switch (getItemViewType(position)) {
            case TYPE_One:
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.address_fragment_list_item2, null);
                    titleHolder = new TitleViewHolder();
                    titleHolder.title = (TextView) convertView.findViewById(R.id.taolun);
                    convertView.setTag(titleHolder);
                } else {
                    titleHolder = (TitleViewHolder) convertView.getTag();
                }
                titleHolder.title.setText("群组");
                break;
            case TYPE_Two:
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.address_fragment_list_item3, null);
                    titleHolder = new TitleViewHolder();
                    titleHolder.title = (TextView) convertView.findViewById(R.id.taolun);
                    convertView.setTag(titleHolder);
                } else {
                    titleHolder = (TitleViewHolder) convertView.getTag();
                }
                titleHolder.title.setText("智慧互联");
                break;
            case TYPE_Three:
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.address_fragment_list_item, null);
                    comHolder = new ComViewHolder();
                    comHolder.tvTitle = convertView.findViewById(R.id.tv_user_item_name);
                    comHolder.tvLetter = convertView.findViewById(R.id.catalog);
                    comHolder.tv_type = convertView.findViewById(R.id.tv_type);
                    convertView.setTag(comHolder);
                } else {
                    comHolder = (ComViewHolder) convertView.getTag();
                }
                    int section = getSectionForPosition(position);
                    if (position == getPositionForSection(section)) {
                        comHolder.tvLetter.setVisibility(View.VISIBLE);
                        comHolder.tvLetter.setText(mContent.getSortLetters());
                    } else {
                        comHolder.tvLetter.setVisibility(View.GONE);
                    }
                    SortModel model = a.get(position);
                    comHolder.tvTitle.setText(model.getUserName());
                    comHolder.tv_type.setText(model.getUserName().substring(model.getUserName().length()-2));

                break;
        }
        return convertView;
    }

    class TitleViewHolder {
        TextView title;
    }

    class ComViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        TextView tv_type;
        ImageView icon;
    }
}
