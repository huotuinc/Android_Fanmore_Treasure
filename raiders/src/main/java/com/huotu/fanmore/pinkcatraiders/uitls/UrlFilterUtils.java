package com.huotu.fanmore.pinkcatraiders.uitls;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.MallPayModel;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/3/24.
 */
public class UrlFilterUtils {


    private
    Context context;
    private
    Activity aty;
    private Handler mHandler;
    private
    BaseApplication application;
    //windows类
    private WindowManager wManager;
    private TextView titleView;
    public
    ProgressPopupWindow payProgress;
    private  String orderUrl;

    public
    UrlFilterUtils (
            Activity aty, Context context, TextView titleView, Handler mHandler,
            BaseApplication application, WindowManager wManager, String orderUrl
    ) {
        this.context = context;
        this.mHandler = mHandler;
        this.application = application;
        this.titleView = titleView;
        this.aty = aty;
        this.wManager = wManager;
        payProgress = new ProgressPopupWindow ( context, aty, wManager );
        this.orderUrl = orderUrl;
    }

    /**
     * webview拦截url作相应的处理
     * @param view
     * @param url
     * @return
     */
    public
    boolean shouldOverrideUrlBySFriend ( WebView view, String url ) {

        if(url.contains ("/Mall/AppAlipay.aspx") )
        {


            //支付进度
            payProgress.showProgress ( "正在加载支付信息" );
            payProgress.showAtLocation (
                    titleView,
                    Gravity.CENTER, 0, 0
            );
            //支付模块
            //获取信息
            //截取问号后面的
            //订单号
            String tradeNo = null;
            String customerID = null;
            String paymentType = null;
            MallPayModel payModel = new MallPayModel ();
            url = url.substring ( url.indexOf ( ".aspx?" )+6, url.length () );
            String[] str = url.split ( "&" );
            for(String map : str)
            {
                String[] values = map.split ( "=" );
                if(2 == values.length)
                {
                    if("trade_no".equals ( values[0] ))
                    {
                        tradeNo = values[1];
                        payModel.setTradeNo ( tradeNo );
                    }
                    else if("customerID".equals ( values[0] ))
                    {
                        customerID = values[1];
                        payModel.setCustomId ( customerID );
                    }
                    else if("paymentType".equals ( values[0] ))
                    {
                        paymentType = values[1];
                        payModel.setPaymentType ( paymentType );
                    }
                }
                else
                {
                }
            }
            //获取用户等级
            StringBuilder builder = new StringBuilder (  );
            builder.append ( orderUrl + "/order/GetOrderInfo" );
            builder.append("?orderid=" + tradeNo);
            AuthMallParamUtils param = new AuthMallParamUtils ( application, System.currentTimeMillis (), builder.toString (), context );
            String payUrl = param.obtainUrlOrder ( );
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doMallPay(aty, context, mHandler, application, payUrl, payModel, payProgress, titleView, wManager, orderUrl );
            return true;
        }
        else
        {
            //跳转到新界面
            view.loadUrl(url);
            return false;
        }
    }
}
