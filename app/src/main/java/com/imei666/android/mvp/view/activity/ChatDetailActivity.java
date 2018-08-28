package com.imei666.android.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.imei666.android.R;
import com.imei666.android.mvp.view.fragment.ChatDetailFragment;
import com.imei666.android.mvp.view.fragment.PayOrderFragment;

public class ChatDetailActivity extends AppCompatActivity {


    private long mFriendId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFriendId = getIntent().getExtras().getLong("friendId");
        setContentView(R.layout.activity_chatdetail);
        addFragment();
    }

    private void addFragment(){
        ChatDetailFragment fragment = ChatDetailFragment.newInStance(mFriendId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.chatdetail_layout, fragment,"chat_detail").commitAllowingStateLoss();
    }
}
