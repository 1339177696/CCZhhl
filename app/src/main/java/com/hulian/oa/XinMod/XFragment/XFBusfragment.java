package com.hulian.oa.XinMod.XFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hulian.oa.R;

/**
 * Created by qgl on 2019/12/16 10:41.
 */
public class XFBusfragment extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static XFBusfragment newInstance(String requestJson) {
        XFBusfragment fragment = new XFBusfragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_work_space, container, false);
        return view;
    }

}
