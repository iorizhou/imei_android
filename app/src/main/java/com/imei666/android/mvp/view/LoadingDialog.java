package com.imei666.android.mvp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.imei666.android.R;

public class LoadingDialog {



    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg,boolean cancelable) {

        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_loading_dialog, null);
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.dialog_view);

        SpinKitView tipText = (SpinKitView) view.findViewById(R.id.spin_kit);



        // 创建自定义样式的Dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        loadingDialog.setCancelable(cancelable);
        loadingDialog.setContentView(layout);

        return loadingDialog;
    }

}
