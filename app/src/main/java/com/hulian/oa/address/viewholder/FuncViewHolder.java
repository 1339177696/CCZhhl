package com.hulian.oa.address.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.address.activity.SystemMessageActivity;
import com.hulian.oa.address.activity.TeamListActivity;
import com.hulian.oa.message.helper.SystemMessageUnreadManager;
import com.hulian.oa.message.reminder.ReminderId;
import com.hulian.oa.message.reminder.ReminderItem;
import com.hulian.oa.message.reminder.ReminderManager;
import com.netease.nim.uikit.business.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.business.contact.core.item.ItemTypes;
import com.netease.nim.uikit.business.contact.core.model.ContactDataAdapter;
import com.netease.nim.uikit.business.contact.core.viewholder.AbsContactViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FuncViewHolder extends AbsContactViewHolder<FuncViewHolder.FuncItem> implements ReminderManager.UnreadNumChangedCallback {

    private static ArrayList<WeakReference<ReminderManager.UnreadNumChangedCallback>> sUnreadCallbackRefs = new ArrayList<>();

    private ImageView image;
    private TextView funcName;
    private TextView unreadNum;
    private Set<ReminderManager.UnreadNumChangedCallback> callbacks = new HashSet<>();

    @Override
    public View inflate(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.func_contacts_item, null);
        this.image = view.findViewById(R.id.img_head);
        this.funcName = view.findViewById(R.id.tv_func_name);
        this.unreadNum = view.findViewById(R.id.tab_new_msg_label);
        return view;
    }

    @Override
    public void refresh(ContactDataAdapter contactAdapter, int position, FuncItem item) {
       if (item == FuncItem.NORMAL_TEAM) {
            funcName.setText("讨论组");
            image.setImageResource(R.mipmap.ic_secretary);
        }
        if (item != FuncItem.VERIFY) {
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            unreadNum.setVisibility(View.GONE);
        }
    }


    private void updateUnreadNum(int unreadCount) {
        // 2.*版本viewholder复用问题
        if (unreadCount > 0 && funcName.getText().toString().equals("验证提醒")) {
            unreadNum.setVisibility(View.VISIBLE);
            unreadNum.setText("" + unreadCount);
        } else {
            unreadNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUnreadNumChanged(ReminderItem item) {
        if (item.getId() != ReminderId.CONTACT) {
            return;
        }
        updateUnreadNum(item.getUnread());
    }

    public static void unRegisterUnreadNumChangedCallback() {
        Iterator<WeakReference<ReminderManager.UnreadNumChangedCallback>> iter = sUnreadCallbackRefs.iterator();
        while (iter.hasNext()) {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(iter.next().get());
            iter.remove();
        }
    }


    public final static class FuncItem extends AbsContactItem {
        static final FuncItem VERIFY = new FuncItem();
        static final FuncItem NORMAL_TEAM = new FuncItem();
        @Override
        public int getItemType() {
            return ItemTypes.FUNC;
        }

        @Override
        public String belongsGroup() {
            return null;
        }


        public static List<AbsContactItem> provide() {
            List<AbsContactItem> items = new ArrayList<>();
            items.add(NORMAL_TEAM);
            return items;
        }

        public static void handle(Context context, AbsContactItem item) {
            if (item == VERIFY) {
                SystemMessageActivity.start(context);
            }
            else if (item == NORMAL_TEAM) {
               TeamListActivity.start(context, ItemTypes.TEAMS.NORMAL_TEAM);
            }
        }
    }
}
