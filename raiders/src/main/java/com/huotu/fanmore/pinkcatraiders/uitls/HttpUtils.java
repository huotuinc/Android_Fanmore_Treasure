package com.huotu.fanmore.pinkcatraiders.uitls;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
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
}
