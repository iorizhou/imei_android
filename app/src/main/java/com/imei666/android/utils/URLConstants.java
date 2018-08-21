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
}
