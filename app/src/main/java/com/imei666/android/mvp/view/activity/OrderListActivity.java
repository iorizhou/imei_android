package com.imei666.android.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.imei666.android.R;
import com.imei666.android.mvp.view.MyViewPager;
import com.imei666.android.mvp.view.fragment.InvalidOrderListFragment;
import com.imei666.android.mvp.view.fragment.PayOrderFragment;
import com.imei666.android.mvp.view.fragment.WaitToPayOrderListFragment;
import com.imei666.android.mvp.view.fragment.WaitToUseOrderListFragment;
import com.imei666.android.utils.adapter.FragmentAdapter;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;
import com.zhengsr.viewpagerlib.indicator.TransIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActivity extends AppCompatActivity {

    private int mIndex;
    @BindView(R.id.orderlist_indicator)
    TabIndicator mTabIndicator;
    @BindView(R.id.orderlist_viewpager)
    MyViewPager mViewPager;
    private List<String> mTabName = new ArrayList<String>();
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndex = getIntent().getIntExtra("index",0);
        setContentView(R.layout.activity_orderlist);
        ButterKnife.bind(this);

        initViewPager();
    }



    private void initViewPager(){
        mTabName.add("待支付");
        mTabName.add("待消费");
        mTabName.add("退款/过期");
        mFragmentList.add(new WaitToPayOrderListFragment());
        mFragmentList.add(new WaitToUseOrderListFragment());
        mFragmentList.add(new InvalidOrderListFragment());
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),mFragmentList));


        mTabIndicator.setHorizontalScrollBarEnabled(false);
        mTabIndicator.setViewPagerSwitchSpeed(mViewPager,600);
        mTabIndicator.setTabData(mViewPager,mTabName, new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int position) {
                mViewPager.setCurrentItem(position);
            }
        });
        mViewPager.setCurrentItem(mIndex);

    }
}
