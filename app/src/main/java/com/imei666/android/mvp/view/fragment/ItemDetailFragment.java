package com.imei666.android.mvp.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.Banner;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.view.LoadingDialog;
import com.imei666.android.mvp.view.MyViewPager;
import com.imei666.android.mvp.view.activity.CreateOrderActivity;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.ProgressDialog;
import com.imei666.android.utils.URLConstants;
import com.imei666.android.utils.adapter.FragmentAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhengsr.viewpagerlib.anim.ZoomOutPageTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;
import com.zhengsr.viewpagerlib.indicator.TextIndicator;
import com.zhengsr.viewpagerlib.indicator.TransIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ItemDetailFragment extends BaseFragment {
    private long mId;
    private PageBean mPageBean;
    private ItemDTO mItemDTO;
    private List<String> mPicList = new ArrayList<String>();
    private List<String> mTabLayoutNameList = new ArrayList<String>();
    private List<BaseFragment> mTabFragmentList = new ArrayList<BaseFragment>();
    @BindView(R.id.itemdetail_loop_viewpager_arc)
    BannerViewPager mViewPager;
    @BindView(R.id.itemdetail_bottom_zoom_arc)
    TransIndicator mIndicator;
    @BindView(R.id.item_name)
    TextView mItemName;
    @BindView(R.id.item_discount)
    TextView mItemDiscount;
    @BindView(R.id.item_origPrice)
    TextView mItemOrigPrice;
    @BindView(R.id.item_type)
    TextView mItemType;
    @BindView(R.id.hospital_avatar)
    ImageView mHospitalImg;
    @BindView(R.id.hospital_name)
    TextView mHospitalName;
    @BindView(R.id.hospital_addr)
    TextView mHospitalAddr;

    @BindView(R.id.hospital_website)
    Button mHospitalWebsite;
    @BindView(R.id.hospital_go_map)
    Button mAddrBtn;
    @BindView(R.id.item_detail_line_indicator)
    TabIndicator mItemDetailIndicator;
    @BindView(R.id.item_detail_viewpager)
    MyViewPager mItemDetailViewPager;
    @BindView(R.id.item_detail_dj)
    TextView mDJTextView;
    @BindView(R.id.item_detail_wk)
    TextView mWkTextView;
    @BindView(R.id.item_detail_zixun)
    Button mZixunBtn;
    @BindView(R.id.item_detail_yd)
    Button mYDBtn;
    @BindView(R.id.item_detail_total_layout)
    RelativeLayout mTotalLayout;


    private PopupWindow mPopupWindow;





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
        showLoading();
        requestItem();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();
    }

    private void requestItem(){
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("id",mId+"");
        new HttpPostTask().doPost(URLConstants.GET_ITEM_DETAIL_BY_ID, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissDialog();
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取详情失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissDialog();
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取项目详情失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mItemDTO = JSON.parseObject(jsonObject.getString("datas"),ItemDTO.class);
                if (mItemDTO == null){
                    Toasty.error(getActivity(), "获取详情失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mPicList.add(mItemDTO.getCover());
                mPicList.add(mItemDTO.getCover());
                initBannerView();
                initDetailTabLayout();
                fillItemData();
            }
        });
    }

    private void fillItemData(){
        mItemName.setText(mItemDTO.getName());
        mItemDiscount.setText(mItemDTO.getDiscountPrice()+"");
        mItemOrigPrice.setText(mItemDTO.getOrigPrice()+"");
        mItemType.setText("项目: "+mItemDTO.getTypeId()+"");
        mHospitalName.setText(mItemDTO.getHospitalName());
        mHospitalAddr.setText(mItemDTO.getHospitalAddr());
        mHospitalWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(),"go website",Toast.LENGTH_SHORT).show();
            }
        });
        mAddrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(),"go website",Toast.LENGTH_SHORT).show();
            }
        });
        ImageLoader.getInstance().displayImage(mItemDTO.getHospitalCover(),mHospitalImg);
        mDJTextView.setText("在线支付: "+mItemDTO.getDjCount());
        mWkTextView.setText("到院再付: "+(mItemDTO.getDiscountPrice()-mItemDTO.getDjCount()));
        mZixunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(),"去聊天",Toast.LENGTH_SHORT).show();
            }
        });
        mYDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(),"去订单创建",Toast.LENGTH_SHORT).show();
                showOrderPopupWindow();
            }
        });
    }

    private void initBannerView(){
        mPageBean = new PageBean.Builder<String>().setDataObjects(mPicList).setIndicator(mIndicator).builder();
        mViewPager.setPageTransformer(false,new ZoomOutPageTransformer());
        mViewPager.setPageListener(mPageBean, R.layout.loop_layout, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object data) {
                ImageView imageView = view.findViewById(R.id.loop_icon);
                final String bean = (String) data;
                //imageloader加载图片
                mImageLoader.displayImage(bean,imageView);
                TextView textView = view.findViewById(R.id.loop_text);
                textView.setText("");

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        });
    }


    public static ItemDetailFragment newInStance(long id){
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            mId = bundle.getLong("id");
        }
        super.onCreate(savedInstanceState);
    }

    //初始化项目详情页下面的三个tab fragment 服务详情H5 预订流程H5 日记和案例

    private void initDetailTabLayout(){
        mTabLayoutNameList.add("服务详情");
        mTabLayoutNameList.add("预订流程");
        mTabLayoutNameList.add("日记和案例");
        ItemServiceDetailFragment serviceDetailFragment = ItemServiceDetailFragment.newInStance(mItemDTO);
        ItemYDFragment ydFragment = ItemYDFragment.newInStance(mItemDTO);
        ItemDiaryFragment diaryFragment = ItemDiaryFragment.newInStance(mItemDTO);
        mTabFragmentList.add(serviceDetailFragment);
        mTabFragmentList.add(ydFragment);
        mTabFragmentList.add(diaryFragment);
        mItemDetailViewPager.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager(),mTabFragmentList));
        mItemDetailIndicator.setHorizontalScrollBarEnabled(false);
        mItemDetailIndicator.setViewPagerSwitchSpeed(mItemDetailViewPager,600);
        mItemDetailIndicator.setTabData(mItemDetailViewPager,mTabLayoutNameList, new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int position) {
                mItemDetailViewPager.setCurrentItem(position);
            }
        });
    }

    private void showOrderPopupWindow(){
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.view_popupwindow_item_detail_createorder, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        TextView discount = (TextView)contentview.findViewById(R.id.item_detail_popup_discount);
        TextView dj = (TextView)contentview.findViewById(R.id.item_detail_popup_dj);
        TextView wk = (TextView)contentview.findViewById(R.id.item_detail_popup_wk);
        discount.setText(mItemDTO.getDiscountPrice()+"元");
        dj.setText(mItemDTO.getDjCount()+"元");
        wk.setText((mItemDTO.getDiscountPrice()-mItemDTO.getDjCount())+"元");
        Button kefuBtn = (Button)contentview.findViewById(R.id.item_detail_popup_kefu);
        Button createOrderBtn = (Button)contentview.findViewById(R.id.item_detail_popup_createorder);
        kefuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                Toasty.info(getActivity(),"客服",Toast.LENGTH_SHORT).show();
            }
        });
        createOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                Intent intent = new Intent(getActivity(), CreateOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dto",mItemDTO);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        mPopupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(false);
        contentview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mPopupWindow.dismiss();

                    return true;
                }
                return false;
            }
        });
        mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        mPopupWindow.showAtLocation(mTotalLayout,  Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
