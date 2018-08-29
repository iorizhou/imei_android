package com.imei666.android.utils;

public class URLConstants {
    public static final String HOST = "http://app.imei666.com";
    //搜索
    public static final String SEARCH = HOST + "/search";
    //获取首页推荐banner
    public static final String GET_TOP_BANNER = HOST + "/banner/getTopBanner";
    //1.3首页显示的项目分类名称列表 (top顶级分类名称)
    public static final String GET_TOP_RECOMMEND_ITEM = HOST + "/type/getTopRecommendItem";
    //首页  获取日记分类列表
    public static final String GET_DIARY_TYPE = HOST + "/diary/getAllDiaryTypes";
    //根据日记TYPE ID获取日记list
    public static final String GET_DIARYLIST_BY_TYPEID = HOST + "/diary/getDiaryListByType";
    //首页活动
    public static final String GET_HOMEPAGE_ACTIVITY = HOST + "/activity/getHomepageActivity";


    //整形页 推荐活动
    public static final String GET_ITEMPAGE_RECOMMEND_ACTIVITY = HOST + "/activity/getRecomendActivity";

    //根据typeid获取项目列表
    public static final String GET_ITEMPAGE_ITEMLIST_BY_ID = HOST + "/item/getItemListByType";

    //项目详情
    public static final String GET_ITEM_DETAIL_BY_ID = HOST + "/item/detail";

    //获取用户当前可用订金红包
    public static final String GET_USER_DJ_REDPACKET = HOST + "/redpacket/getDJRedPacketList";


    //获取用户当前可用尾款红包
    public static final String GET_USER_WK_REDPACKET = HOST + "/redpacket/getWKRedPacketList";

    //获取用户当前可用医院专用红包
    public static final String GET_USER_YY_REDPACKET = HOST + "/redpacket/getYYRedPacketList";

    //提交订单
    public static final String SUBMIT_ORDER = HOST + "/order/create";

    //查询订单详情
    public static final String GET_ORDER_DETAIL = HOST + "/order/detail";


    //获取支付宝支付信息
    public static final String GET_ALIPAY_INFO = HOST + "/order/getAlipayInfo";

    //获取支付宝支付信息
    public static final String GET_ORDER_LIST = HOST + "/order/list";

    //发送消息
    public static final String SEND_MSG = HOST + "/im/sendSingle";


}
