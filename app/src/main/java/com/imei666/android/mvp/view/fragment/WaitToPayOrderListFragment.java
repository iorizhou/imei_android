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

//Status为订单状态 ，-1为全部订单  0为创建成功(待支付)  1为已支付（待消费） 2为已消费（待写日记）  3为退款或过期(退款/过期)

public class WaitToPayOrderListFragment extends OrderListBaseFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestOrderList(0);
    }
}
