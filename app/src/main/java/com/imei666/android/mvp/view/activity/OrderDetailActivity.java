package com.imei666.android.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.OrderDTO;
import com.imei666.android.mvp.view.MyViewPager;
import com.imei666.android.mvp.view.fragment.InvalidOrderListFragment;
import com.imei666.android.mvp.view.fragment.OrderDetailFragment;
import com.imei666.android.mvp.view.fragment.OrderFollowFragment;
import com.imei666.android.mvp.view.fragment.WaitToPayOrderListFragment;
import com.imei666.android.mvp.view.fragment.WaitToUseOrderListFragment;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.URLConstants;
import com.imei666.android.utils.adapter.FragmentAdapter;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class OrderDetailActivity extends BaseActivity {

    private OrderDTO mDto;
    @BindView(R.id.orderdetail_indicator)
    TabIndicator mTabIndicator;
    @BindView(R.id.orderdetail_viewpager)
    MyViewPager mViewPager;

    private List<String> mTabName = new ArrayList<String>();
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private OrderDTO mOrderDTO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
        mDto = (OrderDTO) getIntent().getExtras().getSerializable("order");
        initViewPager();
    }

    private void initViewPager(){
        mTabName.add("订单详情");
        mTabName.add("订单追踪");
        mFragmentList.add(OrderDetailFragment.newInStance(mDto));
        mFragmentList.add(OrderFollowFragment.newInStance(mDto));
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),mFragmentList));

        mTabIndicator.setHorizontalScrollBarEnabled(false);
        mTabIndicator.setViewPagerSwitchSpeed(mViewPager,600);
        mTabIndicator.setTabData(mViewPager,mTabName, new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int position) {
                mViewPager.setCurrentItem(position);
            }
        });
        mViewPager.setCurrentItem(0);

    }

}
