package com.huotu.fanmore.pinkcatraiders.ui.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.LoginQQModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginWXModel;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

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
        LoginQQModel qqModel = new LoginQQModel();
        Message msg = new Message();
        msg.what = Contant.MSG_LOGIN;
        PlatformDb accountDb = plat.getDb ();

        if(plat.getName().equals(QQ.NAME)) {
            String icon=accountDb.get("icon");
            qqModel.setIcon(icon);
            String openid=plat.getDb().getUserId();
            qqModel.setOpenid(openid);
             String secret=accountDb.get("secret");
            qqModel.setSecret(secret);
            String expiewsTime=accountDb.get("expiewsTime");
            qqModel.setExpiewsTime(expiewsTime);
            String token=accountDb.get("token");
            qqModel.setToken(token);
            String nickname=accountDb.get("nickname");
            qqModel.setNickname(nickname);
             String iconQzone=accountDb.get("iconQzone");
            qqModel.setIconQzone(iconQzone);
             String secretType=accountDb.get("secretType");
            qqModel.setSecretType(secretType);
             String pfkey=accountDb.get("pfkey");
            qqModel.setPfkey(pfkey);
             String gender=accountDb.get("gender");
            qqModel.setGender(gender);
             String weibo=accountDb.get("weibo");
            qqModel.setWeibo(weibo);
             String pf=accountDb.get("pf");
            qqModel.setPf(pf);
             String expiresln=accountDb.get("expiresln");
            qqModel.setExpiresln(expiresln);
             String pay_token=accountDb.get("pay_token");
            qqModel.setPay_token(pay_token);



            msg.arg1 = 1;
            msg.obj = qqModel;

        }
        else if( plat.getName().equals(Wechat.NAME)) {

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

            msg.arg1 = 2;
            msg.obj = loginWX;
        }




        mHandler.sendMessage ( msg );
    }
}
