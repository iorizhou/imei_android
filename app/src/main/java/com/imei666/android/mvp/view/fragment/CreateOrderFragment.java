package com.imei666.android.mvp.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.model.dto.SubscriptionRedPacketDTO;
import com.imei666.android.mvp.model.dto.WKRedPacketDTO;
import com.imei666.android.mvp.model.dto.YYRedPacketDTO;
import com.imei666.android.mvp.view.LoadingDialog;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.DateUtil;
import com.imei666.android.utils.URLConstants;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class CreateOrderFragment extends BaseFragment {

    private ItemDTO mItemDTO;
    @BindView(R.id.createorder_itemname)
    TextView mItemName;
    @BindView(R.id.createorder_phonenum)
    TextView mPhoneNum;
    @BindView(R.id.createorder_liuyan)
    EditText mLiuYan;
    @BindView(R.id.createorder_discount)
    TextView mDiscount;
    @BindView(R.id.createorder_buycount)
    EditText mBuyCount;

    @BindView(R.id.createorder_djqcount)
    TextView mDJQMoney;
    @BindView(R.id.createorder_wkqcount)
    TextView mWKQMoney;
    @BindView(R.id.createorder_wkhbcount)
    TextView mYYHBMoney;


    @BindView(R.id.createorder_dj)
    TextView mDJ;
    @BindView(R.id.createorder_djyouhui)
    TextView mDJYouhui;
    @BindView(R.id.createorder_wkmoney)
    TextView mWkMoney;

    @BindView(R.id.createorder_zaixianfu)
    TextView mNeedPay;
    @BindView(R.id.createorder_btn)
    Button mSubmit;
    @BindView(R.id.needpay_tip)
    TextView mNeedPayTip;
    //订金红包
    private List<SubscriptionRedPacketDTO> mDJRedPacketList = new ArrayList<SubscriptionRedPacketDTO>();
    private long mSelectedDJQAmount = 0;
    private long mDJRedPacketId;
    //尾款红包(我们平台发放的)
    private List<WKRedPacketDTO> mWKRedPacketList = new ArrayList<WKRedPacketDTO>();
    private long mSelectedWKQAmount = 0;
    private long mWKRedPacketId;
    //医院红包(医院发放的)
    private List<YYRedPacketDTO> mYYRedPacketList = new ArrayList<YYRedPacketDTO>();
    private long mSelectedYYAmount = 0;
    private long mYYRedPacketId;

    private Dialog mDialog;
    private String mOrderId;

    private void showLoading(){
        if (mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
        mDialog = LoadingDialog.createLoadingDialog(getActivity(),"数据加载中，请稍候...",true);
        mDialog.show();
    }

    private void dismissLoading(){
        try{
            if (mDialog!=null && mDialog.isShowing()){
                mDialog.dismiss();
                mDialog = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateDJPayInfo(){
        mDJQMoney.setText(mSelectedDJQAmount==0?"0张可用":"立减"+mSelectedDJQAmount+"元");
        mNeedPay.setText((mItemDTO.getDjCount()-mSelectedDJQAmount)+"元");
        mNeedPayTip.setText((mItemDTO.getDjCount()-mSelectedDJQAmount)+"元");
        mDJYouhui.setText(mSelectedDJQAmount==0?"无优惠":"立减"+mSelectedDJQAmount+"元");
    }
    private void updateWKInfo(){
        mWKQMoney.setText(mSelectedWKQAmount==0?"0张可用":"立减"+mSelectedWKQAmount+"元");
        mWkMoney.setText((mItemDTO.getDiscountPrice()-mItemDTO.getDjCount()-mSelectedWKQAmount-mSelectedYYAmount)+"元");
        mYYHBMoney.setText(mSelectedYYAmount==0?"0张可用":"立减"+mSelectedYYAmount+"元");
    }
    private void fillData(){
        mItemName.setText(mItemDTO.getName());
        mPhoneNum.setText("17688783268");
        mDiscount.setText(mItemDTO.getDiscountPrice()+"元");
        mDJ.setText(mItemDTO.getDjCount()+"元");
        updateWKInfo();
        updateDJPayInfo();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(mBuyCount.getText().toString().trim())||Integer.parseInt(mBuyCount.getText().toString().trim())<=0){
                        Toasty.error(getActivity(),"购买数量必须大于等于1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showLoading();
                    submitOrder();
                }catch (Exception e){
                        Toasty.error(getActivity(),"购买数量必须大于等于1", Toast.LENGTH_SHORT).show();
                        return;
                }
            }
        });
    }


    public void submitOrder(){
        mSubmit.setClickable(false);
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("itemId",mItemDTO.getId()+"");
        paramMap.put("userId","1");
        paramMap.put("phoneNum",mPhoneNum.getText().toString().trim());
        paramMap.put("message",mLiuYan.getText().toString().trim());
        paramMap.put("buyCount",mBuyCount.getText().toString().trim());
        paramMap.put("djRedPacketId",mDJRedPacketId+"");
        paramMap.put("wkRedPacketId",mWKRedPacketId+"");
        paramMap.put("yyRedPacketId",mYYRedPacketId+"");

        new HttpPostTask().doPost(URLConstants.SUBMIT_ORDER, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissLoading();
                mSubmit.setClickable(true);
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "创建订单失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                mSubmit.setClickable(true);
                dismissLoading();
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){

                    Toasty.error(getActivity(), "创建订单失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mOrderId = jsonObject.getString("datas");

                Toasty.error(getActivity(), "创建订单成功:"+mOrderId, Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    //请求订金可用红包
    private void requestDJRedPacket(){
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("userId","1");
        paramMap.put("status","0");
        new HttpPostTask().doPost(URLConstants.GET_USER_DJ_REDPACKET, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                Log.i(TAG,"error = "+e.getMessage());
                mDJQMoney.setText("0张可用");
                mSelectedDJQAmount = 0;
                Toasty.error(getActivity(), "获取可用订金红包失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                updateDJPayInfo();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    mDJQMoney.setText("0张可用");
                    mSelectedDJQAmount = 0;
                    updateDJPayInfo();
                    Toasty.error(getActivity(), "获取可用订金红包失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mDJRedPacketList = JSON.parseArray(jsonObject.getString("datas"),SubscriptionRedPacketDTO.class);
                if (mDJRedPacketList == null || mDJRedPacketList.size()==0){
                    mDJQMoney.setText("0张可用");
                    mSelectedDJQAmount = 0;
                    updateDJPayInfo();
                }else {
                    SubscriptionRedPacketDTO packetDTO = mDJRedPacketList.get(0);
                    if (packetDTO==null||packetDTO.getAmount()==0||packetDTO.getStatus()!=0|| !DateUtil.isNowAvailable(packetDTO.getStartDate(),packetDTO.getEndDate())||packetDTO.getConditionAmount()>mItemDTO.getDiscountPrice()){
                        mDJQMoney.setText("0张可用");
                        mSelectedDJQAmount = 0;
                    }else {
                        mDJRedPacketId = packetDTO.getId();
                        mDJQMoney.setText("-￥"+packetDTO.getAmount()+"元");
                        mSelectedDJQAmount = packetDTO.getAmount();
                    }
                    updateDJPayInfo();
                }
            }
        });
    }

    //请求可用的尾款红包(我们平台发放的)
    private void requestWKRedPacket(){
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("userId","1");
        paramMap.put("status","0");
        new HttpPostTask().doPost(URLConstants.GET_USER_WK_REDPACKET, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                Log.i(TAG,"error = "+e.getMessage());
                mWKQMoney.setText("0张可用");
                mSelectedWKQAmount = 0;
                Toasty.error(getActivity(), "获取可用尾款红包失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                updateWKInfo();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    mWKQMoney.setText("0张可用");
                    mSelectedWKQAmount = 0;
                    updateWKInfo();
                    Toasty.error(getActivity(), "获取可用尾款红包失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mWKRedPacketList = JSON.parseArray(jsonObject.getString("datas"),WKRedPacketDTO.class);
                if (mWKRedPacketList == null || mWKRedPacketList.size()==0){
                    mWKQMoney.setText("0张可用");
                    mSelectedWKQAmount = 0;
                    updateWKInfo();
                }else {
                    WKRedPacketDTO packetDTO = mWKRedPacketList.get(0);
                    if (packetDTO==null||packetDTO.getAmount()==0||packetDTO.getStatus()!=0|| !DateUtil.isNowAvailable(packetDTO.getStartDate(),packetDTO.getEndDate())||packetDTO.getConditionAmount()>mItemDTO.getDiscountPrice()){
                        mWKQMoney.setText("0张可用");
                        mSelectedWKQAmount = 0;
                    }else {
                        mWKRedPacketId = packetDTO.getId();
                        mWKQMoney.setText("-￥"+packetDTO.getAmount()+"元");
                        mSelectedWKQAmount = packetDTO.getAmount();
                    }
                    updateWKInfo();
                }
            }
        });
    }

    //请求可用的医院红包(医院发放的)
    private void requestYYRedPacket(){
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("userId","1");
        paramMap.put("status","0");
        paramMap.put("hospitalId",mItemDTO.getHospitalId()+"");
        new HttpPostTask().doPost(URLConstants.GET_USER_YY_REDPACKET, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                Log.i(TAG,"error = "+e.getMessage());
                mYYHBMoney.setText("0张可用");
                mSelectedYYAmount = 0;
                Toasty.error(getActivity(), "获取可用医院红包失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                updateWKInfo();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    mYYHBMoney.setText("0张可用");
                    mSelectedYYAmount = 0;
                    updateWKInfo();
                    Toasty.error(getActivity(), "获取可用医院红包失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mYYRedPacketList = JSON.parseArray(jsonObject.getString("datas"),YYRedPacketDTO.class);
                if (mYYRedPacketList == null || mYYRedPacketList.size()==0){
                    mYYHBMoney.setText("0张可用");
                    mSelectedYYAmount = 0;
                    updateWKInfo();
                }else {
                    YYRedPacketDTO packetDTO = mYYRedPacketList.get(0);
                    if (packetDTO==null||packetDTO.getAmount()==0||packetDTO.getStatus()!=0|| !DateUtil.isNowAvailable(packetDTO.getStartDate(),packetDTO.getEndDate())||packetDTO.getConditionAmount()>mItemDTO.getDiscountPrice()||mItemDTO.getHospitalId()!=packetDTO.getHospitalId()){
                        mYYHBMoney.setText("0张可用");
                        mSelectedYYAmount = 0;
                    }else {
                        mYYRedPacketId = packetDTO.getId();
                        mYYHBMoney.setText("-￥"+packetDTO.getAmount()+"元");
                        mSelectedYYAmount = packetDTO.getAmount();
                    }
                    updateWKInfo();
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillData();
        requestDJRedPacket();
        requestWKRedPacket();
        requestYYRedPacket();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_createorder,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            mItemDTO = (ItemDTO) bundle.getSerializable("dto");
        }
        super.onCreate(savedInstanceState);
    }

    public static CreateOrderFragment newInStance(ItemDTO dto){
        CreateOrderFragment fragment = new CreateOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dto",dto);
        fragment.setArguments(bundle);
        return fragment;
    }
}
