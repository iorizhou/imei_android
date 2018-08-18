package com.imei666.android.utils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.imei666.android.mvp.view.fragment.DiaryListFragment;

import java.util.List;

public class FragmentAdapter extends DragDetailFragmentPagerAdapter{
    List<DiaryListFragment> fragments = null;
    public FragmentAdapter(FragmentManager fm,List<DiaryListFragment> datas) {
        super(fm);
        this.fragments = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
