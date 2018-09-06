package com.hoadong.mytalk.frags.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hoadong.common.app.tools.UiTool;
import com.hoadong.common.fragment.BaseFragment;
import com.hoadong.common.ui.GalleryView;
import com.hoadong.common.ui.dialogs.TransStatusBottomSheetDialog;
import com.hoadong.mytalk.R;

import net.qiujuer.genius.ui.Ui;

import java.util.Objects;

/**
 * @author LinghaoDo QQ:1052354999
 *图片选择fragment
 */
public class GalleryFragment extends BottomSheetDialogFragment implements
GalleryView.SelectedChangeListener{
    private GalleryView mGalleryView=null;
    private OnSelectedListener mOnSelectedListener=null;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TransStatusBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gallery,container,false);
        initWidget(view);

        return view;
    }

    /**
     *控件的初始化
     * @param view
     */
    private void initWidget(View view) {
        mGalleryView=view.findViewById(R.id.gallreyView_gallery_fragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGalleryView.setup(getLoaderManager(),this);
    }

    @Override
    public void onSelectedCountChanged(int count) {
        if (count>0){
            dismiss();
            if (mOnSelectedListener!=null){
                //获取所有选中的图片的路径
                String[] paths=mGalleryView.getSelectedPath();
                //返回第一张
                mOnSelectedListener.onSelectedImage(paths[0]);
                mOnSelectedListener=null;
            }
        }

    }

    /**
     * 设置事件监听器
     * @param listener
     * @return
     */
    public GalleryFragment setListener(OnSelectedListener listener){
        mOnSelectedListener=listener;
        return this;
    }

    /***
     * 选中图片的监听器
     */
    public interface  OnSelectedListener{
        void onSelectedImage(String path);
    }


}
