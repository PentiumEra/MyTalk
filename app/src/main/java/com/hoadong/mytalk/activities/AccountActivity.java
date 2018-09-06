package com.hoadong.mytalk.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hoadong.common.app.BaseActivity;
import com.hoadong.mytalk.R;
import com.hoadong.mytalk.frags.account.UpdateInfoFragment;

public class AccountActivity extends BaseActivity {
    private Fragment mCurFragment=null;
    /**
     * 账户显示的入口
     * @param context
     */
    public static void show(Context context){
        context.startActivity(new Intent(context,AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurFragment=new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container_account,mCurFragment)
                .commit();
    }

    /**
     * 实现对应的fragment的数据回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurFragment.onActivityResult( requestCode,  resultCode,  data);

    }
}
