package com.huotu.fanmore.pinkcatraiders.uitls;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
}
