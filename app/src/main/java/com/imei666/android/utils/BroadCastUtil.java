package com.imei666.android.utils;

import android.content.Intent;
import android.os.Bundle;

import com.imei666.android.ImeiApplication;

public class BroadCastUtil {
    public static BroadCastUtil sInstance;

    private BroadCastUtil() {

    }

    public static synchronized BroadCastUtil getInstance() {
        if (sInstance==null) {
            sInstance = new BroadCastUtil();
        }
        return sInstance;
    }

    public void sendBroadcast(String action, Bundle bundle){
        Intent intent = new Intent(action);
        intent.putExtras(bundle);
        ImeiApplication.getInstace().sendBroadcast(intent);
    }
}
