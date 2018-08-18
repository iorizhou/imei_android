package com.imei666.android.utils.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    List<View> mViewList = new ArrayList<View>();

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    ViewPager viewPager;
    private void setViewList(List<View> datas){
        this.mViewList = datas;
    }


    public MyPagerAdapter(){

    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        viewPager.addView(mViewList.get(position));
        return mViewList.get(position);
    }
}
