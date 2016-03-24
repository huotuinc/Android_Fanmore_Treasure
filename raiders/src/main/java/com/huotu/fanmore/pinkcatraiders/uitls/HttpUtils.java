package com.huotu.fanmore.pinkcatraiders.uitls;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.MallOrderModel;
import com.huotu.fanmore.pinkcatraiders.model.MallPayModel;
import com.huotu.fanmore.pinkcatraiders.model.OrderModel;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.PayPopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * http请求工具类
 */
public class HttpUtils<T> {

    public HttpUtils()
    {

    }

    /**
     * get请求
     * @param url
     * @param listener
     * @param errorListener
     */
    public void doVolleyGet(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        KJJsonObjectRequest request = new KJJsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        VolleyUtil.addRequest(request);
    }

    /**
     * get请求(Utf)
     * @param url
     * @param listener
     * @param errorListener
     */
    public void doVolleyGetUtf(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {
        JsonUTF8Request request = new JsonUTF8Request(Request.Method.GET, url, null, listener, errorListener);
        VolleyUtil.addRequest(request);
    }

    /**
     * post请求
     * @param t
     * @param url
     * @param param
     * @param listener
     * @param errorListener
     */
    public void doVolleyPost(T t, String url, Map param, Response.Listener<T> listener, Response.ErrorListener errorListener)
    {
        GsonRequest request = new GsonRequest (Request.Method.POST, url, t.getClass(), null, param, listener, errorListener);
        VolleyUtil.addRequest(request);
    }

    /**
     *
     * @方法描述：get请求
     * @方法名：getByHttpConnection
     * @参数：@param url
     * @参数：@return
     * @返回：InputStream
     * @exception
     * @since
     */
    public String doGet(String url)
    {
        HttpURLConnection conn = null;
        InputStream inStream = null;
        String jsonStr = null;
        URL get_url;
        try
        {
            get_url = new URL(url);
            conn = (HttpURLConnection) get_url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(8000);
            int statusCode = conn.getResponseCode();
            if (200 == statusCode )
            {
                inStream = conn.getInputStream();
                byte[] dataByte = SystemTools.readInputStream(inStream);
                jsonStr = new String(dataByte);
            } else
            {
                // 获取数据失败
                jsonStr = "{\"resultCode\":50601,\"systemResultCode\":1}";
            }
        }catch( ConnectTimeoutException ctimeoutex){
            jsonStr = "{\"resultCode\":50001,\"resultDescription\":\"网络请求超时，请稍后重试\",\"systemResultCode\":1}";
        }catch (SocketTimeoutException stimeoutex) {
            jsonStr = "{\"resultCode\":50001,\"resultDescription\":\"网络请求超时，请稍后重试\",\"systemResultCode\":1}";
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            // 服务无响应
            jsonStr = "{\"resultCode\":50001,\"resultDescription\":\"系统请求失败\",\"systemResultCode\":1}";
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            // 服务无响应
            jsonStr = "{\"resultCode\":50001,\"resultDescription\":\"系统请求失败\",\"systemResultCode\":1}";
        } finally
        {
            try
            {
                if (null != inStream)
                {
                    inStream.close();
                }
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (null != conn)
            {
                conn.disconnect();
            }
        }

        return jsonStr;
    }

    public void doMallPay(final Activity aty, final Context context, final Handler mHandler, final BaseApplication application, String url, final MallPayModel mallPayModel, final ProgressPopupWindow payProgress, final TextView titleView, final WindowManager wManager, final String  notifyurl){
        final KJJsonObjectRequest re = new KJJsonObjectRequest (Request.Method.GET, url, null, new Response.Listener<JSONObject >(){


            @Override
            public void onResponse(JSONObject response) {

                JSONUtil<MallOrderModel> jsonUtil = new JSONUtil<MallOrderModel>();
                MallOrderModel orderInfo = new MallOrderModel();
                orderInfo = jsonUtil.toBean(response.toString (), orderInfo);
                if(200 == orderInfo.getCode ()) {
                    if ( null != orderInfo ) {
                        MallOrderModel.OrderData order = orderInfo.getData ( );
                        if ( null == order)
                        {
                            //支付信息获取错误
                            payProgress.dismissView ( );
                            NoticePopWindow noticePop = new NoticePopWindow ( context, aty, wManager, "获取订单信息失败。");
                            noticePop.showNotice ( );
                            noticePop.showAtLocation (
                                    titleView,
                                    Gravity.CENTER, 0, 0
                            );
                        }
                        else
                        {
                            mallPayModel.setWxFee ( String.valueOf(Integer.parseInt(new DecimalFormat("0").format(100 * format2Decimal(order.getFinal_Amount())))) );
                            mallPayModel.setOrderNo(mallPayModel.getTradeNo());
                            mallPayModel.setDetail(order.getToStr ( ) );
                            mallPayModel.setWxCallbackUrl(notifyurl);

                            if ( null != order ) {
                                payProgress.dismissView ( );
                                PayPopWindow payPopWindow = new PayPopWindow ( aty, context, mHandler, application, mallPayModel, "微信支付", "支付宝支付" );
                                payPopWindow.showAtLocation (
                                        titleView,
                                        Gravity.BOTTOM, 0, 0
                                );
                                //支付
                        /*if("1".equals ( payModel.getPaymentType () ) || "7".equals ( payModel.getPaymentType () ))
                        {
                            //添加支付宝回调路径
                            payModel.setNotifyurl ( application.obtainMerchantUrl () + application.readAlipayNotify ( ) );
                            //alipay
                            PayFunc payFunc = new PayFunc ( context, payModel, application, mHandler, aty, payProgress );
                            payFunc.aliPay ( );

                        }
                        else if("2".equals ( payModel.getPaymentType () ) || "9".equals ( payModel.getPaymentType () ))
                        {
                            payModel.setAttach ( payModel.getCustomId ()+"_0" );
                            //添加微信回调路径
                            payModel.setNotifyurl ( application.obtainMerchantUrl ( ) + application.readWeixinNotify() );
                            PayFunc payFunc = new PayFunc ( context, payModel, application, mHandler, aty, payProgress );
                            payFunc.wxPay ( );

                        }*/
                            }

                        }
                    }
                    else
                    {
                        payProgress.dismissView ( );
                        NoticePopWindow noticePop = new NoticePopWindow ( context, aty, wManager, "获取订单信息失败。");
                        noticePop.showNotice ();
                        noticePop.showAtLocation (
                                titleView,
                                Gravity.CENTER, 0, 0
                        );
                    }
                }
                else
                {
                    //支付信息获取错误
                    payProgress.dismissView ( );
                    NoticePopWindow noticePop = new NoticePopWindow ( context, aty, wManager, "获取订单信息失败。");
                    noticePop.showNotice ( );
                    noticePop.showAtLocation (
                            titleView,
                            Gravity.CENTER, 0, 0
                    );
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                payProgress.dismissView ( );
                NoticePopWindow noticePop = new NoticePopWindow ( context, aty, wManager, "支付服务不可用");
                noticePop.showNotice ( );
                noticePop.showAtLocation(
                        titleView,
                        Gravity.CENTER, 0, 0
                );
            }


        });
        Volley.newRequestQueue ( context ).add( re);
    }

    //保留2位小数
    private double format2Decimal(double d)
    {
        BigDecimal bg = new BigDecimal ( d );
        return bg.setScale ( 2,   BigDecimal.ROUND_HALF_UP).intValue();
    }
}
