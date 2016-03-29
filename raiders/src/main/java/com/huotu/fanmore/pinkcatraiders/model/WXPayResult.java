package com.huotu.fanmore.pinkcatraiders.model;

import java.io.Serializable;

/**
 * 微信支付结果类
 */
public
class WXPayResult extends BaseModel {

    private int code;

    private String message;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
