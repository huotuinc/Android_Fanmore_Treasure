package com.huotu.fanmore.pinkcatraiders.uitls;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;

import org.json.JSONObject;

/**
 * 自定义volley网络GET请求类
 */
public class KJJsonObjectRequest extends JsonObjectRequest {

    public KJJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(50000, 0, 1.0f));
        this.setTag(Contant.VOLLEY_TAG);
    }

    public KJJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(50000, 0, 1.0f));
        this.setTag(Contant.VOLLEY_TAG);
    }
}
