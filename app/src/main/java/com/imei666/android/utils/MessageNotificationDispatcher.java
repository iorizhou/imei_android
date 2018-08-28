package com.imei666.android.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageNotificationDispatcher {

    private static HashMap<String,MessageListener> mObserverMap;

    public static MessageNotificationDispatcher sInstance;

    private MessageNotificationDispatcher() {
        mObserverMap = new HashMap<String,MessageListener>();
    }

    public static synchronized MessageNotificationDispatcher getInstance() {
        if (sInstance==null) {
            sInstance = new MessageNotificationDispatcher();
        }
        return sInstance;
    }

    public void notifyAll(String msg){
        Iterator<Map.Entry<String, MessageListener>> it = mObserverMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, MessageListener> entry = it.next();
            entry.getValue().onMessage(msg);
        }
    }

    public void regMessageNotification(String regName,MessageListener listener){
        mObserverMap.put(regName,listener);
    }

    public void unregMessageNotification(String regName){
        mObserverMap.remove(regName);
    }

    public interface MessageListener{
        public void onMessage(String content);
    }
}
