package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.OrderDTO;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.URLConstants;
import com.imei666.android.utils.adapter.OrderListAdapter;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class OrderListBaseFragment extends BaseFragment {

    @BindView(R.id.orderlist_listview)
    ListView mListView;
    private List<OrderDTO> mDatas = new ArrayList<OrderDTO>();
    private OrderListAdapter mAdapter;

    public void requestOrderList(int status){
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("userId","1");
        paramMap.put("status",status+"");
        new HttpPostTask().doPost(URLConstants.GET_ORDER_LIST, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                Log.i(TAG,"error = "+e.getMessage());

                Toasty.error(getActivity(), "获取订单列表失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissDialog();
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){

                    Toasty.error(getActivity(), "获取订单列表失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mDatas = JSON.parseArray(jsonObject.getString("datas"),OrderDTO.class);
                if (mDatas==null||mDatas.size()==0){
                    Toasty.error(getActivity(), "无订单", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                Toasty.error(getActivity(), "orderlist = "+mDatas.size(), Toast.LENGTH_SHORT, true).show();
                initListView();
            }
        });
    }

    private void initListView(){
        mAdapter = new OrderListAdapter(getActivity(),mDatas);
        mListView.setAdapter(mAdapter);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_waittopay,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading();
    }
}
