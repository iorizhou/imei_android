package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.OrderDTO;
import com.imei666.android.utils.OrderFollow;
import com.imei666.android.utils.adapter.OrderFollowAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFollowFragment extends BaseFragment {

    private List<OrderFollow> traceList = new ArrayList<OrderFollow>();
    private OrderFollowAdapter mAdapter;
    private OrderDTO mOrderDTO;
    @BindView(R.id.orderdetail_follow_listview)
    ListView mListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_orderfollow,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderDTO = (OrderDTO)getArguments().getSerializable("order");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();

    }

    private void initListView(){
        //第一步，取创建时间
        traceList.add(new OrderFollow(mOrderDTO.getCreateDate(),"创建订单"));
        //如果付过钱了，取付款时间
        if (mOrderDTO.getPayStatus()==1){
            traceList.add(new OrderFollow(mOrderDTO.getPayDate(),"支付了订单"));
        }
        if (mOrderDTO.getOrderStatus()==2){
            traceList.add(new OrderFollow(mOrderDTO.getConsumeDate(),"到医院消费了"));
        }
        if (mOrderDTO.getOrderStatus()==3){
            traceList.add(new OrderFollow(mOrderDTO.getOrderInvalidTime(),"订单退款或过期了"));
        }
        Collections.reverse(traceList);
        mAdapter = new OrderFollowAdapter(getActivity(),traceList);
        mListView.setAdapter(mAdapter);
    }

    public static OrderFollowFragment newInStance(OrderDTO dto){
        OrderFollowFragment fragment = new OrderFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("order",dto);
        fragment.setArguments(bundle);
        return fragment;
    }
}
