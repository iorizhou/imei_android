package com.imei666.android.mvp.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imei666.android.mvp.view.activity.WebViewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;


public class BaseFragment extends Fragment {
    public static final String TAG = BaseFragment.class.getSimpleName();
    public ImageLoader mImageLoader;


    public void onBackPressed(){
        Log.i(TAG,"onBackPressed");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImageLoader = ImageLoader.getInstance();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void openWebViewActivity(Context context,Bundle bundle){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void goIntent(Intent intent){
        getActivity().startActivity(intent);
    }


}
