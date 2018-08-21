package com.imei666.android.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.imei666.android.R;
import com.imei666.android.mvp.view.fragment.ItemDetailFragment;

public class ItemDetailActivity extends AppCompatActivity{

    private static final String TAG = ItemDetailActivity.class.getSimpleName();
    private long mId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getIntent().getExtras().getLong("id");
        setContentView(R.layout.activity_itemdetail);
        addFragment();
    }

    private void addFragment(){
        ItemDetailFragment fragment = ItemDetailFragment.newInStance(mId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.item_detail_layout, fragment,"item_detail").commitAllowingStateLoss();
    }
}
