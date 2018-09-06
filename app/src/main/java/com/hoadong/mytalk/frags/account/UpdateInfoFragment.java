package com.hoadong.mytalk.frags.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.hoadong.common.app.Application;
import com.hoadong.common.fragment.BaseFragment;
import com.hoadong.common.ui.GalleryView;
import com.hoadong.common.ui.PortraitView;
import com.hoadong.mytalk.R;
import com.hoadong.mytalk.frags.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 *@author  LinghaoDo QQ:1052354999
 *更新用户信息
 */
public class UpdateInfoFragment extends BaseFragment {

    @BindView(R.id.iv_avator_update_info)
    PortraitView mAvator=null;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }
    @OnClick(R.id.iv_avator_update_info)
    void onAvatorClick(){
        new GalleryFragment().setListener(new GalleryFragment.OnSelectedListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onSelectedImage(String path) {
                UCrop.Options options=new UCrop.Options();
                // 设置图片格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                //设置压缩后的图片精度
                options.setCompressionQuality(96);
                //得到头像的缓存地址
                File dPath= Application.getAvatorTmpFile();
                UCrop.of(Uri.fromFile(new File(path)),Uri.fromFile(dPath))
                        .withAspectRatio(1,1)
                        .withMaxResultSize(520,520)
                        .withOptions(options)
                        .start(getActivity());
            }
            //注意：使用的是getchildFragmentManager
        }).show(getChildFragmentManager(),GalleryFragment.class.getName());
    }

    /**
     * 首先是调用activity中的onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri!=null){
                //加载到图片中
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
    private void loadPortrait(Uri uri){
        Glide.with(getActivity())
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mAvator);
    }


}
