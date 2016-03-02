package com.huotu.fanmore.pinkcatraiders.base;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.model.InitOutputsModel;
import com.huotu.fanmore.pinkcatraiders.uitls.PreferenceHelper;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.orm.SugarContext;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

/**
 * 粉猫夺宝application
 */
public class BaseApplication extends Application {
    private static BaseApplication app;

    public Platform plat;
    public FragManager mFragManager;
    public FragManager proFragManager;
    public static synchronized BaseApplication getInstance() {
        if (app == null) {
            app = new BaseApplication();
        }
        return app;
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged ( newConfig );
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        ShareSDK.initSDK(getApplicationContext());
        VolleyUtil.init(getApplicationContext());
        //加载异常处理模块
        CrashHandler crashHandler = CrashHandler.getInstance ();
        crashHandler.init(getApplicationContext());
        //加载数据库初始化模块
        SugarContext.init ( getApplicationContext ( ) );
    }

    //判断是否为4.4版本。可设置沉浸模式
    public boolean isKITKAT()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null;// 网络是否连接
    }

    /**
     * 获取手机IMEI码
     */
    public String getPhoneIMEI ( Context cxt ) {
        TelephonyManager tm = ( TelephonyManager ) cxt
                .getSystemService ( Context.TELEPHONY_SERVICE );
        return tm.getDeviceId ( );
    }

    /**
     * 获取当前应用程序的版本号
     */
    public String getAppVersion(Context context)
    {
        String version = "0";
        try
        {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e)
        {

        }
        return version;
    }

    public boolean isFirst()
    {
        String initInfo = PreferenceHelper.readString(getApplicationContext(), Contant.SYS_INFO, Contant.FIRST_OPEN);
        if(TextUtils.isEmpty(initInfo))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void writeUserInfo(AppUserModel user)
    {
        PreferenceHelper.writeString(getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_REALNAME,user.getRealName());
        PreferenceHelper.writeInt(getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_ENABLED,user.getEnabled());
        PreferenceHelper.writeString(getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_MOBILE,user.getMoblie());
        PreferenceHelper.writeInt(getApplicationContext(), Contant.LOGIN_USER_INFO, Contant.LOGIN_AUTH_MOBILEBANDED, user.getMobileBanded());
        PreferenceHelper.writeString(getApplicationContext(), Contant.LOGIN_USER_INFO, Contant.LOGIN_AUTH_MONEY, String.valueOf(user.getMoney()));
        PreferenceHelper.writeString(getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_TOKEN,user.getToken());
        PreferenceHelper.writeInt(getApplicationContext(), Contant.LOGIN_USER_INFO, Contant.LOGIN_AUTH_USERFORMTYPE, user.getUserFormType());
        PreferenceHelper.writeString(getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_UDERHEAD,user.getUserHead());
        PreferenceHelper.writeLong(getApplicationContext(), Contant.LOGIN_USER_INFO, Contant.LOGIN_AUTH_USERID, user.getUserId());

        PreferenceHelper.writeString(getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_USERNAME,user.getUsername());
    }
    //获取头像
    public String readUerHead()
    {
        return PreferenceHelper.readString ( getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_UDERHEAD );
    }

    //获取userId
    public Long readUerId()
    {
        return PreferenceHelper.readLong ( getApplicationContext ( ), Contant.LOGIN_USER_INFO,
                                           Contant.LOGIN_AUTH_USERID );
    }

    //获取user账号
    public String readAccount()
    {
        return PreferenceHelper.readString ( getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_USERNAME );
    }

    //获取user昵称
    public String readNickName()
    {
        return PreferenceHelper.readString ( getApplicationContext ( ), Contant.LOGIN_USER_INFO,
                                             Contant.LOGIN_AUTH_REALNAME );
    }

    //获取user手机号
    public String readMobile()
    {
        return PreferenceHelper.readString ( getApplicationContext ( ), Contant.LOGIN_USER_INFO,
                                             Contant.LOGIN_AUTH_MOBILE );
    }

    public void writeInitInfo(String initStr)
    {
        PreferenceHelper.writeString ( getApplicationContext (), Contant.SYS_INFO, Contant.FIRST_OPEN, initStr );
    }

    public void loadGlobalData(InitOutputsModel.InitInnerModel.GlobalModel globalModel)
    {
        PreferenceHelper.writeString(getApplicationContext(),"global_info","customerServicePhone",globalModel.getCustomerServicePhone());
        PreferenceHelper.writeString(getApplicationContext(),"global_info","helpURL",globalModel.getHelpURL());
        PreferenceHelper.writeString(getApplicationContext(),"global_info","serverUrl",globalModel.getServerUrl());
        PreferenceHelper.writeBoolean(getApplicationContext(), "global_info", "voiceSupported", globalModel.isVoiceSupported());
    }

    public void loadUpdate(InitOutputsModel.InitInnerModel.UpdateModel update)
    {
        PreferenceHelper.writeString(getApplicationContext(),"update_info","updateMD5",update.getUpdateMD5());
        PreferenceHelper.writeString(getApplicationContext(),"update_info","updateTips",update.getUpdateTips());
        PreferenceHelper.writeString(getApplicationContext(),"update_info","updateUrl",update.getUpdateUrl());
        PreferenceHelper.writeString(getApplicationContext(),"update_info","updateType",update.getUpdateType());
    }
    //判断是否登录
    public boolean isLogin()
    {
        String token = PreferenceHelper.readString ( getApplicationContext(),Contant.LOGIN_USER_INFO,Contant.LOGIN_AUTH_TOKEN);
        if(null != token && !"".equals ( token ))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String obtainToken()
    {

        return PreferenceHelper.readString(getApplicationContext (), Contant.LOGIN_USER_INFO, Contant.LOGIN_AUTH_TOKEN);

    }

}
