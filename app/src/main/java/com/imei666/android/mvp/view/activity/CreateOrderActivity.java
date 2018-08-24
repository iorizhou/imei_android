package com.imei666.android.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.view.fragment.CreateOrderFragment;
import com.imei666.android.mvp.view.fragment.ItemDetailFragment;

public class CreateOrderActivity extends AppCompatActivity {
    private static final String TAG = CreateOrderActivity.class.getSimpleName();
    private ItemDTO mItemDTO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemDTO = (ItemDTO) getIntent().getExtras().getSerializable("dto");
        setContentView(R.layout.activity_createorder);
        addFragment();
    }

    private void addFragment(){
        CreateOrderFragment fragment = CreateOrderFragment.newInStance(mItemDTO);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.item_createorder_layout, fragment,"create_order").commitAllowingStateLoss();
    }
}
