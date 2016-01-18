package com.huotu.fanmore.pinkcatraiders.ui.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.LoginWXModel;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;

/**
 * 微信授权实现类
 */
public
class AutnLogin {

    private
    Context context;
    private
    Handler mHandler;
    private View view;
    private
    BaseApplication application;

    public
    AutnLogin ( Context context, Handler mHandler, View view, BaseApplication application ) {
        this.context = context;
        this.mHandler = mHandler;
        this.view = view;
        this.application = application;
    }

    public
    void authorize ( Platform plat ) {
        if ( plat.isValid ( ) ) {
            application.plat = plat;
            String userId = plat.getDb ( ).getUserId ( );
            if ( ! TextUtils.isEmpty ( userId ) ) {
                mHandler.sendEmptyMessage ( Contant.MSG_USERID_FOUND );
                login ( plat );
                return;
            }
            else {
                mHandler.sendEmptyMessage ( Contant.MSG_USERID_NO_FOUND );
                return;
            }
        }
        plat.setPlatformActionListener ( new PlatformActionListener ( ) {
                                             @Override
                                             public
                                             void onComplete ( Platform platform, int action, HashMap< String, Object > hashMap ) {

                                                 view.setClickable ( true );
                                                 if ( action == Platform.ACTION_USER_INFOR ) {
                                                     Message msg = new Message();
                                                     msg.what = Contant.MSG_AUTH_COMPLETE;
                                                     msg.obj = platform;
                                                     mHandler.sendMessage ( msg );
                                                 }
                                             }

                                             @Override
                                             public
                                             void onError ( Platform platform, int action, Throwable throwable ) {
                                                 view.setClickable ( true );
                                                 Message msg = new Message();
                                                 msg.what = Contant.MSG_AUTH_ERROR;
                                                 msg.obj = throwable;
                                                 mHandler.sendMessage ( msg );
                                             }

                                             @Override
                                             public
                                             void onCancel ( Platform platform, int action ) {
                                                 view.setClickable ( true );
                                                 if (action == Platform.ACTION_USER_INFOR) {
                                                     mHandler.sendEmptyMessage(Contant.MSG_AUTH_CANCEL );
                                                 }
                                             }
                                         } );
        plat.SSOSetting(false);
        plat.showUser ( null );
    }

    private void login(Platform plat) {
        LoginWXModel loginWX = new LoginWXModel();
        Message msg = new Message();
        msg.what = Contant.MSG_LOGIN;
        PlatformDb accountDb = plat.getDb ();
        //unionid
        String unionid = accountDb.get("unionid");
        loginWX.setUnionid(unionid);
        //openId
        String openid = accountDb.get("openid");
        loginWX.setOpenid(openid);
        //昵称
        String nickname = accountDb.get("nickname");
        loginWX.setNickname(nickname);
        //微信头像
        String headimgurl = accountDb.get("icon");
        loginWX.setHeadimgurl(headimgurl);
        //性别
        String sex = accountDb.get("gender");
        loginWX.setSex(Integer.parseInt(sex));
        //国家
        String country = accountDb.get("country");
        loginWX.setCountry(country);
        //省份
        String province = accountDb.get("province");
        loginWX.setProvince(province);
        //城市
        String city = accountDb.get("city");
        loginWX.setCity(city);
        msg.obj = loginWX;
        mHandler.sendMessage ( msg );
    }
}
