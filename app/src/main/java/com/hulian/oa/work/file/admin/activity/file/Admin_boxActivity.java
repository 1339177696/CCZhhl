package com.hulian.oa.work.file.admin.activity.file;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hulian.oa.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.views.Dialog_list;
import com.hulian.oa.work.file.admin.adapter.MyGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Admin_boxActivity extends BaseActivity {
    MyGridViewAdapter adapter;
    private OptionsPickerView file_num,file_box,log_box;;
    List<String> listnum=new ArrayList<>();
    List<String> listbox=new ArrayList<>();
    List<String> listlog=new ArrayList<>();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_box_no)
    EditText etBoxNo;
    @BindView(R.id.et_box)
    EditText etBox;
    @BindView(R.id.gridview)
    GridView gridview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_box);
        ButterKnife.bind(this);
        initBoxLog();
        intRecycle();
        initNum();
        initBox();
    }

    private void intRecycle() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(i + "111");
        }

        adapter=new MyGridViewAdapter(mContext,list);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog_list dialog_list=new Dialog_list(Admin_boxActivity.this,"开柜","确定","取消",listlog);
                dialog_list.show();
            }
        });
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(Admin_boxActivity.this,"ddd",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    private void initNum() {
        listnum.add("1号文件柜");
        listnum.add("2号文件柜");
        file_num = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                etBoxNo.setText(listnum.get(options1));
            }
        }).setTitleText("请选择文件柜").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        file_num.setPicker(listnum);
    }

    private void initBox() {
        listbox.add("全部柜");
        listbox.add("传阅柜");
        listbox.add("归档柜");
        file_box = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                etBox.setText(listbox.get(options1));
            }
        }).setTitleText("请选择柜子类型").setContentTextSize(22).setTitleSize(22).setSubCalSize(21).build();
        file_box.setPicker(listbox);
    }
    private void initBoxLog() {
        listlog.add("张磊于2019-09-09入柜2019-09-09");
        listlog.add("张磊于2019-09-09入柜2019-09-09");
        listlog.add("张磊于2019-09-09入柜2019-09-09");
        listlog.add("张磊于2019-09-09入柜2019-09-09");

    }
    @OnClick({R.id.iv_back, R.id.et_box_no, R.id.et_box})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_box_no:
                file_num.show();
                break;
            case R.id.et_box:
                file_box.show();
                break;
        }
    }

}
