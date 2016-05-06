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
 * Created by lenovo on 2016/3/24.
 */
public class AuthMallParamUtils {

    private
    BaseApplication application;

    private String url;

    private long timestamp;
    private
    Context context;

    public
    AuthMallParamUtils(BaseApplication application, long timestamp, String url, Context context)
    {
        this.application = application;
        this.timestamp = timestamp;
        this.url = url;
        this.context = context;
    }

    public String obtainUrlOrder()
    {
        StringBuilder builder = new StringBuilder (  );
        try {
            Map< String, String > paramMap = new HashMap< String, String >( );
            //获取url中的参数
            String params = url.substring ( url.indexOf ( "?" ) + 1, url.length ( ) );
            String[] str = params.split ( "&" );
            if ( str.length > 0 ) {
                for ( String map : str ) {
                    //获取参数
                    String[] values = map.split ( "=" );
                    if ( 2 == values.length ) {
                        paramMap.put ( values[ 0 ], URLEncoder.encode(values[1], "UTF-8") );
                    }
                    else if ( 1 == values.length ) {
                        paramMap.put ( values[ 0 ], null );
                    }
                }
            }

            //添加额外固定参数
            paramMap.put ( "version", application.getAppVersion () );
            paramMap.put ( "operation", "BUYER_ANDROID_2015DC" );
            //1、timestamp
            paramMap.put ( "timestamp", URLEncoder.encode ( String.valueOf ( timestamp ), "UTF-8" ) );
            //appid
            paramMap.put ( "appid", URLEncoder.encode ( "huotuacf89c9231848c9f49" , "UTF-8" ));
            //生成sigin
            paramMap.put ( "sign", getSign ( paramMap ) );

            builder.append ( url );
            builder.append ( "&timestamp=" + paramMap.get ( "timestamp" ) );
            builder.append ( "&appid="+paramMap.get ( "appid" ) );
            builder.append ( "&sign="+paramMap.get ( "sign" ) );
            builder.append ( "&version=" + application.getAppVersion () );
            builder.append ( "&operation=" + "BUYER_ANDROID_2015DC" );

            return builder.toString ();
        }
        catch ( UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            return null;
        }
    }

    private String getSign(Map map)
    {
        String values = this.doSort(map);
        String signHex = EncryptUtil.getInstance().encryptMd532(values);
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
        Iterator it = treeMap.entrySet ().iterator();
        while(it.hasNext ())
        {
            Map.Entry entry =(Map.Entry) it.next();
            buffer.append ( entry.getKey ()+"=" );
            buffer.append ( entry.getValue ()+"&" );
        }
        String suffix = buffer.substring ( 0, buffer.length ()-1 )+"0ad8abe244331aacf89c9231848c9f49";
        return suffix;
    }
}
