package com.imei666.android.net;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;



public class HttpPostTask {

    public void doPost(String url, Map<String,String> paramMap, Callback call){
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);
        for (String key : paramMap.keySet()){
            postFormBuilder.addParams(key,paramMap.get(key));
        }
        RequestCall requestCall = postFormBuilder.build();
        requestCall.execute(call);
    }
}
