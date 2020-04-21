package com.hulian.oa.work.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hulian.oa.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：qgl 时间： 2020/4/20 11:11
 * Describe:我的考勤
 */
public class MaintenanceFragment extends Fragment {
    private Unbinder unbinder;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maintenancefragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
