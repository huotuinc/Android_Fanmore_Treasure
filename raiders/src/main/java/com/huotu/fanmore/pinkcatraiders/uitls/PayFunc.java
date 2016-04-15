package com.huotu.fanmore.pinkcatraiders.uitls;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.huotu.fanmore.pinkcatraiders.async.WXPayAsyncTask;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import java.math.BigDecimal;

/**
 * Created by lenovo on 2016/2/25.
 */
public class PayFunc {


    private PayModel payModel;

    private
    BaseApplication application;

    private
    Handler handler;

    private
    Context context;

    private
    Activity aty;

    private ProgressPopupWindow progress;

    public PayFunc(
            Context context, PayModel payModel, BaseApplication application, Handler handler,
            Activity aty, ProgressPopupWindow progress
    ) {

        this.payModel = payModel;
        this.application = application;
        this.handler = handler;
        this.context = context;
        this.aty = aty;
        this.progress = progress;
    }

    public void wxPay() {
        //根据订单号获取支付信息
        String body = payModel.getDetail();
        String priceStr = payModel.getWxFee();
        int productType = 0;
        long productId = 0;
        progress.dismissView();
        //调用微信支付模块
        new WXPayAsyncTask(handler, body, priceStr, productType, productId, context, application, payModel.getWxCallbackUrl(), payModel.getAttach(), payModel.getOrderNo()).execute();
    }

    public void aliPay() {
        AliPayUtil aliPay = new AliPayUtil(aty, handler, application);
        //根据订单号获取订单信息
        String body = payModel.getDetail();
        String price = payModel.getAlipayFee();
        String subject = payModel.getDetail();
        int productType = 0;
        long productId = 0;
        progress.dismissView();
        aliPay.pay(subject, body, price, payModel.getAlipayCallbackUrl(), productType, productId, payModel.getOrderNo());
    }
}
