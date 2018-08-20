package com.imei666.android.utils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.imei666.android.mvp.view.fragment.ItemListFragment;

import java.util.List;

public class ItemListFragmentAdapter extends DragDetailFragmentPagerAdapter{
    List<ItemListFragment> fragments = null;
    public ItemListFragmentAdapter(FragmentManager fm, List<ItemListFragment> datas) {
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
