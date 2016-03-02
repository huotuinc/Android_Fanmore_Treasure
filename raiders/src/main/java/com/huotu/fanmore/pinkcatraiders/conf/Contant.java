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

    //支付环节------------------------------------------
    //微信支付ID
    public static final String WXPAY_ID    = "wxd8c58460d0199dd5";
    public static final String WXPAY_SECRT = "8ad99de44bd96a323eb40dc161e7d8e8";
    public static final String WXPAY_KEY = "NzfP6pfeljyHeY08LO9p8YAKZCGLz8akO4lCGdXZOGnVsJqfo8jeuYB7C0GoFJGEKZMDVGKWYnbbJj3pCpvJzd4iY7bVglaNz54XAD26tiCr5DZGLjZFoRxbqe8i3HT5";
    public static final String WXPAY_PARTNER = "1220397601";
    //支付宝支付
    //商户ID
    public static final String ALIPAY_PARTNER = "2088211251545121";
    //商户收款账号
    public static final String ALIPAY_SELLER = "2088211251545121";
    //APPkEY
    public static final String ALIPAY_KEY = "2088211251545121";
    //支付环节------------------------------------------

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
    //更新全部商品数量
    public static final int LOAD_ALL_COUNT      =0x00000013;

    /**
     * 上传图片
     */
    public static final int UPLOAD_IMAGE        = 0x00000031;

    /**
     * 选择地址
     */
    public static final int SELECT_ADDRESS      = 0x00000032;
    /**
     * 立即夺宝
     */
    public static final int RAIDERS_NOW      = 0x00000040;
    public static final int CAROUSE_URL     = 0x00000041;
    public static final int CART_SELECT     = 0x00000042;
    public static final int ADD_LIST    = 0x00000043;
    public static final int BILLING    = 0x00000044;

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
     * 菜单标记5
     */
    public final static String TAG_5 = "mall";

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

    //public static final String REQUEST_URL = "http://192.168.3.22:8080/duobao/app/";
    public static final String REQUEST_URL = "http://192.168.1.41:8080/duobao/app/";
    // public static final String REQUEST_URL = "http://192.168.3.22:8080/duobao/app/";
    //public static final String REQUEST_URL = "http://192.168.1.41:8080/duobao/app/";
    //public static final String REQUEST_URL = "http://duobao.51flashmall.com:8091/app/";
    //public static final String REQUEST_URL                 = "http://192.168.1.146:8081/duobao/app/";
    //public static final String REQUEST_URL = "http://192.168.3.13:8080/duobao/app/";
    //获取夺宝记录
    public static final String GET_MY_RAIDER_LIST          = "getMyRaiderList";

    //获取首页商品列表
    public static final String GET_GOODS_LIST_INDEX        = "getGoodsListByIndex";

    //获取专区商品列表
    public static final String GET_GOODS_LIST_BY_AREA = "getGoodsListByArea";
    //获取往期揭晓（商品每期的中奖列表） 排序依据 期号
    public static final String GET_PAST_LIST          =   "getPastList";
    //获取分类浏览列表
    public static final String GET_CATE_GORY_LIST="getCategoryList";
    //获取商品详情信息
    public static final String GET_GOODS_DTAIL_BY_GOODS_ID = "getGoodsDetailByGoodsId";

    //获取参与历史
    public static final String GET_BUY_LIST                = "getBuyList";

    //获取幻灯片轮播列表
    public static final String GET_SLIDE_LIST              = "getSlideList";
    //获取幻灯片轮播列表
    public static final String GET_SLIDE_DETAIL            = "getSlideDetail";

    //初始化
    public static final String INIT                        = "init";

    //登录
    public static final String LOGIN                       = "login";
    //qq 微信授权登录
    public static final String AUTHLOGIN= "authLogin";

    public static final String REG                         = "reg";
    //修改用户信息资料
    public static final String UPDATE_PROFILE = "updateProfile";
    //获取地址列表
    public static final String GET_MY_ADDRESS_LIST = "getMyAddressList";
    //添加地址
    public static final String ADD_MY_ADDRESS = "addMyAddress";
    //更新地址
    public static final String UPDATE_ADDRESS = "updateAddress";
    //删除地址
    public static final String DELETE_ADDRESS = " deleteAddress";
    //获取默认充值金额数据
    public static final String GET_DEFAULT_PUT_MONEY_LIST = "getDefaultPutMoneyList";
    //获取充值记录
    public static final String GET_MY_PUT_LIST = "getMyPutList";
    //获取红包列表
    public static final String GET_MY_REDPACKAGES_LIST = "getMyRedPacketsList";
    //获取中奖纪录列表
    public static final String GET_MY_LOTTERY_LIST = "getMyLotteryList";
    //预支付信息
    public static final String PUT_MONEY = "putMoney";
    //获取最新揭晓
    public static final String GET_NEWOPEN_LIST="getNewOpenList";
    //获取普通分类商品
    public static final String GET_GOODS_LIST_BY_CATEGORY="getGoodsListByCategory";
    //获取全部商品
    public static final String GET_GOODS_LIST_BY_ALL_CATEGORY="getGoodsListByAllCategory";
    //获取其他商品列表
    public static final String GET_GOODS_LIST_BY_OTHER_CATEGORY="getGoodsListByOtherCategory";

    //支付
    //微信支付
    public static final String WX_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //首页晒单列表
    public static final String GET_SHARE_ORDER_LIST = "getShareOrderList";
    //我的晒单
    public static final String GET_MY_SHARE_ORDER_LIST = "getMyShareOrderList";
    //商品晒单
    public static final String GET_SHARE_ORDER_LIST_BY_GOOSID = "getShareOrderListByGoodsId";
    //获取晒单详情
    public static final String GET_SHARE_ORDER_DETAIL = "getShareOrderDetail";
    //喇叭通知
    public static final String GET_NOTICE_LIST = "getNoticeList";
    //清单列表
    public static final String GET_SHOPPING_LIST = "getShoppingList";
    //添加清单
    public static final String JOIN_SHOPPING_CART = "joinShoppingCart";
    //搜索
    public static final String SEARCH_GOODS = "searchGoods";
    //发布晒单
    public static final String ADD_SHARE_ORDER = "addShareOrder";
    //查看夺宝号码
    public static final String GET_MY_RAIDER_NUMBER = "getMyRaiderNumbers";
}
