package com.huotu.fanmore.pinkcatraiders.uitls;

import android.content.Context;

import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 鉴权参数类
 */
public class AuthParamUtils {

    private BaseApplication application;
    private long timestamp;
    private
    Context context;

    public
    AuthParamUtils( BaseApplication application, long timestamp, Context context)
    {
        this.timestamp = timestamp;
        this.context = context;
        this.application = application;
    }

    /**
     * 获取post请求的参数形式，map
     * @return
     */
    public Map<String, Object> obtainPostParam(Map<String, Object> params)
    {
        //添加公共参数
        Map<String, Object> allParams = appendParams(params);
        //获取sign
        String signStr = obtainSign(allParams);
        allParams.put("sign", signStr);
        return allParams;
    }

    /**
     * 生成sign
     * @param params
     * @return
     */
    private String obtainSign(Map<String, Object> params)
    {
        Map<String, Object> signMap = new HashMap<String, Object>();
        //安全码
        signMap.put("appsecret", Contant.APP_SECRET);
        signMap.putAll(params);
        //排序
        TreeMap<String, Object> orderMap = new TreeMap<String, Object>(signMap);
        StringBuilder builder = new StringBuilder();
        Iterator iterator = orderMap.entrySet().iterator ( );
        while (iterator.hasNext())
        {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            if(null!=entry.getValue() && !"".equals(entry.getValue().toString()))
            {
                builder.append(entry.getValue());
            }
        }
        return EncryptUtil.getInstance().encryptMd532(builder.toString());
    }

    public Map<String, Object> obtainAllParamUTF8(Map<String, Object> params)
    {
        //添加公共参数
        Map<String, Object> allParams = appendParams ( params );
        return allParams;
    }

    public String obtainSignUTF8(Map<String, Object> params)
    {
        return obtainSign(params);
    }

    public String obtainGetParamUTF8(Map<String, Object> params)
    {
        StringBuilder builder = new StringBuilder();
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            builder.append("&"+entry.getKey()+"="+entry.getValue());
        }
        String paramStr = builder.toString();
        //替换第一个&为？
        paramStr = paramStr.replaceFirst(String.valueOf(paramStr.charAt(0)), "?");

        return paramStr;
    }

    /**
     * 获取get请求的参数形式
     * @return
     */
    public String obtainGetParam(Map<String, Object> params)
    {
        //添加公共参数
        Map<String, Object> allParams = appendParams(params);
        //获取sign
        String signStr = obtainSign(allParams);
        allParams.put("sign", signStr);
        StringBuilder builder = new StringBuilder();
        Iterator iterator = allParams.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            builder.append("&"+entry.getKey()+"="+entry.getValue());
        }
        String paramStr = builder.toString();
        //替换第一个&为？
        paramStr = paramStr.replaceFirst(String.valueOf(paramStr.charAt(0)), "?");

        return paramStr;
    }

    /**
     * ONE
     * @param params
     * @return
     */
    private Map<String, Object> appendParams(Map<String, Object> params)
    {
        //加入公共参数
        //appKey
        params.put("appKey", Contant.APPKEY);
        //lat
        params.put("lat", "");
        //lng
        params.put("lng", "");
        //cityCode
        params.put("cityCode", "");
        //cityCode
        params.put("cityCode", "");
        //cpaCode
        params.put("cpaCode", "");
        //时间
        params.put("timestamp", String.valueOf(timestamp));
        //版本号
        params.put("version", application.getAppVersion(context));
        //operation
        params.put("operation", Contant.OPERATION);
        //imei
        String imei = application.getPhoneIMEI(context);
        if(null != imei && !"".equals(imei))
        {
            params.put("imei", imei);
        }
        else
        {
            params.put("imei", "");
        }
        //token
        String token = application.obtainToken();
        if(null !=token && !"".equals(token))
        {
            params.put("token", application.obtainToken());
        }
        else
        {
            params.put("token", "");
        }

        return params;
    }
}
