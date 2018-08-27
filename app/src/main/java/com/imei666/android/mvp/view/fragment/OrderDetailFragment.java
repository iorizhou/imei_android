package com.imei666.android.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.OrderDTO;
import com.imei666.android.mvp.view.activity.ItemDetailActivity;
import com.imei666.android.mvp.view.activity.PayOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailFragment extends BaseFragment {
    @BindView(R.id.orderdetail_orderid)
    TextView mOrderId;
    @BindView(R.id.orderdetail_status)
    TextView mStatus;
    @BindView(R.id.orderdetail_phone)
    TextView mPhone;
    @BindView(R.id.orderdetail_createtime)
    TextView mCreateTime;
    @BindView(R.id.orderdetail_paychannel)
    TextView mPayChannel;
    @BindView(R.id.orderdetail_discount)
    TextView mDiscount;
    @BindView(R.id.orderdetail_buycount)
    TextView mCount;
    @BindView(R.id.orderdetail_wk)
    TextView mWK;
    @BindView(R.id.orderdetail_needpay)
    TextView mNeedPay;
    @BindView(R.id.orderdetail_itemname)
    TextView mItemName;
    @BindView(R.id.orderdetail_hospital)
    TextView mHospital;
    @BindView(R.id.orderdetail_price)
    TextView mPrice;
    @BindView(R.id.orderdetail_hospitaladdr)
    TextView mHospitalAddr;
    @BindView(R.id.ope_btn)
    Button mOpeBtn;

    private OrderDTO mOrderDTO;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mOrderDTO = (OrderDTO) bundle.getSerializable("order");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_orderdetail,null);
        ButterKnife.bind(this,view);
        return view;
    }

    public static OrderDetailFragment newInStance(OrderDTO dto){
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("order",dto);
        fragment.setArguments(bundle);
        return fragment;
    }


    private void fillData(){
        mOrderId.setText(mOrderId.getText().toString()+mOrderDTO.getId());
        String status = "";
        if (mOrderDTO.getOrderStatus()==0){
            status = "待支付";
        }else if (mOrderDTO.getOrderStatus()==1){
            status = "已支付，待消费";
        }else if (mOrderDTO.getOrderStatus()==2){
            status = "已消费完成";
        }else if (mOrderDTO.getOrderStatus()==3){
            status = "退款/已过期";
        }
        mCount.setText(mCount.getText().toString()+mOrderDTO.getBuyCount());
        mStatus.setText(mStatus.getText().toString()+status);
        mPhone.setText(mPhone.getText().toString()+"17688783268");
        mCreateTime.setText(mCreateTime.getText().toString()+mOrderDTO.getCreateDate());
        mPayChannel.setText(mPayChannel.getText().toString()+"支付订金");
        mDiscount.setText(mDiscount.getText().toString()+mOrderDTO.getTotalPrice());
        mWK.setText(mWK.getText().toString()+(mOrderDTO.getTotalPrice()-mOrderDTO.getDjTotalCount()));
        mNeedPay.setText(mNeedPay.getText().toString()+mOrderDTO.getNeedPayCount());
        mItemName.setText(mOrderDTO.getItemName());
        mHospital.setText("深圳博美");
        mHospitalAddr.setText("深圳市XXX");
        mPrice.setText(mOrderDTO.getTotalPrice()+"元");
        if (mOrderDTO.getOrderStatus()==0){
            mOpeBtn.setText("去支付");
            mOpeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), PayOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId",mOrderDTO.getId()+"");
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            });
            mOpeBtn.setVisibility(View.VISIBLE);
        }else if (mOrderDTO.getOrderStatus()==1){

        }else if (mOrderDTO.getOrderStatus()==2){
            mOpeBtn.setText("再次购买");
            mOpeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",mOrderDTO.getItemId());
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            });
            mOpeBtn.setVisibility(View.VISIBLE);
        }else if (mOrderDTO.getOrderStatus()==3){
            mOpeBtn.setText("再次购买");
            mOpeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("id",mOrderDTO.getItemId());
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            });
            mOpeBtn.setVisibility(View.VISIBLE);
        }
    }


}
