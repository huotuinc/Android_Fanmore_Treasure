package com.huotu.fanmore.pinkcatraiders.conf;

/**
 * 系统环境配置参数
 */
public class Contant {

    /**
     * 启动界面时长
     */
    public final static int    ANIMATION_COUNT  = 1000;

    /**
     * appsecret
     */
    public static final String APP_SECRET       = "4165a8d240b29af3f41818d10599d0d1";

    /**
     * operation
     */
    public static final String OPERATION        = "fenmore2016";

    public static final String SYS_INFO         = "sysInfo";

    public static final String SYS_PACKAGE      = "cy.com.morefan";

    public static final String FIRST_OPEN       = "firstOpen";

    public static final String MEMBER_INFO      = "member_info";

    public static final String MEMBER_TOKEN     = "member_token";

    public static final String APPKEY           = "123456";

    //微信支付ID
    public static final String WXPAY_ID         = "";

    //微信登录:用户存在
    public static final int    MSG_USERID_FOUND = 0x00000001;

    //微信登录：用户不存在
    public static final int MSG_USERID_NO_FOUND = 0x00000002;

    public static final int MSG_LOGIN           = 0x00000003;

    public static final int MSG_AUTH_CANCEL     = 0x00000004;

    public static final int MSG_AUTH_ERROR      = 0x00000005;

    public static final int MSG_AUTH_COMPLETE   = 0x00000006;

    //鉴权失效
    public static final int LOGIN_AUTH_ERROR    = 0x00000007;

    public static final int INIT_MENU_ERROR     = 0x00000008;

    //切换内容页
    public static final int SWITCH_UI           = 0x00000010;


    //更新夺宝数量
    public static final int UPDATE_RAIDER_COUNT = 0x00000011;

    //更新专区数量
    public static final int LOAD_AREA_COUNT     = 0x00000012;

    /**
     * 上传图片
     */
    public static final int UPLOAD_IMAGE        = 0x00000031;

    /**
     * 选择地址
     */
    public static final int SELECT_ADDRESS      = 0x00000032;

    /**
     * 菜单标记1
     */
    public final static String TAG_1 = "home";

    /**
     * 菜单标记2
     */
    public final static String TAG_2 = "newest";

    /**
     * 菜单标记3
     */
    public final static String TAG_3 = "list";

    /**
     * 菜单标记4
     */
    public final static String TAG_4 = "profile";

    /**
     * capCode
     */
    // 登录信息文件
    public final static String LOGIN_USER_INFO = "login_user_info";

    // 短信获取方式:文本
    public final static String SMS_TYPE_TEXT   = "0";

    // 短信获取方式:语音
    public final static String SMS_TYPE_VOICE = "1";

    /**
     * token添加的类型
     */
    public final static String LOGIN_AUTH_REALNAME     = "realName";

    public final static String LOGIN_AUTH_ENABLED      = "enabled";

    public final static String LOGIN_AUTH_MOBILE       = "mobile";

    public final static String LOGIN_AUTH_MOBILEBANDED = "mobileBanded";

    public final static String LOGIN_AUTH_MONEY        = "money";

    public final static String LOGIN_AUTH_TOKEN        = "token";

    public final static String LOGIN_AUTH_USERFORMTYPE = "userFormType";

    public final static String LOGIN_AUTH_UDERHEAD     = "userHead";

    public final static String LOGIN_AUTH_USERID       = "userId";

    public final static String LOGIN_AUTH_USERNAME     = "username";

    //接口请求地址
    //loginapi
    //public static final String REQUEST_URL = "http://192.168.3.67:8080/duobao/app/";
    public static final String REQUEST_URL = "http://192.168.3.22:8080/duobao/app/";
    //public static final String REQUEST_URL = "http://192.168.1.41:8080/duobao/app/";
    //public static final String REQUEST_URL                 = "http://192.168.1.146:8081/duobao/app/";
    //public static final String REQUEST_URL = "http://192.168.3.13:8080/duobao/app/";
    //获取夺宝记录
    public static final String GET_MY_RAIDER_LIST          = "getMyRaiderList";

    //获取首页商品列表
    public static final String GET_GOODS_LIST_INDEX        = "getGoodsListByIndex";

    //获取专区商品列表
    public static final String GET_GOODS_LIST_BY_AREA = "getGoodsListByArea";
    //获取分类浏览列表
    public static final String GET_CATE_GORY_LIST="getCategoryList";
    //获取商品详情信息
    public static final String GET_GOODS_DTAIL_BY_GOODS_ID = "getGoodsDetailByGoodsId";

    //获取参与历史
    public static final String GET_BUY_LIST                = "getBuyList";

    //初始化
    public static final String INIT                        = "init";

    //登录
    public static final String LOGIN                       = "login";

    public static final String REG                         = "reg";
    //修改用户信息资料
    public static final String UPDATE_PROFILE = "updateProfile";
}
