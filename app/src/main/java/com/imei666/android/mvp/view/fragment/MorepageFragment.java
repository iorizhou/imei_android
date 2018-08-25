package com.imei666.android.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.view.activity.OrderListActivity;
import com.imei666.android.utils.adapter.ItemListAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MorepageFragment extends BaseFragment {


    @BindView(R.id.more_listview)
    ListView mListView;

    private View mHeaderView;
    private ItemListAdapter mAdapter;
    private List<ItemDTO> mItems = new ArrayList<ItemDTO>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_more,null);
        ButterKnife.bind(this,view);
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.view_morefragment_headerview,null);
        mListView.addHeaderView(mHeaderView);
        mAdapter = new ItemListAdapter(getActivity(),mItems);
        mListView.setAdapter(mAdapter);
        initHeaderView();
        return view;
    }

    private void initHeaderView(){
        ImageView avatar = mHeaderView.findViewById(R.id.more_view_avatar);
        avatar.setImageResource(R.drawable.ic_check);
        TextView username = mHeaderView.findViewById(R.id.more_view_username);
        username.setText("周鹏大哥");
        TextView updateInfo = mHeaderView.findViewById(R.id.more_view_updateinfo);
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(),"去改资料", Toast.LENGTH_SHORT).show();
            }
        });
        RelativeLayout orderInfoLayout = mHeaderView.findViewById(R.id.more_view_orderlist_layout);
        orderInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goOrderList(0);
            }
        });
        LinearLayout waitToPayLayout = mHeaderView.findViewById(R.id.more_view_waittopay_layout);
        waitToPayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(),"待支付", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout waitToUse = mHeaderView.findViewById(R.id.more_view_waittouse_layout);
        waitToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(),"待消费", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout tuikuan = mHeaderView.findViewById(R.id.more_view_refund_layout);
        tuikuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(),"退款", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goOrderList(int index){
        Intent intent = new Intent(getActivity(), OrderListActivity.class);
        intent.putExtra("index",index);
        getActivity().startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
