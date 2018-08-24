package com.imei666.android.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.imei666.android.R;
import com.imei666.android.mvp.model.dto.ItemDTO;
import com.imei666.android.utils.MyWebChromeClient;
import com.imei666.android.utils.MyWebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class ItemServiceDetailFragment extends BaseFragment {

    private ItemDTO mItemDTO;
    @BindView(R.id.item_service_webview)
    WebView mWebView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            mItemDTO = (ItemDTO) bundle.getSerializable("dto");
            Toasty.info(getActivity(),"mItemDTO = "+mItemDTO.getName(), Toast.LENGTH_SHORT).show();
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_itemdetail_service_detail,null);
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

        mWebView.loadUrl("https://www.qq.com");
    }

    public static ItemServiceDetailFragment newInStance(ItemDTO dto){
        ItemServiceDetailFragment fragment = new ItemServiceDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dto",dto);
        fragment.setArguments(bundle);
        return fragment;
    }
}
