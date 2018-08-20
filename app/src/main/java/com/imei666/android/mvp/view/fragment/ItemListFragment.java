package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.DiaryDTO;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.URLConstants;
import com.imei666.android.utils.adapter.DiaryListAdapter;
import com.imei666.android.utils.adapter.ItemListAdapter;
import com.paging.listview.PagingListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ItemListFragment extends Fragment {
    public static final String TAG = ItemListFragment.class.getSimpleName();
    private List<ItemDTO> mItemList = new ArrayList<ItemDTO>();
    private List<Long> mItemIdList = new ArrayList<Long>();
    private ItemListAdapter mAdapter;
    @BindView(R.id.paging_list_view)
    PagingListView mListView;
    private long mTypeid;
    private String mCity;
    private int mIndex;
    private int mCount = 10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toasty.error(getActivity(), "itemlist onCreateView called", Toast.LENGTH_SHORT, true).show();
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_itemlist,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void requestItem(){
        Toasty.error(getActivity(), "requestItem called", Toast.LENGTH_SHORT, true).show();
        Map<String,String> paramMap = new HashMap<String, String>();
        paramMap.put("typeid",mTypeid+"");
        paramMap.put("index",mIndex + "");
        paramMap.put("count",mCount + "");
        paramMap.put("city",mCity);
        new HttpPostTask().doPost(URLConstants.GET_ITEMPAGE_ITEMLIST_BY_ID, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取项目失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                mListView.setIsLoading(false);
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取项目失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                List<ItemDTO> list = JSON.parseArray(jsonObject.getString("datas"),ItemDTO.class);
                if (list==null || list.size()==0){
                    mListView.setHasMoreItems(false);
                    mListView.setIsLoading(false);
                    return;
                }
                for (ItemDTO dto: list){
                    if (!mItemIdList.contains(dto.getId())){
                        mItemList.add(dto);
                        mItemIdList.add(dto.getId());
                    }
                }

                mListView.setHasMoreItems(list.size()<mCount?false:true);
                mListView.setIsLoading(false);
                mIndex += list.size();
                Log.i(TAG,"mItemList.size = "+mItemList.size());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initListView(){
        mAdapter = new ItemListAdapter(getActivity(),mItemList);
        mListView.setHasMoreItems(false);
        mListView.setFocusable(false);
        mListView.setAdapter(mAdapter);
        mListView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestItem();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
        requestItem();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Toasty.error(getActivity(), "onCreate called", Toast.LENGTH_SHORT, true).show();
        Bundle bundle = getArguments();
        if (bundle!=null){
            mTypeid = bundle.getLong("typeid");
            mCity = bundle.getString("city");
        }
        super.onCreate(savedInstanceState);
    }

    public static ItemListFragment newInStance(long typeid,String city){

        ItemListFragment fragment = new ItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("typeid",typeid);
        bundle.putString("city",city);
        fragment.setArguments(bundle);
        return fragment;
    }



}
