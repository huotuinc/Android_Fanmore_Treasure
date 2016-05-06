package com.huotu.fanmore.pinkcatraiders.uitls;

import android.content.Context;
import android.text.TextUtils;

import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    public String obtainUrl()
    {
        StringBuilder builder = new StringBuilder (  );
        try {
            Map< String, String > paramMap = new HashMap< String, String > ( );




            //paramMap.put ( "version", application.getAppVersion ( context ) );
            // paramMap.put ( "operation", Constant.OPERATION_CODE );
            paramMap.put ( "buserId", String.valueOf(application.readUerId()) );
            paramMap.put ( "customerid", String.valueOf(application.readUerId()) );
            //添加额外固定参数
            //1、timestamp
            paramMap.put ( "timestamp", URLEncoder.encode(String.valueOf(timestamp), "UTF-8") );
            //appid
            paramMap.put ( "appid", URLEncoder.encode ( "huotuacf89c9231848c9f49", "UTF-8" ));
            //unionid
            paramMap.put (
                    "unionid", URLEncoder.encode(
                            String.valueOf(application.readUerId()),
                            "UTF-8") );
            //生成sigin
            paramMap.put("sign", getSign(paramMap));
              String url="cosytest.51flashmall.com";
            builder.append ( url );
            builder.append ( "?timestamp=" + paramMap.get ( "timestamp" ) );
            builder.append ( "&customerid="+application.readUerId() );
            builder.append ( "&appid="+paramMap.get ( "appid" ) );
            builder.append ( "&unionid="+paramMap.get ( "unionid" ) );
            builder.append ( "&sign="+paramMap.get ( "sign" ) );
            builder.append ( "&buserId="+application.readUerId ( ) );
//                builder.append ( "&version=" + application.getAppVersion ( context ) );
//                builder.append ( "&operation=" + Constant.OPERATION_CODE );


            return builder.toString ();
        }
        catch ( UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch blockL
            L.e ( e.getMessage ( ) );
            return null;
        }

    }
    private String getSign(Map map)
    {
        String values = this.doSort(map);
        L.i ( "sign", values );
        // values = URLEncoder.encode(values);
        //String signHex =DigestUtils.md5DigestAsHex(values.toString().getBytes("UTF-8")).toLowerCase();
        String signHex = EncryptUtil.getInstance().encryptMd532(values);
        L.i("signHex", signHex);
        return signHex;
    }

    /**
     *
     * @方法描述：获取sign码第二步：参数排序
     * @方法名：doSort
     * @参数：@param map
     * @参数：@return
     * @返回：String
     * @exception
     * @since
     */
    private String doSort(Map<String, String> map)
    {
        //将MAP中的key转成小写
        Map<String, String> lowerMap = new HashMap< String, String > (  );
        Iterator lowerIt = map.entrySet ().iterator ();
        while ( lowerIt.hasNext () )
        {
            Map.Entry entry = ( Map.Entry ) lowerIt.next ();
            Object value = entry.getValue ( );
            if( ! TextUtils.isEmpty(String.valueOf(value)) )
            {
                lowerMap.put ( String.valueOf ( entry.getKey () ).toLowerCase (), String.valueOf ( value ) );
            }
        }

        TreeMap<String, String> treeMap = new TreeMap< String, String > ( lowerMap );
        StringBuffer buffer = new StringBuffer();
        Iterator it = treeMap.entrySet ().iterator ();
        while(it.hasNext ())
        {
            Map.Entry entry =(Map.Entry) it.next();
            buffer.append ( entry.getKey ()+"=" );
            buffer.append ( entry.getValue ()+"&" );
        }
        String suffix = buffer.substring ( 0, buffer.length ()-1 )+Contant.APP_SECRET;
        return suffix;
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
        params.put("version", application.getAppVersion());
        //operation
        params.put("operation", Contant.OPERATION);
        //imei
        String imei = application.getPhoneIMEI();
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
