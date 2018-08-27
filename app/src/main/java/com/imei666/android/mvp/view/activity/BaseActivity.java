package com.imei666.android.mvp.view.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;

import com.imei666.android.mvp.view.LoadingDialog;

public class BaseActivity extends AppCompatActivity {

    private Dialog mDialog;

    public void showLoading(){
        if (mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
        mDialog = LoadingDialog.createLoadingDialog(this,"数据加载中，请稍候...",true);
        mDialog.show();
    }

    public void dismissDialog(){
        try {
            if (mDialog!=null&&mDialog.isShowing()){
                mDialog.dismiss();
                mDialog = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
