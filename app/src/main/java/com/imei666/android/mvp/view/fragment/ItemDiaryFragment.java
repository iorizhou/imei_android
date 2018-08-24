package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.imei666.android.mvp.model.dto.ItemDTO;

public class ItemDiaryFragment extends BaseFragment {


    private ItemDTO mItemDTO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            mItemDTO = (ItemDTO) bundle.getSerializable("dto");
        }
        super.onCreate(savedInstanceState);
    }

    public static ItemDiaryFragment newInStance(ItemDTO dto){
        ItemDiaryFragment fragment = new ItemDiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dto",dto);
        fragment.setArguments(bundle);
        return fragment;
    }
}
