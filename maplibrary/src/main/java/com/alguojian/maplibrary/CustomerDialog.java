package com.alguojian.maplibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 可以滑动全屏的dialog
 *
 * @author alguojian
 * @date 2018/6/26
 */
public class CustomerDialog extends BottomSheetDialog {

    public CustomerDialog(@NonNull Context context) {
        super(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.buttomdialog, null);
        setContentView(dialogView);
    }
}
