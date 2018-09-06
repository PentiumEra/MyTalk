package com.hoadong.mytalk;

import android.app.Activity;
import android.os.Bundle;

import com.hoadong.common.app.BaseActivity;
import com.hoadong.mytalk.activities.MainActivity;
import com.hoadong.mytalk.frags.assist.PermissionsFragment;

public class LaunchActivity extends BaseActivity {


    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionsFragment.haveAll(this,getSupportFragmentManager())){
            MainActivity.show(this);
            finish();
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;

    }
}
