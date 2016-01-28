package com.huotu.fanmore.pinkcatraiders.conf;

/**
 * 系统环境配置参数
 */
public class Contant {

    /**
     * 启动界面时长
     */
    public final static int ANIMATION_COUNT = 1000;
    /**
     * appsecret
     */
    public static final String APP_SECRET = "4165a8d240b29af3f41818d10599d0d1";
    /**
     * operation
     */
    public static final String OPERATION = "ymr2015huotu";
    public static final String SYS_INFO    = "sysInfo";
    public static final String SYS_PACKAGE = "cy.com.morefan";
    public static final String FIRST_OPEN  = "firstOpen";
    public static final String MEMBER_INFO = "member_info";
    public static final String MEMBER_TOKEN = "member_token";
    //微信支付ID
    public static final String WXPAY_ID ="";
    //微信登录:用户存在
    public static final int MSG_USERID_FOUND    = 0x00000001;

    //微信登录：用户不存在
    public static final int MSG_USERID_NO_FOUND = 0x00000002;
    public static final int MSG_LOGIN           = 0x00000003;
    public static final int MSG_AUTH_CANCEL     = 0x00000004;
    public static final int MSG_AUTH_ERROR      = 0x00000005;
    public static final int MSG_AUTH_COMPLETE   = 0x00000006;
    //鉴权失效
    public static final int LOGIN_AUTH_ERROR = 0x00000007;
    public static final int INIT_MENU_ERROR = 0x00000008;
    //切换内容页
    public static final int SWITCH_UI = 0x00000010;
    public static final String URL="http://192.168.1.41:8080/duobao/app/";
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
    /**
     * token添加的类型
     */
    public final static String LOGIN_AUTH_REALNAME="realName";
    public final static String LOGIN_AUTH_ENABLED="enabled";
    public final static String LOGIN_AUTH_MOBILE="mobile";
    public final static String LOGIN_AUTH_MOBILEBANDED="mobileBanded";
    public final static String LOGIN_AUTH_MONEY="money";
    public final static String LOGIN_AUTH_TOKEN="token";
    public final static String LOGIN_AUTH_USERFORMTYPE="userFormType";
    public final static String LOGIN_AUTH_UDERHEAD="userHead";
    public final static String LOGIN_AUTH_USERID="userId";
    public final static String LOGIN_AUTH_USERNAME="username";
}
