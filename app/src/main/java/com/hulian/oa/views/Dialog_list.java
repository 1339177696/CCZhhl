package com.hulian.oa.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.hulian.oa.R;
import com.hulian.oa.work.file.admin.adapter.Followadapter;

import java.util.List;


public class Dialog_list extends Dialog  {

    private Context context;
    private String title;
    private String confirmButtonText;
    private String cacelButtonText;
    private ClickListenerInterface clickListenerInterface;
    private List<String> itemWorks;
    private Followadapter adapter;
    public interface ClickListenerInterface {

        public void doConfirm(List<String> itemWorks);

        public void doCancel();
    }

    public Dialog_list(Context context, String title, String confirmButtonText, String cacelButtonText, List<String> itemWorks) {
        super(context, R.style.custom_dialog2);
        this.context = context;
        this.title = title;
        this.confirmButtonText = confirmButtonText;
        this.cacelButtonText = cacelButtonText;
        this.itemWorks=itemWorks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_admin_box, null);
        setContentView(view);

       // TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvConfirm = (TextView) view.findViewById(R.id.confirm);

       // tvTitle.setText(title);
        tvConfirm.setText(confirmButtonText);

        tvConfirm.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.35); // 高度设置为屏幕的0.6
//        dialogWindow.setAttributes(lp);
        ListView myListView=view.findViewById(R.id.myListView);
        adapter = new Followadapter(context, itemWorks);
        myListView.setAdapter(adapter);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.confirm:
                 dismiss();
                    break;
            }
        }

    }

}