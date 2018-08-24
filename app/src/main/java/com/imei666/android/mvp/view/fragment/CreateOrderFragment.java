package com.imei666.android.mvp.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.view.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

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
    TextView mWKHBMoney;


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


    private Dialog mDialog;

    private void showLoading(){
        if (mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
        mDialog = LoadingDialog.createLoadingDialog(getActivity(),"数据加载中，请稍候...",true);
        mDialog.show();
    }

    private void fillData(){
        mItemName.setText(mItemDTO.getName());
        mPhoneNum.setText("17688783268");
        mDiscount.setText(mItemDTO.getDiscountPrice()+"元");
        mDJQMoney.setText("-￥50");
        mWKQMoney.setText("0张可用");
        mWKHBMoney.setText("0张可用");
        mDJ.setText(mItemDTO.getDjCount()+"元");
        mDJYouhui.setText(mDJQMoney.getText());
        mWkMoney.setText((mItemDTO.getDiscountPrice()-mItemDTO.getDjCount())+"元");
        mNeedPay.setText((mItemDTO.getDjCount()-50)+"元");
        mNeedPayTip.setText((mItemDTO.getDjCount()-50)+"元");
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(mBuyCount.getText().toString().trim())||Integer.parseInt(mBuyCount.getText().toString().trim())<=0){
                        Toasty.error(getActivity(),"购买数量必须大于等于1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showLoading();
                }catch (Exception e){
                    if (TextUtils.isEmpty(mBuyCount.getText().toString().trim())||Integer.parseInt(mBuyCount.getText().toString().trim())<=0){
                        Toasty.error(getActivity(),"购买数量必须大于等于1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillData();
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
