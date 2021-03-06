package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.model.dto.OrderDTO;
import com.imei666.android.mvp.model.dto.SubscriptionRedPacketDTO;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.DateUtil;
import com.imei666.android.utils.URLConstants;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class PayOrderFragment extends BaseFragment implements View.OnClickListener{

    private String mOrderId;
    private OrderDTO mOrderDTO;
    @BindView(R.id.alipaylayout)
    RelativeLayout mAlipayLayout;
    @BindView(R.id.wxpaylayout)
    RelativeLayout mWxPayLayout;
    @BindView(R.id.huabeilayout)
    RelativeLayout mHuabeiLayout;
    @BindView(R.id.gopaybtn)
    Button mPayBtn;
    @BindView(R.id.pay_amout)
    TextView mPayAmount;
    @BindView(R.id.alipay_checkbox)
    CheckBox mAlipayCheckBox;
    @BindView(R.id.weixinpay_checkbox)
    CheckBox mWXPayCheckBox;
    @BindView(R.id.huabei_checkbox)
    CheckBox mHuabeiCheckBox;
    private int mPayChannel = 0;


    //请求订单详情
    private void requestOrderDetail(){
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("userId","1");
        paramMap.put("orderId",mOrderId);
        new HttpPostTask().doPost(URLConstants.GET_ORDER_DETAIL, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                Log.i(TAG,"error = "+e.getMessage());

                Toasty.error(getActivity(), "获取订单详情失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                getActivity().finish();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissDialog();
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){

                    Toasty.error(getActivity(), "获取订单详情失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    getActivity().finish();
                    return;
                }
                mOrderDTO = JSON.parseObject(jsonObject.getString("datas"),OrderDTO.class);
                if (mOrderDTO==null){
                    Toasty.error(getActivity(), "获取订单详情失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    getActivity().finish();
                    return;
                }
                if (mOrderDTO.getPayStatus()!=0||mOrderDTO.getOrderStatus()!=0){
                    Toasty.error(getActivity(), "该笔订单状态异常，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    getActivity().finish();
                    return;
                }
                fillData();
                mPayBtn.setClickable(true);
                mPayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLoading();
                        mPayBtn.setClickable(false);
                        requestPayInfo();
                    }
                });
            }
        });
    }

    private void requestPayInfo(){
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("userId","1");
        paramMap.put("orderId",mOrderId);
        String url = URLConstants.GET_ALIPAY_INFO;
        new HttpPostTask().doPost(url, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                mPayBtn.setClickable(true);
                Log.i(TAG,"error = "+e.getMessage());

                Toasty.error(getActivity(), "获取支付信息失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissDialog();
                mPayBtn.setClickable(true);
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取支付信息失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                String payInfo = jsonObject.getString("datas");
                Toasty.error(getActivity(), "payinfo = "+payInfo, Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void fillData(){
        mPayAmount.setText(mOrderDTO.getNeedPayCount()+"元");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            mOrderId = bundle.getString("orderId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_payorder,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading();
        requestOrderDetail();
        mAlipayLayout.setOnClickListener(this);
        mWxPayLayout.setOnClickListener(this);
        mHuabeiLayout.setOnClickListener(this);
    }

    public static PayOrderFragment newInStance(String orderId){
        PayOrderFragment fragment = new PayOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderId",orderId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.alipaylayout:
                mPayChannel = 0;
                mAlipayCheckBox.setChecked(true);
                mWXPayCheckBox.setChecked(false);
                mHuabeiCheckBox.setChecked(false);
                break;
            case R.id.wxpaylayout:
                mPayChannel = 1;
                mAlipayCheckBox.setChecked(false);
                mWXPayCheckBox.setChecked(true);
                mHuabeiCheckBox.setChecked(false);
                break;

            case R.id.huabeilayout:
                mAlipayCheckBox.setChecked(false);
                mWXPayCheckBox.setChecked(false);
                mHuabeiCheckBox.setChecked(true);
                mPayChannel = 2;
                break;
        }
    }
}
