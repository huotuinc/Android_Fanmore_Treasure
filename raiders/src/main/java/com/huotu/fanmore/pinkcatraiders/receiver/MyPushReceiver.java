package com.huotu.fanmore.pinkcatraiders.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.PreferenceHelper;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 激光推送处理广播类
 */
public
class MyPushReceiver extends BroadcastReceiver
{

    private String imei = null;

    private String regId = null;

    @Override
    public
    void onReceive ( final Context context, Intent intent ) {
        Bundle bundle = intent.getExtras ( );
        if ( JPushInterface.ACTION_REGISTRATION_ID.equals ( intent.getAction ( ) ) ) {
            // 注册后获取极光全局ID
            regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            imei = BaseApplication.getPhoneIMEI(context);
            //将imei注册为别名
            //将remote别名传递给
            String url = Contant.REQUEST_URL + Contant.UPDATE_DEVICE_TOKEN;
            AuthParamUtils params = new AuthParamUtils(BaseApplication.getInstance(), System.currentTimeMillis(), context.getApplicationContext());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("deviceToken", imei);
            Map<String, Object> param = params.obtainPostParam(maps);
            BaseModel base = new BaseModel ();
            HttpUtils<BaseModel> httpUtils = new HttpUtils<BaseModel> ();
            httpUtils.doVolleyPost(
                    base, url, param, new Response.Listener<BaseModel>() {
                        @Override
                        public void onResponse(BaseModel response) {
                            BaseModel base = response;
                            if (1 == base.getResultCode()) {
                                //上传成功
                                //与jpush remote
                                JPushInterface.setAlias(context.getApplicationContext(), imei, new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int code, String s, Set<String> set) {

                                        // TODO Auto-generated method stub
                                        switch (code) {
                                            case 0:
                                                PreferenceHelper.writeString(context, Contant.JPUSH_CONFIG, Contant.JPUSH_ALIAS, imei);
                                                break;

                                            case 6002:
                                                break;

                                            default:
                                                break;
                                        }
                                    }
                                });
                            } else {
                                //上传失败
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                                                                         .getAction()))
        {
            // 处理接受的自定义消息
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                                                                              .getAction()))
        {
            // 接收到了自定义的通知
            // 通知ID
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                                                                            .getAction()))
        {
            // 接受点击通知事件
            String title = bundle
                    .getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                                                                          .getAction()))
        {
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..
            // 接受富文本框
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);


        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                                                                          .getAction()))
        {
            boolean connected = intent.getBooleanExtra(
                    JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            // 处理网络变更事件

        } else
        {

        }
    }
}

