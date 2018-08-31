package com.hoadong.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import com.hoadong.common.fragment.BaseFragment;

import java.util.List;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by linghaoDo on 2018/8/29
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentLayoutId();
    protected void initWidget(){
        ButterKnife.bind(this);
    }
    protected void initData(){

    }
    protected void initWindows(){

    }

    /**
     * 初始化相关参数
     * @param bundle
     * @return 如果参数正确 返回true  错误返回false
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())){
            //得到界面id并设置到界面中
            int layoutId=getContentLayoutId();
            setContentView(layoutId);
            initWidget();
            initData();
        }else {
            finish();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面返回时，finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //当activity中有多层fragment
        List<Fragment> fragments=getSupportFragmentManager().getFragments();
        if(fragments!=null&&fragments.size()>0){
            // 如果fragment是我们自己写的fragment(是否是我们自己能处理的类型)
            for(Fragment fragment:fragments){
                if (fragment instanceof BaseFragment){
                    // 判断是否拦截了返回按钮
                    if (((BaseFragment) fragment).onBackPressed()){
                        // 如果有 直接return
                        return;
                    }

                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
