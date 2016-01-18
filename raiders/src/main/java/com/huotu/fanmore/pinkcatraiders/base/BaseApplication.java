package com.huotu.fanmore.pinkcatraiders.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.uitls.PreferenceHelper;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import cn.sharesdk.framework.Platform;

/**
 * 粉猫夺宝application
 */
public class BaseApplication extends Application {

    public Platform plat;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyUtil.init(getApplicationContext());
        //加载异常处理模块
        CrashHandler crashHandler = CrashHandler.getInstance ();
        crashHandler.init(getApplicationContext());
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

    public void writeInitInfo(String initStr)
    {
        PreferenceHelper.writeString ( getApplicationContext (), Contant.SYS_INFO, Contant.FIRST_OPEN, initStr );
    }

    //判断是否登录
    public boolean isLogin()
    {
        String token = PreferenceHelper.readString ( getApplicationContext (), Contant.MEMBER_INFO, Contant.MEMBER_TOKEN );
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
        return PreferenceHelper.readString(getApplicationContext (), Contant.MEMBER_INFO, Contant.MEMBER_TOKEN);
    }
}
