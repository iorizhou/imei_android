package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.imei666.android.mvp.view.activity.MainActivity;
import com.imei666.android.utils.MessageNotificationDispatcher;

import es.dmoral.toasty.Toasty;

public class MsgpageFragment extends BaseFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).regMessageListener("msg_fragment", new MessageNotificationDispatcher.MessageListener() {
            @Override
            public void onMessage(String content) {
                Toasty.info(getActivity(),"消息来了 "+content, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
