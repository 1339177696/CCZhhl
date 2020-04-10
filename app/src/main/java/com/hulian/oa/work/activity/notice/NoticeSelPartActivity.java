package com.hulian.oa.work.activity.notice;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.hulian.oa.activity.BaseActivity;
import com.hulian.oa.R;
import com.hulian.oa.bean.Department;
import com.hulian.oa.work.activity.notice.adapter.MyAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeSelPartActivity extends BaseActivity {
    private ListView mListView;
    private Button bt_commit;
    private List<Department> models;
    private List<Department> models_sel_result;
    private CheckBox mMainCkb;
    private MyAdapter mMyAdapter;
    //监听来源
    public boolean mIsFromItem = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_sel_part);
        ButterKnife.bind(this);
        initView();
        initData();
        initViewOper();
    }

    /**
     * view初始化
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.list_main);
        mMainCkb = (CheckBox) findViewById(R.id.ckb_main);
        Drawable drawableWeiHui1 = getResources().getDrawable(R.drawable.zz_img_qgl1);
        drawableWeiHui1.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        mMainCkb.setCompoundDrawables(null, drawableWeiHui1, null, null);//只放上面

        bt_commit = findViewById(R.id.bt_commit);
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models_sel_result = new ArrayList<Department>();
                for (Department model : models) {
                    if (model.isIscheck()) {
                        models_sel_result.add(model);
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("partment", (Serializable) models_sel_result);
                setResult(1, intent);
                finish();
            }
        });
    }

    /**
     * 数据加载
     */
    private void initData() {
        models = (List<Department>) getIntent().getSerializableExtra("partment");
    }

    /**
     * 数据绑定
     */
    private void initViewOper() {
        mMyAdapter = new MyAdapter(models, this, new AllCheckListener() {

            @Override
            public void onCheckedChanged(boolean b) {
                //根据不同的情况对maincheckbox做处理
                if (!b && !mMainCkb.isChecked()) {
                    return;
                } else if (!b && mMainCkb.isChecked()) {
                    mIsFromItem = true;
                    mMainCkb.setChecked(false);
                } else if (b) {
                    mIsFromItem = true;
                    mMainCkb.setChecked(true);
                }
            }
        });
        mListView.setAdapter(mMyAdapter);
        //全选的点击监听
        mMainCkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //当监听来源为点击item改变maincbk状态时不在监听改变，防止死循环
                if (mIsFromItem) {
                    mIsFromItem = false;
                    Log.e("mainCheckBox", "此时我不可以触发");
                    return;
                }

                //改变数据
                for (Department model : models) {
                    model.setIscheck(b);
                }
                //刷新listview
                mMyAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    //对item导致maincheckbox改变做监听
    public interface AllCheckListener {
        void onCheckedChanged(boolean b);
    }
}
