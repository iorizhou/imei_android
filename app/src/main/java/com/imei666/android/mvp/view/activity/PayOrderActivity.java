package com.imei666.android.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.view.fragment.CreateOrderFragment;
import com.imei666.android.mvp.view.fragment.PayOrderFragment;

public class PayOrderActivity extends AppCompatActivity {
    private static final String TAG = PayOrderActivity.class.getSimpleName();
    private String mOrderId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderId = getIntent().getExtras().getString("orderId");
        setContentView(R.layout.activity_payorder);
        addFragment();
    }

    private void addFragment(){
        PayOrderFragment fragment = PayOrderFragment.newInStance(mOrderId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.payorder_layout, fragment,"pay_order").commitAllowingStateLoss();
    }
}
