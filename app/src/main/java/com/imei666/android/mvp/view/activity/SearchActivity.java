package com.imei666.android.mvp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.imei666.android.R;
import com.imei666.android.utils.SPUtils;
import com.imei666.android.utils.adapter.SearchHistoryAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.et_search)
    EditText mSearchEditText;
    @BindView(R.id.tv_cancel)
    TextView mCancel;
    @BindView(R.id.listView)
    ListView mHistoryListView;
    @BindView(R.id.tv_clear)
    TextView mClear;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private SearchHistoryAdapter mAdapter;
    private static final int HISTORY_MAX = 10;
    private List<String> mDatas = new ArrayList<String>();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //搜索请求
            Toast.makeText(SearchActivity.this,"联想加载请求",Toast.LENGTH_SHORT).show();
            //search(String.valueOf(mEditTextSearch.getText()));
        }
    };
    private static final int MSG_SEARCH = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        showSearchHistory();
    }

    private void initView(){
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) mSearchEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //搜索具体逻辑
                    //搜索请求转交给函数去处理：
                    //search(String.valueOf(mEditTextSearch.getText()));
                    if (TextUtils.isEmpty(mSearchEditText.getText().toString())){
                        Toasty.info(SearchActivity.this,"请输入搜索内容", Toast.LENGTH_SHORT, true).show();
                        return true;
                    }
                    saveSearchHistory(mSearchEditText.getText().toString().trim());
                    updateHistoryList();
                    Toasty.info(SearchActivity.this,"去搜索 : "+mSearchEditText.getText().toString(), Toast.LENGTH_SHORT, true).show();
                    return true;
                }
                return false;
            }
        });


        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //文字变动 ， 有未发出的搜索请求，应取消
                if(mHandler.hasMessages(MSG_SEARCH)){
                    mHandler.removeMessages(MSG_SEARCH);
                }
                //如果为空 直接显示搜索历史
                if(TextUtils.isEmpty(s)){
                    //showHistory();
                }else {//否则延迟500ms开始搜索
                    mHandler.sendEmptyMessageDelayed(MSG_SEARCH,500); //自动搜索功能 删除
                }
            }
        });
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearsearchHistory();
                updateHistoryList();
            }
        });
    }

    private void updateHistoryList(){
        mDatas.clear();
        Map<String, String> hisAll = (Map<String, String>) SPUtils.getAll(SearchActivity.this,"imei_search_sp");
        //将key排序升序
        Object[] keys = hisAll.keySet().toArray();
        Arrays.sort(keys);
        int keyLeng = keys.length;
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        int hisLeng = keyLeng > HISTORY_MAX ? HISTORY_MAX : keyLeng;
        for (int i = 1; i <= hisLeng; i++) {
            mDatas.add(hisAll.get(keys[keyLeng - i]));
        }
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
    }

    private void saveSearchHistory(String keyWords) {
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        Map<String, String> historys = (Map<String, String>) SPUtils.getAll(SearchActivity.this,"imei_search_sp");
        for (Map.Entry<String, String> entry : historys.entrySet()) {
            if(keyWords.equals(entry.getValue())){
                SPUtils.remove(SearchActivity.this,entry.getKey(),"imei_search_sp");
            }
        }
        SPUtils.put(SearchActivity.this, "" + sdf.format(new Date()), keyWords,"imei_search_sp");
    }

    private void showSearchHistory() {
        mAdapter = new SearchHistoryAdapter(SearchActivity.this);

        Map<String, String> hisAll = (Map<String, String>) SPUtils.getAll(SearchActivity.this,"imei_search_sp");
        //将key排序升序
        Object[] keys = hisAll.keySet().toArray();
        Arrays.sort(keys);
        int keyLeng = keys.length;
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        int hisLeng = keyLeng > HISTORY_MAX ? HISTORY_MAX : keyLeng;
        for (int i = 1; i <= hisLeng; i++) {
            mDatas.add(hisAll.get(keys[keyLeng - i]));
        }
        mAdapter.setDatas(mDatas);
        mHistoryListView.setAdapter(mAdapter);
    }


    private void clearsearchHistory() {
        SPUtils.clear(this,"imei_search_sp");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        delMoreSearchHistory();
    }

    private void delMoreSearchHistory() {

        Map<String, String> hisAll = (Map<String, String>) SPUtils.getAll(this,"imei_search_sp");
        if (hisAll.size() > HISTORY_MAX) {
            //将key排序升序
            Object[] keys = hisAll.keySet().toArray();
            Arrays.sort(keys);
            // LENGTH = 12 , MAX = 10 , I = 1,0,count =2;
            for (int i = keys.length - HISTORY_MAX - 1; i > -1; i--) {
                SPUtils.remove(this, (String) keys[i],"imei_search_sp");
            }
        }

    }
}
