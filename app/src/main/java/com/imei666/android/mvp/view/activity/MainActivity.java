package com.imei666.android.mvp.view.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.imei666.android.ImeiApplication;
import com.imei666.android.R;
import com.imei666.android.db.DBUtil;
import com.imei666.android.mvp.model.dto.MessageDTO;
import com.imei666.android.mvp.model.dto.MessageDTODao;
import com.imei666.android.mvp.model.dto.MessageListDTO;
import com.imei666.android.mvp.view.fragment.BaseFragment;
import com.imei666.android.mvp.view.fragment.HomepageFragment;
import com.imei666.android.mvp.view.fragment.ItempageFragment;
import com.imei666.android.mvp.view.fragment.MorepageFragment;
import com.imei666.android.mvp.view.fragment.MsgpageFragment;
import com.imei666.android.utils.Constants;
import com.imei666.android.utils.MessageNotificationDispatcher;
import com.lljjcoder.style.citylist.CityListSelectActivity;
import com.lljjcoder.style.citylist.bean.CityInfoBean;
import com.lljjcoder.style.citylist.utils.CityListLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseFragmentActivity implements EasyPermissions.PermissionCallbacks {


    public static final String TAG = "MainActivity";
    private Fragment mCurFragment;
    private LinearLayout mLayout1,mLayout2,mLayout3,mLayout4;
    private BaseFragment mHomepageFragment,mItemFrgment,mMsgFragment,mMoreFragment;
    @BindView(R.id.text1)
    TextView mText1;
    @BindView(R.id.text2)
    TextView mText2;
    @BindView(R.id.text3)
    TextView mText3;
    @BindView(R.id.text4)
    TextView mText4;
    ImageView mImg1,mImg2,mImg3,mImg4;
    FragmentManager mFragmentManager;
    private BroadcastReceiver mMessageReceiver;
    public void goSelectCity(){
        Intent intent = new Intent(MainActivity.this, CityListSelectActivity.class);
        startActivityForResult(intent, CityListSelectActivity.CITY_SELECT_RESULT_FRAG);
    }

    public void regMessageListener(String name, MessageNotificationDispatcher.MessageListener listener){
        MessageNotificationDispatcher.getInstance().regMessageNotification(name,listener);
    }

    //注册消息message通知
    private void regMessageBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.NEW_MSG_ACTION);
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle==null||bundle.getString("msg")==null||bundle.getString("msg").trim().equals("")){
                    return;
                }
                MessageDTO dto = JSONObject.parseObject(bundle.getString("msg"),MessageDTO.class);
                boolean isOWnSend = 1==dto.getSenderId()?true:false;
                dto.setOwnSend(isOWnSend);
                DBUtil.getInstance(ImeiApplication.getInstace()).getDaoSession().getMessageDTODao().insert(dto);
                //消息存好了，此时开始更新messagelist

                MessageListDTO messageListDTO = new MessageListDTO();
                long friendId = isOWnSend?dto.getRecverId():dto.getSenderId();
                messageListDTO.setFriendId(friendId);
                messageListDTO.setContent(dto.getContent());
                messageListDTO.setFriendAvatar(isOWnSend?dto.getRecverAvatar():dto.getSenderAvatar());
                messageListDTO.setFriendName(isOWnSend?dto.getRecverName():dto.getSenderName());
                messageListDTO.setTime(dto.getSendTime());
                DBUtil.getInstance(MainActivity.this).getDaoSession().getMessageListDTODao().insertOrReplace(messageListDTO);
                MessageNotificationDispatcher.getInstance().notifyAll(bundle.getString("msg"));
            }
        };
        registerReceiver(mMessageReceiver,filter);
    }

    private void asyncInitCityData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                CityListLoader.getInstance().loadCityData(MainActivity.this);
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        asyncInitCityData();
        requireSomePermission();

        mLayout1 = (LinearLayout)findViewById(R.id.frag1);
        mLayout2 = (LinearLayout)findViewById(R.id.frag2);
        mLayout3 = (LinearLayout)findViewById(R.id.frag3);
        mLayout4 = (LinearLayout)findViewById(R.id.frag4);
        mText1.setText("butterknife");
        mImg1 = (ImageView)findViewById(R.id.img1);
        mImg2 = (ImageView)findViewById(R.id.img2);
        mImg3 = (ImageView)findViewById(R.id.img3);
        mImg4 = (ImageView)findViewById(R.id.img4);
        mFragmentManager = getSupportFragmentManager();
        mHomepageFragment = new HomepageFragment();
        mItemFrgment = new ItempageFragment();
        mMsgFragment = new MsgpageFragment();
        mMoreFragment = new MorepageFragment();
        mLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
                addOrShowFragment(mTransaction,mHomepageFragment,R.id.details,"homepage");
            }
        });
        mLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
                addOrShowFragment(mTransaction,mItemFrgment,R.id.details,"itempage");
            }
        });
        mLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
                addOrShowFragment(mTransaction,mMsgFragment,R.id.details,"msgpage");
            }
        });
        mLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
                addOrShowFragment(mTransaction,mMoreFragment,R.id.details,"morepage");
            }
        });
        mFragmentManager.beginTransaction().add(R.id.details, mHomepageFragment,"homepage").commitAllowingStateLoss();
        mCurFragment = mHomepageFragment;
        updateDocker();
        regMessageBroadcast();
    }

    private void addOrShowFragment(FragmentTransaction transaction, BaseFragment fragment, int id, String tag) {


        if(mCurFragment == fragment)
            return;
        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(mCurFragment).add(id, fragment,tag).commitAllowingStateLoss();
        } else {
            transaction.hide(mCurFragment).show(fragment).commitAllowingStateLoss();
        }
        if (mCurFragment!=null){
            mCurFragment.setUserVisibleHint(false);
        }
        mCurFragment =  fragment;
        if (mCurFragment!=null){
            mCurFragment.setUserVisibleHint(true);
        }
        updateDocker();
    }

    public void updateDocker(){
        if (mCurFragment.getTag().equals("homepage")){
            mText1.setTextColor(getResources().getColor(R.color.netease_white));
            mText2.setTextColor(getResources().getColor(R.color.netease_gray));
            mText3.setTextColor(getResources().getColor(R.color.netease_gray));
            mText4.setTextColor(getResources().getColor(R.color.netease_gray));
            mImg1.setVisibility(View.VISIBLE);
            mImg2.setVisibility(View.GONE);
            mImg3.setVisibility(View.GONE);
            mImg4.setVisibility(View.GONE);
        }else if (mCurFragment.getTag().equals("itempage")){
            mText2.setTextColor(getResources().getColor(R.color.netease_white));
            mText1.setTextColor(getResources().getColor(R.color.netease_gray));
            mText3.setTextColor(getResources().getColor(R.color.netease_gray));
            mText4.setTextColor(getResources().getColor(R.color.netease_gray));
            mImg2.setVisibility(View.VISIBLE);
            mImg1.setVisibility(View.GONE);
            mImg3.setVisibility(View.GONE);
            mImg4.setVisibility(View.GONE);
        }else if (mCurFragment.getTag().equals("msgpage")){
            mText3.setTextColor(getResources().getColor(R.color.netease_white));
            mText1.setTextColor(getResources().getColor(R.color.netease_gray));
            mText2.setTextColor(getResources().getColor(R.color.netease_gray));
            mText4.setTextColor(getResources().getColor(R.color.netease_gray));
            mImg3.setVisibility(View.VISIBLE);
            mImg1.setVisibility(View.GONE);
            mImg2.setVisibility(View.GONE);
            mImg4.setVisibility(View.GONE);
        }else if (mCurFragment.getTag().equals("morepage")){
            mText4.setTextColor(getResources().getColor(R.color.netease_white));
            mText1.setTextColor(getResources().getColor(R.color.netease_gray));
            mText2.setTextColor(getResources().getColor(R.color.netease_gray));
            mText3.setTextColor(getResources().getColor(R.color.netease_gray));
            mImg4.setVisibility(View.VISIBLE);
            mImg1.setVisibility(View.GONE);
            mImg2.setVisibility(View.GONE);
            mImg3.setVisibility(View.GONE);
        }


    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


    @AfterPermissionGranted(1000)
    private void requireSomePermission() {
        String[] perms = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            //有权限
        } else {
            //没权限
            EasyPermissions.requestPermissions(this, getString(R.string.file_permission_tip),
                    1000, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CityListSelectActivity.CITY_SELECT_RESULT_FRAG) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();

                CityInfoBean cityInfoBean = (CityInfoBean) bundle.getParcelable("cityinfo");

                if (null == cityInfoBean) {
                    return;
                }

                HomepageFragment homepageFragment = (HomepageFragment)getSupportFragmentManager().findFragmentByTag("homepage");
                homepageFragment.updateCity(cityInfoBean);

                Toasty.info(MainActivity.this,"城市： " + cityInfoBean.getName(), Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(mMessageReceiver);
        }catch (Exception e){

        }
    }
}
