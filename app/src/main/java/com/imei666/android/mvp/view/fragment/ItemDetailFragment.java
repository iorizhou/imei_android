package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imei666.android.R;
import com.imei666.android.mvp.view.LoadingDialog;
import com.zhengsr.viewpagerlib.indicator.TransIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailFragment extends BaseFragment {
    @BindView(R.id.itemdetail_loop_viewpager_arc)
    BannerViewPager mViewPager;
    @BindView(R.id.itemdetail_bottom_zoom_arc)
    TransIndicator mIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_itemdetail,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadingDialog.createLoadingDialog(getActivity(),"数据加载中，请稍候...",true).show();
    }
}
