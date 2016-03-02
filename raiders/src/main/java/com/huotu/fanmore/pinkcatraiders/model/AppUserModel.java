package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;

/**
 * 用户实体
 */
public class AppUserModel extends BaseModel  {
    int enabled;
    Long userId;
    String moblie;
    int mobileBanded;
    BigDecimal money;



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

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getMobileBanded() {
        return mobileBanded;
    }

    public void setMobileBanded(int mobileBanded) {
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

    private MyAddressListModel addressModel;

    public
    MyAddressListModel getAddressModel ( ) {

        return addressModel;
    }

    public
    void setAddressModel ( MyAddressListModel addressModel ) {

        this.addressModel = addressModel;
    }
}
