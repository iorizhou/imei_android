package com.imei666.android.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imei666.android.ImeiApplication;
import com.imei666.android.db.DBUtil;
import com.imei666.android.mvp.model.dto.MessageDTO;
import com.imei666.android.mvp.model.dto.MessageListDTO;
import com.imei666.android.mvp.view.activity.MainActivity;
import com.imei666.android.net.HttpPostTask;
import com.imei666.android.utils.Constants;
import com.imei666.android.utils.MessageNotificationDispatcher;
import com.imei666.android.utils.URLConstants;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MessageService extends Service {
    private BroadcastReceiver mMessageReceiver;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        //不管bind多少次 oncreate只会调用一次
        regMessageBroadcast();
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                if (!isOWnSend){
                    //不是自己发的，那么就是别人发给你的，你接收的消息 ，直接将其的发送状态置为1 即成功状态  以免在adapter中 错误的由于0默认值，而显示发送的转圈
                    dto.setSendStatus(1);
                }
                DBUtil.getInstance(ImeiApplication.getInstace()).getDaoSession().getMessageDTODao().insert(dto);
                //消息存好了，此时开始更新messagelist

                MessageListDTO messageListDTO = new MessageListDTO();
                long friendId = isOWnSend?dto.getRecverId():dto.getSenderId();
                messageListDTO.setFriendId(friendId);
                messageListDTO.setContent(dto.getContent());
                messageListDTO.setFriendAvatar(isOWnSend?dto.getRecverAvatar():dto.getSenderAvatar());
                messageListDTO.setFriendName(isOWnSend?dto.getRecverName():dto.getSenderName());
                messageListDTO.setTime(dto.getSendTime());
                DBUtil.getInstance(MessageService.this).getDaoSession().getMessageListDTODao().insertOrReplace(messageListDTO);
                MessageNotificationDispatcher.getInstance().notifyAll(bundle.getString("msg"));
            }
        };
        registerReceiver(mMessageReceiver,filter);
    }

    public class MyBinder extends Binder{

        public MessageService getService(){
            return MessageService.this;
        }
    }


    public String getHello(){
        return "来自messageService的问候";
    }

    public void sendMsg(String content, final MessageDTO tmpMsg, long mFriendId, final SendMsgListener listener){
        Map<String,String> paramMap = new HashMap<String, String>();

        paramMap.put("userId","1");
        paramMap.put("recvId",mFriendId+"");
        paramMap.put("messageType",0+"");
        paramMap.put("content",content);
        new HttpPostTask().doPost(URLConstants.SEND_MSG, paramMap, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (listener!=null){
                    listener.onSendFailed(-1,"消息发送失败");
                }
            }

            @Override
            public void onResponse(String response, int id) {

                JSONObject jsonObject = JSONObject.parseObject(response);
                if (!jsonObject.getString("msgCode").equals("0")){
                    if (listener!=null){
                        listener.onSendFailed(-1,"消息发送失败");
                    }
                    return;
                }
                MessageDTO dto = JSON.parseObject(jsonObject.getString("datas"),MessageDTO.class);
                boolean isOWnSend = 1==dto.getSenderId()?true:false;
                dto.setOwnSend(isOWnSend);
                dto.setSendStatus(1);
                if (dto==null){
                    if (listener!=null){
                        listener.onSendFailed(-1,"消息发送失败");
                    }
                    return;
                }
                DBUtil.getInstance(MessageService.this).getDaoSession().getMessageDTODao().insert(dto);
//                datas.remove(tmpMsg);
//                datas.add(dto);
//                adapter.refresh(datas);

                //记得更新messagelist

                MessageListDTO messageListDTO = new MessageListDTO();
                long friendId = isOWnSend?dto.getRecverId():dto.getSenderId();
                messageListDTO.setFriendId(friendId);
                messageListDTO.setContent(dto.getContent());
                messageListDTO.setFriendAvatar(isOWnSend?dto.getRecverAvatar():dto.getSenderAvatar());
                messageListDTO.setFriendName(isOWnSend?dto.getRecverName():dto.getSenderName());
                messageListDTO.setTime(dto.getSendTime());
                DBUtil.getInstance(MessageService.this).getDaoSession().getMessageListDTODao().insertOrReplace(messageListDTO);
                MessageNotificationDispatcher.getInstance().notifyAll(JSONObject.toJSONString(dto));
                if (listener!=null){
                    listener.onSendSuccess(dto);
                }
            }
        });
    }

    public interface SendMsgListener{
        public void onSendSuccess(MessageDTO messageDTO);
        public void onSendFailed(int msgCode,String msg);

    }
}
