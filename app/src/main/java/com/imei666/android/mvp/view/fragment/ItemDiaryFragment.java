package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class ItemDiaryFragment extends BaseFragment {


    private long mItemId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            mItemId = bundle.getLong("itemid");
        }
        super.onCreate(savedInstanceState);
    }

    public static ItemDiaryFragment newInStance(long itemid){
        ItemDiaryFragment fragment = new ItemDiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("itemid",itemid);
        fragment.setArguments(bundle);
        return fragment;
    }
}
