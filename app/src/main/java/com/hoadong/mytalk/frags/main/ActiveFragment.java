package com.hoadong.mytalk.frags.main;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoadong.common.fragment.BaseFragment;
import com.hoadong.mytalk.R;

/**
 * @author linghaoDo
 */
public class ActiveFragment extends BaseFragment {


    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public Object setLayout() {
        return R.layout.fragment_active;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
