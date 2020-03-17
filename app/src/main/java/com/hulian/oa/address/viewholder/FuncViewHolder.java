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
//        if (item == FuncItem.VERIFY) {
//            funcName.setText("验证提醒");
//            image.setImageResource(R.mipmap.icon_verify_remind);
//            image.setScaleType(ImageView.ScaleType.FIT_XY);
//            int unreadCount = SystemMessageUnreadManager.getInstance().getSysMsgUnreadCount();
//            updateUnreadNum(unreadCount);
//            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
//            sUnreadCallbackRefs.hb_add(new WeakReference<ReminderManager.UnreadNumChangedCallback>(this));
//        }
//        else if (item == FuncItem.ROBOT) {
//            funcName.setText("智能机器人");
//            image.setImageResource(R.drawable.ic_robot);
//        }
        //else
       if (item == FuncItem.NORMAL_TEAM) {
            funcName.setText("讨论组");
            image.setImageResource(R.mipmap.ic_secretary);
        }
//        else if (item == FuncItem.ADVANCED_TEAM) {
//            funcName.setText("高级群");
//            image.setImageResource(R.drawable.ic_advanced_team);
//        } else if (item == FuncItem.BLACK_LIST) {
//            funcName.setText("黑名单");
//            image.setImageResource(R.drawable.ic_black_list);
//        } else if (item == FuncItem.MY_COMPUTER) {
//            funcName.setText("我的电脑");
//            image.setImageResource(R.drawable.ic_my_computer);
//        }

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
      //  static final FuncItem ROBOT = new FuncItem();
        static final FuncItem NORMAL_TEAM = new FuncItem();
      //  static final FuncItem ADVANCED_TEAM = new FuncItem();
     //   static final FuncItem BLACK_LIST = new FuncItem();
    //    static final FuncItem MY_COMPUTER = new FuncItem();

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
//            items.hb_add(VERIFY);
            //items.hb_add(ROBOT);
            items.add(NORMAL_TEAM);
//            items.hb_add(ADVANCED_TEAM);
//            items.hb_add(BLACK_LIST);
//            items.hb_add(MY_COMPUTER);

            return items;
        }

        public static void handle(Context context, AbsContactItem item) {
            if (item == VERIFY) {
                SystemMessageActivity.start(context);
            }
//            else if (item == ROBOT) {
//                RobotListActivity.start(context);
//            }
            else if (item == NORMAL_TEAM) {
               TeamListActivity.start(context, ItemTypes.TEAMS.NORMAL_TEAM);
            }
//            else if (item == ADVANCED_TEAM) {
//                TeamListActivity.start(context, ItemTypes.TEAMS.ADVANCED_TEAM);
//            } else if (item == MY_COMPUTER) {
//                SessionHelper.startP2PSession(context, DemoCache.getAccount());
//            } else if (item == BLACK_LIST) {
//                BlackListActivity.start(context);
//            }
        }
    }
}
