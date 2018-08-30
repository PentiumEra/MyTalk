package com.hoadong.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoadong.common.app.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by linghaoDo on 2018/8/29
 */
public abstract class BaseFragment extends SwipeBackFragment {
    //绑定页面
    public abstract Object setLayout();

    private Unbinder mUnbinder = null;
    private final SupportFragmentDelegate DELEGATE = new SupportFragmentDelegate(this);
    private View mRootView = null;

    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);

    // 标示是否第一次初始化数据
    protected boolean mIsFirstInitData = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("setLayout must be int or layout");
        }

        initWidget(rootView);

        onBindView(savedInstanceState, rootView);
        return rootView;

    }

    /**
     * 初始化控件
     *
     * @param rootView
     */
    private void initWidget(View rootView) {
        mUnbinder = ButterKnife.bind(this, rootView);
    }


    /**
     * 刚刚被activity调用时调用的方法
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());

    }

    /**
     * 在fragment中初始化参数
     *
     * @param bundle
     */
    protected void initArgs(Bundle bundle) {
    }

    /**
     * 当首次初始化数据的时候会调用的方法
     */
    private void onFirstInit() {

    }

    public final BaseActivity getBaseActivity() {
        return (BaseActivity) _mActivity;
    }


    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mIsFirstInitData) {
            // 触发一次以后就不会触发
            mIsFirstInitData = false;
            // 触发
            onFirstInit();
        }
        // 当View创建完成后初始化数据
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            //解除绑定
            mUnbinder.unbind();
        }
    }

    /**
     * 返回按键触发时调用
     *
     * @return 返回true 代表我已处理返回逻辑，activity不用自己finish
     * 返回false 代表我没有处理 activity走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }
}
