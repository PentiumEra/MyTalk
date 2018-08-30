package com.hoadong.mytalk;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.hoadong.common.app.BaseActivity;
import com.hoadong.common.ui.PortraitView;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements
BottomNavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.appbar)
    View mLayAppbar;
    @BindView(R.id.iv_avator)
    PortraitView mAvator;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.iv_search)
    ImageView mSearch;
    @BindView(R.id.lay_container)
    FrameLayout mContainer;
    @BindView(R.id.btn_floataction)
    FloatActionButton actionButton;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        mNavigationView.setOnNavigationItemSelectedListener(this);
        super.initWidget();
        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View,GlideDrawable>(mLayAppbar) {
                    //this  不是activity  是viewtarget
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            this.view.setBackground(resource.getCurrent());
                    }
                });
    }
    @OnClick(R.id.iv_avator)
    void onAvatorClick(){}

    @OnClick(R.id.iv_search)
    void onSearchMenuClick(){

    }
    @OnClick(R.id.btn_floataction)
    void onActionClick(){}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mTitle.setText(item.getTitle());
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
