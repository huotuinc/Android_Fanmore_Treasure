package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AppUserModel  {
    boolean enabled;
    Long userId;
    String moblie;
    boolean mobileBanded;
    BigDecimal money;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public boolean isMobileBanded() {
        return mobileBanded;
    }

    public void setMobileBanded(boolean mobileBanded) {
        this.mobileBanded = mobileBanded;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserFormType() {
        return userFormType;
    }

    public void setUserFormType(Integer userFormType) {
        this.userFormType = userFormType;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String realName;
    String token;
    Integer userFormType;
    String userHead;
    String username;

}
