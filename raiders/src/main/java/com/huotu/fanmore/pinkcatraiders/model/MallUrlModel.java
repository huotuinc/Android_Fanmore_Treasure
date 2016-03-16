package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/3/16.
 */
public class MallUrlModel extends BaseModel {
    String loginUrl;
    String bottomNavUrl;
    String orderRequestUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getBottomNavUrl() {
        return bottomNavUrl;
    }

    public void setBottomNavUrl(String bottomNavUrl) {
        this.bottomNavUrl = bottomNavUrl;
    }

    public String getOrderRequestUrl() {
        return orderRequestUrl;
    }

    public void setOrderRequestUrl(String orderRequestUrl) {
        this.orderRequestUrl = orderRequestUrl;
    }
}
