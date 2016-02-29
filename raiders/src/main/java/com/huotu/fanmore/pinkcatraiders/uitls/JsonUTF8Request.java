package com.huotu.fanmore.pinkcatraiders.uitls;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 处理中文参数请求对象
 */
public
class JsonUTF8Request extends JsonRequest<JSONObject > {
    public JsonUTF8Request(int method, String url, JSONObject jsonRequest,
                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
              errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            response.headers.put(
                    HTTP.CONTENT_TYPE,
                                 response.headers.get("content-type"));
            response.headers.put ( HTTP.CHARSET_PARAM,"utf-8" );
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError (e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}