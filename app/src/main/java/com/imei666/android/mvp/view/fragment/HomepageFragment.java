package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.Banner;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.URLConstants;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import okhttp3.Call;

public class HomepageFragment extends BaseFragment implements View.OnClickListener,AbsListView.OnScrollListener{
    private List<Banner> mBannerList = new ArrayList<Banner>();
    private PageBean mPageBean;
    @BindView(R.id.loop_viewpager_arc)
    BannerViewPager mViewPager;
    @BindView(R.id.bottom_zoom_arc)
    ZoomIndicator mViewPagerIndicator;

    private void requestBanners(){
        Map<String,String> paramMap = new HashMap<String, String>();
        paramMap.put("city","深圳");
        new HttpPostTask().doPost(URLConstants.GET_TOP_BANNER, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Crouton.makeText(getActivity(),"获取首页banner失败，请稍候重试", Style.INFO);
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Crouton.makeText(getActivity(),"获取首页banner失败:"+jsonObject.getString("msg")+"，请稍候重试", Style.INFO);
                    return;
                }
                mBannerList = JSON.parseArray(jsonObject.getString("datas"),Banner.class);
                mViewPagerIndicator.invalidate();
                mViewPager.invalidate();
            }
        });
    }

    private void initBannerView(){
        mPageBean = new PageBean.Builder<Banner>().setDataObjects(mBannerList).setIndicator(mViewPagerIndicator).builder();
        mViewPager.setPageTransformer(false,new MzTransformer());
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
                Crouton.makeText(getActivity(),"点击了banner", Style.INFO);

            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_homepage,container,false);
        ButterKnife.bind(view);
        initBannerView();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestBanners();
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
