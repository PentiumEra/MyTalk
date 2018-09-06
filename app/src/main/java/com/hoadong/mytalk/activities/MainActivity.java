package com.hoadong.mytalk.activities;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.hoadong.common.app.BaseActivity;
import com.hoadong.common.ui.PortraitView;
import com.hoadong.mytalk.R;
import com.hoadong.mytalk.frags.main.ActiveFragment;
import com.hoadong.mytalk.frags.main.ContactFragment;
import com.hoadong.mytalk.frags.main.GroupFragment;
import com.hoadong.mytalk.helper.NavHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {
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
    private NavHelper<Integer> mNavHelper = null;

    /**
     * 显示入口
     * @param context
     */
    public static void show(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        // 初始化底部按钮工具类
        mNavHelper = new NavHelper<Integer>(MainActivity.this, R.id.lay_container, getSupportFragmentManager(), this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<Integer>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_group, new NavHelper.Tab<Integer>(GroupFragment.class, R.string.title_group))
                .add(R.id.action_contact, new NavHelper.Tab<Integer>(ContactFragment.class, R.string.title_contact));
        // 底部menu的点击监听
        mNavigationView.setOnNavigationItemSelectedListener(this);
        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    //this  不是activity  是viewtarget
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        //从底部接管我们的menu，然后进行手动的触发第一次点击
        Menu menu = mNavigationView.getMenu();
        //触发首次选中home
        menu.performIdentifierAction(R.id.action_home, 0);

    }

    @OnClick(R.id.iv_avator)
    void onAvatorClick() {
        AccountActivity.show(this);
    }

    @OnClick(R.id.iv_search)
    void onSearchMenuClick() {

    }

    @OnClick(R.id.btn_floataction)
    void onActionClick() {
    }

    /**
     * @param item 传入的menu
     * @return true 能够处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mTitle.setText(item.getTitle());
        // 转接事件流到工具类
        return mNavHelper.performClickMenu(item.getItemId());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        mTitle.setText(newTab.extra);
        //对浮动按钮动画的处理
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_home)) {
            transY = Ui.dipToPx(getResources(), 76);
        } else {
            if (Objects.equals(newTab.extra, R.string.title_group)) {
                actionButton.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
            } else {
                actionButton.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;
            }
        }
            // 开始动画
            // 旋转，Y轴位移，弹性差值器，时间
         actionButton.animate()
                    .rotation(rotation)
                    .translationY(transY)
                    .setInterpolator(new AnticipateOvershootInterpolator(1))
                    .setDuration(480)
                    .start();

    }
}
