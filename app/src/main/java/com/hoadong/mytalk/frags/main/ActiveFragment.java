package com.hoadong.mytalk.frags.main;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoadong.common.fragment.BaseFragment;
import com.hoadong.common.ui.GalleryView;
import com.hoadong.mytalk.R;

import butterknife.BindView;

/**
 * @author linghaoDo
 */
public class ActiveFragment extends BaseFragment {
    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }
}
