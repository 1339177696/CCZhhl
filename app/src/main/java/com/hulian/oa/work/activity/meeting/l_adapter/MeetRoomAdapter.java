package com.hulian.oa.work.activity.meeting.l_adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.bean.MeetingRoom;

import java.util.ArrayList;
import java.util.List;

public class MeetRoomAdapter extends BaseAdapter {
    public Context mContext;
    private List<MeetingRoom> list = new ArrayList<>();
    public MeetRoomAdapter(Context mContext, List<MeetingRoom> list){
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
            return list.size();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.l_item_meet_room, null);
        ImageView imageView = view.findViewById(R.id.iv_meet_room);
        TextView tv_meet_room_num = view.findViewById(R.id.tv_meet_room_num);
        TextView tv_meet_count = view.findViewById(R.id.tv_meet_count);
        TextView tv_sel_room = view.findViewById(R.id.tv_sel_room);
        TextView meetingContacts = view.findViewById(R.id.meetingContacts);
        TextView meetingPhone = view.findViewById(R.id.meetingPhone);
        ImageView phone_ls = view.findViewById(R.id.phone_ls);

//        Glide.with(mContext).load(list.get(position).getPhotoPath()).into(imageView);
        tv_meet_room_num.setText(list.get(position).getMeetingRoomLocation() + list.get(position).getMeetingRoomName());
        tv_meet_count.setText("容纳人数：" + list.get(position).getGalleryful());
        if (list.get(position).getIsCheck().equals("0")) {
            tv_sel_room.setText("选择");
            tv_sel_room.setBackgroundResource(R.drawable.bt_7background_qgl2);
        } else {
            tv_sel_room.setText("已选");
            tv_sel_room.setBackgroundResource(R.drawable.bt_7background_qgl1);
        }

        if (list.get(position).getMeetingContacts() != null && list.get(position).getMeetingContacts() != "") {
            meetingContacts.setVisibility(View.VISIBLE);
            meetingContacts.setText("会议联系人：" + list.get(position).getMeetingContacts());
            tv_sel_room.setText("已占用");
            tv_sel_room.setBackgroundResource(R.drawable.bt_7background_meet);
            phone_ls.setVisibility(View.VISIBLE);

        } else {
            meetingContacts.setVisibility(View.GONE);
            phone_ls.setVisibility(View.GONE);

        }

        if (list.get(position).getMeetingContactsPhone() != null && list.get(position).getMeetingContactsPhone() != "") {
            meetingPhone.setVisibility(View.VISIBLE);
            meetingPhone.setText("联系电话：" + list.get(position).getMeetingContactsPhone());
        } else {
            meetingPhone.setVisibility(View.GONE);
        }
        return view;
    }
}
