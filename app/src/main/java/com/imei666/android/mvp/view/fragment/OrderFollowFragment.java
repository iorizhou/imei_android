package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.OrderDTO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFollowFragment extends BaseFragment {




    private OrderDTO mOrderDTO;

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

    }

    public static OrderFollowFragment newInStance(OrderDTO dto){
        OrderFollowFragment fragment = new OrderFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("order",dto);
        fragment.setArguments(bundle);
        return fragment;
    }
}
