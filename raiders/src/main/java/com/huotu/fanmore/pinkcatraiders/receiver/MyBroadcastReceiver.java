package com.huotu.fanmore.pinkcatraiders.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * 公共广播类
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    public static String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED" ;
    public static String ACTION_BACKGROUD_BACK_TO_UPDATE = "cy.com.morefan.BACKGROUD_BACK_TO_UPDATE" ;//后台返回
    public static String ACTION_USER_MAINDATA_UPDATE = "cy.com.morefan.MAINDATA_UPDATE" ;
    public static String ACTION_USER_LOGIN = "cy.com.morefan.LOGIN" ;
    public static String ACTION_USER_LOGOUT = "cy.com.morefan.LOGOUT";
    public static String ACTION_SHARE_TO_WEIXIN_SUCCESS = "cy.com.morefan.SHARE_TO_WEIXIN_SUCCESS" ;
    public static String ACTION_SHARE_TO_SINA_SUCCESS = "cy.com.morefan.SHARE_TO_SINA_SUCCESS" ;
    public static String ACTION_SHARE_TO_QZONE_SUCCESS = "cy.com.morefan.SHARE_TO_QZONE_SUCCESS" ;
    public static String ACTION_REFRESH_TASK_LIST = "cy.com.morefan.REFRESH_TASK_LIST" ;
    public static String ACTION_ALARM_UP = "cy.com.morefan.ACTION_ALARM_UP" ;
    public static String ACTION_FLOW_ADD = "cy.com.morefan.ACTION_FLOW_ADD";
    public static String ACTION_PAY_SUCCESS = "com.huotu.partner.ACTION_PAY_SUCCESS";
    public static String REFRESH_TASK_STATUS = "cy.com.morefan.pre.status";

    public static String GET_VOICE_REGISTER = "cy.com.morefan.voice.register";

    public static String ACTION_REFRESH_TASK_DETAIL="cy.com.morefan.REFRESH_TASK_DETAIL";
    public static String ACTION_REQUESTFLOW="cy.com.morefan.REQUESTFLOW";
    public static String ACTION_SENDFLOW="cy.com.morefan.SENDFLOW";

    //分享成功后微信没有回调
    public static String ACTION_WX_NOT_BACK = "cy.com.morefan.ACTION_WX_NOT_BACK" ;
    public static String ACTION_WX_PAY_CALLBACK="cy.com.morefan.ACTION_WX_PAY_CALLBACK";
    //清单结算模式
    public static String SHOP_CART="cy.com.morefan.SHOP_CART";
    //其他界面跳转到购物车
    public static String JUMP_CART="cy.com.morefan.JUMP_CART";
    public static String TO_ADDRESSLIST="cy.com.morefan.TO_ADDRESSLIST";
    //晒单成功

    public static String SHOW_ORDER="cy.com.morefan.SHOW_ORDER";
    //控制title上的消息tag显隐
    public static String TITLE_MSG_TAG ="cy.com.morefan.TITLE_MSG_TAG";

    public enum ReceiverType{
        WXNotBack,AlarmUp, RefreshTaskList,UserMainDataUpdate, Sms, Login, Logout, ShareToWeixinSuccess,
        ShareToSinaSuccess, ShareToQzoneSuccess, BackgroundBackToUpdate, FlowAdd,Register,RefreshTaskDetail,
        WX_Pay_Callback,requestFlow,sendFlow,wxPaySuccess,shopCart,jumpCart,toaddresslist,titleMsgTag,showOrder
    }
    public interface BroadcastListener{
        void onFinishReceiver(ReceiverType type, Object msg);
    }

    private Context mContext;
    private BroadcastListener listener;

    /**
     * 发送广播
     * @param context
     * @param action
     */
    public static
    void sendBroadcast ( Context context, String action ) {
        if ( context == null )
            return;
        Intent intent = new Intent ( action );
        context.sendBroadcast ( intent );
    }

    /**
     * 发送广播
     * @param context
     * @param action
     */
    public static
    void sendBroadcast ( Context context, String action, Bundle extra ) {
        if ( context == null )
            return;
        Intent intent = new Intent ( action );
        intent.putExtras ( extra );
        context.sendBroadcast ( intent );
    }

    /**
     * 注册广播
     * @param context
     * @param listener
     * @param actions
     */
    public
    MyBroadcastReceiver ( Context context, BroadcastListener listener, String... actions ) {
        this.listener = listener;
        this.mContext = context;
        //注册广播
        IntentFilter intentFilter = new IntentFilter ( );
        for ( int i = 0, length = actions.length ; i < length ; i++ )
            intentFilter.addAction ( actions[ i ] );
        context.registerReceiver ( this, intentFilter );
    }

    public
    void unregisterReceiver ( ) {
        mContext.unregisterReceiver ( this );
        mContext = null;
    }

    @Override
    public
    void onReceive ( Context context, Intent intent ) {
        if ( listener == null )
            return;
        if ( intent.getAction ( ).equals ( ACTION_WX_NOT_BACK ) ) {
            listener.onFinishReceiver ( ReceiverType.WXNotBack, intent.getExtras ( ) );
        }
        else if ( intent.getAction ( ).equals ( ACTION_ALARM_UP ) ) {
            listener.onFinishReceiver ( ReceiverType.AlarmUp, intent.getExtras ( ) );
        }
        else if ( intent.getAction ( ).equals ( ACTION_REFRESH_TASK_LIST ) ) {
            listener.onFinishReceiver ( ReceiverType.RefreshTaskList, intent.getExtras ( ) );
        }
        else if ( intent.getAction ( ).equals ( ACTION_USER_MAINDATA_UPDATE ) ) {
            listener.onFinishReceiver ( ReceiverType.UserMainDataUpdate, null );
        }
        else if ( intent.getAction ( ).equals ( ACTION_USER_LOGIN ) ) {
            listener.onFinishReceiver ( ReceiverType.Login, null );
        }
        else if ( intent.getAction ( ).equals ( ACTION_USER_LOGOUT ) ) {
            listener.onFinishReceiver ( ReceiverType.Logout, null);
        } else if (intent.getAction().equals(ACTION_SHARE_TO_WEIXIN_SUCCESS)) {
            listener.onFinishReceiver(ReceiverType.ShareToWeixinSuccess, null);
        } else if (intent.getAction().equals(ACTION_SHARE_TO_SINA_SUCCESS)) {
            listener.onFinishReceiver(ReceiverType.ShareToSinaSuccess, null);
        }else if (intent.getAction().equals(ACTION_SHARE_TO_QZONE_SUCCESS)) {
            listener.onFinishReceiver(ReceiverType.ShareToQzoneSuccess, null);
        }else if( intent.getAction().equals(ACTION_BACKGROUD_BACK_TO_UPDATE)){
            listener.onFinishReceiver(ReceiverType.BackgroundBackToUpdate, null);
        }
        else if (intent.getAction().equals(ACTION_FLOW_ADD))
        {
            listener.onFinishReceiver(ReceiverType.FlowAdd, null);
        }
        else if( intent.getAction().equals(GET_VOICE_REGISTER)){
            listener.onFinishReceiver(ReceiverType.Register, null);
        }else if( intent.getAction().equals( ACTION_REFRESH_TASK_DETAIL)){
            listener.onFinishReceiver(ReceiverType.RefreshTaskDetail, intent.getExtras() );
        }else if( intent.getAction().equals(ACTION_WX_PAY_CALLBACK)){
            listener.onFinishReceiver(ReceiverType.WX_Pay_Callback,null);
        }else if( intent.getAction().equals(ACTION_REQUESTFLOW)){
            listener.onFinishReceiver(ReceiverType.requestFlow,null);
        }else if( intent.getAction().equals(ACTION_SENDFLOW)){
            listener.onFinishReceiver(ReceiverType.sendFlow,null);
        }
        else if(intent.getAction().equals(ACTION_PAY_SUCCESS))
        {
            listener.onFinishReceiver(ReceiverType.wxPaySuccess,null);
        }
        else if(intent.getAction().equals(SHOP_CART))
        {
            listener.onFinishReceiver(ReceiverType.shopCart,intent.getExtras ( ));
        }
        else if(intent.getAction().equals(JUMP_CART))
        {
            listener.onFinishReceiver(ReceiverType.jumpCart,intent.getExtras());
        }
        else if(intent.getAction().equals(TO_ADDRESSLIST))
        {
            listener.onFinishReceiver(ReceiverType.toaddresslist,intent.getExtras());
        }
        else if(intent.getAction().equals(TITLE_MSG_TAG))
        {
            listener.onFinishReceiver(ReceiverType.titleMsgTag,null);
        }
        else if(intent.getAction().equals(SHOW_ORDER))
        {
            listener.onFinishReceiver(ReceiverType.showOrder,intent.getExtras());
        }
    }
}