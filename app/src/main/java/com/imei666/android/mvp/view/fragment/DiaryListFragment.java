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
import com.imei666.android.mvp.model.dto.Banner;
import com.imei666.android.mvp.model.dto.DiaryDTO;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.URLConstants;
import com.imei666.android.utils.adapter.DiaryListAdapter;
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

public class DiaryListFragment extends Fragment {
    public static final String TAG = DiaryListFragment.class.getSimpleName();
    private List<DiaryDTO> mDiaryList = new ArrayList<DiaryDTO>();
    private List<Long> mDiaryIdList = new ArrayList<Long>();
    private DiaryListAdapter mAdapter;
    @BindView(R.id.paging_list_view)
    PagingListView mListView;
    private long mTypeid;
    private int mIndex;
    private int mCount = 10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_diarylist,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void requestDiary(){
        Map<String,String> paramMap = new HashMap<String, String>();
        paramMap.put("typeid",mTypeid+"");
        paramMap.put("index",mIndex + "");
        paramMap.put("count",mCount + "");
        new HttpPostTask().doPost(URLConstants.GET_DIARYLIST_BY_TYPEID, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG,"error = "+e.getMessage());
                Toasty.error(getActivity(), "获取日记失败，请稍候重试", Toast.LENGTH_SHORT, true).show();
                mListView.setIsLoading(false);
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    Toasty.error(getActivity(), "获取日记失败:"+jsonObject.getString("msg")+"，请稍候重试", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                List<DiaryDTO> list = JSON.parseArray(jsonObject.getString("datas"),DiaryDTO.class);
                if (list==null || list.size()==0){
                    mListView.setHasMoreItems(false);
                    mListView.setIsLoading(false);
                    return;
                }
                for (DiaryDTO dto: list){
                    if (!mDiaryIdList.contains(dto.getId())){
                    mDiaryList.add(dto);
                        mDiaryIdList.add(dto.getId());
                    }
                }

                mListView.setHasMoreItems(list.size()<mCount?false:true);
                mListView.setIsLoading(false);
                mIndex += list.size();
                Log.i(TAG,"mDiaryList.size = "+mDiaryList.size());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initListView(){
        mAdapter = new DiaryListAdapter(getActivity(),mDiaryList);
        mListView.setHasMoreItems(false);
        mListView.setFocusable(false);
        mListView.setAdapter(mAdapter);
        mListView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestDiary();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestDiary();
        initListView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            Log.i("diarylist","savedInstanceState != null");
            mTypeid = bundle.getLong("typeid");
        }
        super.onCreate(savedInstanceState);
    }

    public static DiaryListFragment newInStance(long typeid){
        Log.i("diarylist","newInStance");
        DiaryListFragment fragment = new DiaryListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("typeid",typeid);
        fragment.setArguments(bundle);
        return fragment;
    }



}
