package com.hulian.oa.work.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hulian.oa.R;
import com.hulian.oa.work.file.admin.activity.WriteReportActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 陈泽宇 on 2020/3/10.
 * Describe:写汇报
 */
public class WriteReportFragment extends Fragment {

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_write_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.day_report, R.id.week_report, R.id.month_report})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), WriteReportActivity.class);
        switch (view.getId()) {
            case R.id.day_report:
                intent.putExtra("type","1");
                break;
            case R.id.week_report:
                intent.putExtra("type","2");
                break;
            case R.id.month_report:
                intent.putExtra("type","3");
                break;
        }
        startActivity(intent);
    }
}
