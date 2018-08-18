package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
import com.imei666.android.mvp.model.dto.Banner;
import com.imei666.android.mvp.model.dto.DiaryTypeDTO;
import com.imei666.android.mvp.model.dto.TypeDTO;
import com.imei666.android.mvp.view.MyViewPager;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.URLConstants;
import com.imei666.android.utils.adapter.FragmentAdapter;
import com.imei666.android.utils.adapter.GridViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhengsr.viewpagerlib.anim.ZoomOutPageTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;
import com.zhengsr.viewpagerlib.indicator.TransIndicator;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
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

public class HomepageFragment extends BaseFragment implements View.OnClickListener,AbsListView.OnScrollListener{
    private List<Banner> mBannerList = new ArrayList<Banner>();
    private PageBean mPageBean;
    private List<TypeDTO> mTypeList = new ArrayList<TypeDTO>();
    private GridViewAdapter mTypeGridAdapter;
    private List<DiaryTypeDTO> mDiaryTypeList = new ArrayList<DiaryTypeDTO>();
    private List<String> mDiaryTypeNameList = new ArrayList<String>();
    private List<DiaryListFragment> mDiaryListFragment = new ArrayList<DiaryListFragment>();
    private List<ActivityDTO> mActivityList = new ArrayList<ActivityDTO>();
    @BindView(R.id.activity1)
    ImageView mActivity1;
    @BindView(R.id.activity2)
    ImageView mActivity2;
    @BindView(R.id.activity3)
    ImageView mActivity3;
    @BindView(R.id.activity_layout)
    LinearLayout mActivityLayout;
    @BindView(R.id.loop_viewpager_arc)
    BannerViewPager mViewPager;
    @BindView(R.id.bottom_zoom_arc)
    TransIndicator mViewPagerIndicator;
    @BindView(R.id.staggeredGridView1)
    GridView mTypeGridView;
    @BindView(R.id.line_indicator)
    TabIndicator mDiaryTypeIndicator;
    @BindView(R.id.viewpager)
    MyViewPager mDiaryViewPager;




    private void requestBanners(){
        Map<String,String> paramMap = new HashMap<String, String>();
        paramMap.put("city","深圳");
        new HttpPostTask().doPost(URLConstants.GET_TOP_BANNER, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取首页banner失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取首页banner失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                Log.i(TAG,"datas = "+jsonObject.getString("datas"));
                mBannerList = JSON.parseArray(jsonObject.getString("datas"),Banner.class);
                Log.i(TAG,"mbannerlistsize"+mBannerList.size());
                initBannerView();
            }
        });
    }

    private void requestType(){
        Map<String,String> paramMap = new HashMap<String, String>();
        new HttpPostTask().doPost(URLConstants.GET_TOP_RECOMMEND_ITEM, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取首页banner失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取首页banner失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
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

    private void initTypeGrid(){

        mTypeGridAdapter = new GridViewAdapter(getActivity());

        mTypeGridAdapter.setDatas(mTypeList);
        mTypeGridView.setAdapter(mTypeGridAdapter);
    }

    private void initBannerView(){
        mPageBean = new PageBean.Builder<Banner>().setDataObjects(mBannerList).setIndicator(mViewPagerIndicator).builder();
        mViewPager.setPageTransformer(false,new ZoomOutPageTransformer());
        mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getActivity(), "点击了", Toast.LENGTH_SHORT, true).show();
            }
        });
        mViewPager.setPageListener(mPageBean, R.layout.loop_layout, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object data) {
                ImageView imageView = view.findViewById(R.id.loop_icon);
                Banner bean = (Banner) data;
                //imageloader加载图片
                mImageLoader.displayImage(bean.getPicUrl(),imageView);
                TextView textView = view.findViewById(R.id.loop_text);
                textView.setText(bean.getName());

                //如若你要设置点击事件，也可以直接通过这个view 来设置，或者图片的更新等等
                //TO-DO 跳转至不同的逻辑
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });

            }
        });
    }

    private void requestActivity(){
        Map<String,String> paramMap = new HashMap<String, String>();
        new HttpPostTask().doPost(URLConstants.GET_HOMEPAGE_ACTIVITY, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取活动失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取活动失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mActivityList.clear();
                mActivityList = JSON.parseArray(jsonObject.getString("datas"),ActivityDTO.class);
                Log.i(TAG,"mActivityList.size = "+mActivityList.size());

                initActivityView();
            }
        });
    }

    private void initActivityView(){
        if (mActivityList!=null && mActivityList.size()>0){
            mActivityLayout.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mActivityList.get(0).getPicUrl(),mActivity1);
            mActivity1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url",mActivityList.get(0).getJumpUrl());
                    bundle.putString("title",mActivityList.get(0).getName());
                    openWebViewActivity(getActivity(),bundle);
                }
            });
            if (mActivityList.size()>1){
                ImageLoader.getInstance().displayImage(mActivityList.get(1).getPicUrl(),mActivity2);
                mActivity2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url",mActivityList.get(1).getJumpUrl());
                        bundle.putString("title",mActivityList.get(1).getName());
                        openWebViewActivity(getActivity(),bundle);
                    }
                });
            }
            if (mActivityList.size()>2){
                ImageLoader.getInstance().displayImage(mActivityList.get(2).getPicUrl(),mActivity3);
                mActivity3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url",mActivityList.get(2).getJumpUrl());
                        bundle.putString("title",mActivityList.get(2).getName());
                        openWebViewActivity(getActivity(),bundle);
                    }
                });
            }
        }
    }

    private void requestDiaryType(){
        Map<String,String> paramMap = new HashMap<String, String>();
        new HttpPostTask().doPost(URLConstants.GET_DIARY_TYPE, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取日记分类，请稍候重试", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取日记分类失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mDiaryTypeNameList.clear();
                mDiaryListFragment.clear();
                mDiaryTypeList = JSON.parseArray(jsonObject.getString("datas"),DiaryTypeDTO.class);
                Log.i(TAG,"mDiaryTypeList.size = "+mDiaryTypeList.size());
                for (DiaryTypeDTO dto : mDiaryTypeList){
                    if (!mDiaryTypeNameList.contains(dto.getName())){
                        mDiaryTypeNameList.add(dto.getName());
                    }
                    DiaryListFragment fragment = DiaryListFragment.newInStance(dto.getId());
                    mDiaryListFragment.add(fragment);
                }
                initDiaryTypeView();
            }
        });
    }

    private void initDiaryTypeView(){
        mDiaryViewPager.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager(),mDiaryListFragment));


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
        View view = inflater.inflate(R.layout.fragment_homepage,container,false);
        ButterKnife.bind(this, view);
        //type先加一个全部项目
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setId(0);
        typeDTO.setName("全部项目");
        typeDTO.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534625361608&di=c7311ab113abb54c4dab31af2538d5c0&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D11b43609f7faaf5190ee89fce43dfe9b%2Ff2deb48f8c5494ee2fb0913627f5e0fe99257e0b.jpg");
        mTypeList.add(typeDTO);

//        initBannerView();
//        initTypeGrid();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestBanners();
        requestType();
        requestDiaryType();
        requestActivity();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
