package com.hoadong.common.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.ViewGroup;
import android.view.Window;

import com.hoadong.common.app.tools.UiTool;

import java.util.Objects;

/**
 * @auther linghaoDo QQ:1052354999
 */
public class TransStatusBottomSheetDialog  extends BottomSheetDialog {

    public TransStatusBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public TransStatusBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window window=getWindow();
        if (window==null){
            return;
        }
        int screenHeight= UiTool.getScreenHeight(Objects.requireNonNull(getOwnerActivity()));

        // 得到状态栏的高度
        int statusHeight= UiTool.getStatusBarHeight(getOwnerActivity());
        int dialogHeight=screenHeight-statusHeight;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                dialogHeight<=0?ViewGroup.LayoutParams.MATCH_PARENT:dialogHeight);
    }
}
