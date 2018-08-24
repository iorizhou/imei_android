package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.utils.MyWebChromeClient;
import com.imei666.android.utils.MyWebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemYDFragment extends BaseFragment {


    private ItemDTO mItemDTO;
    @BindView(R.id.item_yd_webview)
    WebView mWebView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            mItemDTO = (ItemDTO) bundle.getSerializable("dto");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_itemdetail_yd,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSavePassword(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());

        mWebView.loadUrl("https://www.sina.com");
    }

    public static ItemYDFragment newInStance(ItemDTO dto){
        ItemYDFragment fragment = new ItemYDFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dto",dto);
        fragment.setArguments(bundle);
        return fragment;
    }
}
