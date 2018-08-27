package com.imei666.android.utils;

public class OrderFollow {

    /** 时间 */
    private String acceptTime;
    /** 描述 */
    private String acceptStation;

    public OrderFollow() {
    }

    public OrderFollow(String acceptTime, String acceptStation) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }


}
