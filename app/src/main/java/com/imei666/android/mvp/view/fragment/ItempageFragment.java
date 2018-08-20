package com.imei666.android.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ActivityDTO;
import com.imei666.android.mvp.model.dto.DiaryTypeDTO;
import com.imei666.android.mvp.model.dto.RecommendItemDTO;
import com.imei666.android.mvp.model.dto.TypeDTO;
import com.imei666.android.mvp.view.MyViewPager;
import com.imei666.android.mvp.view.activity.MainActivity;
import com.imei666.android.mvp.view.activity.SearchActivity;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.URLConstants;
import com.imei666.android.utils.adapter.FragmentAdapter;
import com.imei666.android.utils.adapter.GridViewAdapter;
import com.imei666.android.utils.adapter.ItemListFragmentAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ItempageFragment extends BaseFragment implements View.OnClickListener{

    private List<TypeDTO> mTypeList = new ArrayList<TypeDTO>();
    private GridViewAdapter mTypeGridAdapter;
    private List<ActivityDTO> mActivityList = new ArrayList<ActivityDTO>();
    private List<DiaryTypeDTO> mDiaryTypeList = new ArrayList<DiaryTypeDTO>();
    private List<String> mDiaryTypeNameList = new ArrayList<String>();
    private List<ItemListFragment> mItemListFragment = new ArrayList<ItemListFragment>();

    @BindView(R.id.staggeredGridView1)
    GridView mTypeGridView;
    @BindView(R.id.city)
    TextView mCityTextView;
    @BindView(R.id.search_et)
    TextView mSearchEditText;
    @BindView(R.id.activity_total_layout)
    LinearLayout mActivityLayout;
    @BindView(R.id.item_line_indicator)
    TabIndicator mDiaryTypeIndicator;
    @BindView(R.id.item_viewpager)
    MyViewPager mDiaryViewPager;


    private void initDiaryTypeView(){
        mDiaryViewPager.setAdapter(new ItemListFragmentAdapter(getActivity().getSupportFragmentManager(),mItemListFragment));


        mDiaryTypeIndicator.setHorizontalScrollBarEnabled(false);
        mDiaryTypeIndicator.setViewPagerSwitchSpeed(mDiaryViewPager,600);
        mDiaryTypeIndicator.setTabData(mDiaryViewPager,mDiaryTypeNameList, new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int position) {
                mDiaryViewPager.setCurrentItem(position);
            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_itempage,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSearchBanner();
        requestType();
        requestRecommendActivity();
        requestItemType();
    }

    @Override
    public void onClick(View view) {
        
    }

    private void requestRecommendActivity(){
        Map<String,String> paramMap = new HashMap<String, String>();
        paramMap.put("city",mCityTextView.getText().toString().trim());
        paramMap.put("index","0");
        paramMap.put("count","8");
        new HttpPostTask().doPost(URLConstants.GET_ITEMPAGE_RECOMMEND_ACTIVITY, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取推荐活动失败", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取推荐活动失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mActivityList = JSON.parseArray(jsonObject.getString("datas"),ActivityDTO.class);
//                initTypeGrid();
                initActivityView();
            }
        });
    }

    private void requestType(){
        Map<String,String> paramMap = new HashMap<String, String>();
        new HttpPostTask().doPost(URLConstants.GET_TOP_RECOMMEND_ITEM, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取类目分类失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取类目分类失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                Log.i(TAG,"datas = "+jsonObject.getString("datas"));
                List<TypeDTO> list = JSON.parseArray(jsonObject.getString("datas"),TypeDTO.class);
                mTypeList.addAll(list);
                Log.i(TAG,"mTypeList"+mTypeList.size());
                initTypeGrid();

            }
        });
    }

    private void requestItemType(){
        Map<String,String> paramMap = new HashMap<String, String>();
        new HttpPostTask().doPost(URLConstants.GET_DIARY_TYPE, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取分类失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取分类失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mDiaryTypeNameList.clear();
                mItemListFragment.clear();
                mDiaryTypeList = JSON.parseArray(jsonObject.getString("datas"),DiaryTypeDTO.class);
                Log.i(TAG,"mDiaryTypeList.size = "+mDiaryTypeList.size());
                for (DiaryTypeDTO dto : mDiaryTypeList){
                    if (!mDiaryTypeNameList.contains(dto.getName())){
                        mDiaryTypeNameList.add(dto.getName());
                    }
                    ItemListFragment fragment = ItemListFragment.newInStance(dto.getId(),mCityTextView.getText().toString());
                    mItemListFragment.add(fragment);
                }
                initDiaryTypeView();
            }
        });
    }

    private void initActivityView(){
        if (mActivityList==null||mActivityList.size()==0){
            return;
        }
        for (ActivityDTO dto : mActivityList){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_itempage_activity,null);
            ImageView img = (ImageView)view.findViewById(R.id.activity_img);
            ImageLoader.getInstance().displayImage(dto.getPicUrl(),img);
            LinearLayout layout = (LinearLayout)view.findViewById(R.id.item_layout);
            for (RecommendItemDTO recommendItemDTO : dto.getItems()){
                View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.view_itempage_item,null);
                ImageView itemPic = (ImageView)itemView.findViewById(R.id.item_pic);
                TextView itemName = (TextView)itemView.findViewById(R.id.item_name);
                TextView itemDiscount = (TextView)itemView.findViewById(R.id.discount);
                TextView itemOrig = (TextView)itemView.findViewById(R.id.orig_price);
                itemOrig.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
                ImageLoader.getInstance().displayImage(recommendItemDTO.getPicUrl(),itemPic);
                itemName.setText(recommendItemDTO.getName());
                itemDiscount.setText(recommendItemDTO.getDiscount()+"");
                itemOrig.setText(recommendItemDTO.getOrigPrice()+"");
                layout.addView(itemView);
            }
            if (dto.getItems().size()>0){
                //增加一个查看更多
                View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.view_itempage_item,null);
                ImageView itemPic = (ImageView)itemView.findViewById(R.id.item_pic);
                TextView itemName = (TextView)itemView.findViewById(R.id.item_name);
                ImageLoader.getInstance().displayImage("drawable://"+R.drawable.pic_logo,itemPic);
                itemName.setText("查看更多");
                layout.addView(itemView);
            }
            mActivityLayout.addView(view);
        }
    }

    private void initTypeGrid(){

        mTypeGridAdapter = new GridViewAdapter(getActivity());

        mTypeGridAdapter.setDatas(mTypeList);
        mTypeGridView.setAdapter(mTypeGridAdapter);
    }
    private void initSearchBanner(){
        mCityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).goSelectCity();;
                Toasty.info(getActivity(),"选择城市", Toast.LENGTH_SHORT, true).show();
            }
        });
        mSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                goIntent(intent);
                Toasty.info(getActivity(),"跳转到搜索activity", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

}
