package com.imei666.android.mvp.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.Banner;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.mvp.view.LoadingDialog;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.ProgressDialog;
import com.imei666.android.utils.URLConstants;
import com.zhengsr.viewpagerlib.anim.ZoomOutPageTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
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
    private List<String> mPicList = new ArrayList<String>();
    @BindView(R.id.itemdetail_loop_viewpager_arc)
    BannerViewPager mViewPager;
    @BindView(R.id.itemdetail_bottom_zoom_arc)
    TransIndicator mIndicator;
    private Dialog mDialog;
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
        mDialog = LoadingDialog.createLoadingDialog(getActivity(),"数据加载中，请稍候...",true);
        mDialog.show();
        requestItem();
    }


    private void dismissDialog(){
        try{
            if (mDialog!=null &&mDialog.isShowing()){
                mDialog.dismiss();
                mDialog = null;
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();
    }

    private void requestItem(){
        Toasty.error(getActivity(), "requestItem called", Toast.LENGTH_SHORT, true).show();
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
                ItemDTO dto = JSON.parseObject(jsonObject.getString("datas"),ItemDTO.class);
                if (dto == null){
                    Toasty.error(getActivity(), "获取详情失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                mPicList.add(dto.getCover());
                mPicList.add(dto.getCover());
                initBannerView();
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
}
