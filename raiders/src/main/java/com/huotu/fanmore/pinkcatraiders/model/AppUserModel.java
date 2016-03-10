package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;

/**
 * 用户实体
 */
public class AppUserModel extends BaseModel  {
    int enabled;
    Long userId;
    String moblie;

    public int getQqBanded() {
        return qqBanded;
    }

    public void setQqBanded(int qqBanded) {
        this.qqBanded = qqBanded;
    }

    public int getWexinBanded() {
        return wexinBanded;
    }

    public void setWexinBanded(int wexinBanded) {
        this.wexinBanded = wexinBanded;
    }

    BigDecimal money;
    int mobileBanded;
    int qqBanded;
    int wexinBanded;


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
    /**
     * 是否有密码 有：1 无：0
     */
    private int hasPassword;

    /**
     * 是否已经分享红包 是：1 否：0
     */
    private int hasShareRed;

    /**
     * 用户积分
     */
    private Long integral;

    /**
     * 五种红包状态
     *
     *注册送红包 开启：1 关闭：0
     */
    private int regSendRed;

    /**
     * 购买后第一次分享
     *
     * 开启：1 关闭：0
     */
    private int buyAndShare;

    /**
     * 充值送红包
     *
     * 开启：1 关闭：0
     */
    private int putMoney;

    public int getXiuxiuxiu() {
        return xiuxiuxiu;
    }

    public void setXiuxiuxiu(int xiuxiuxiu) {
        this.xiuxiuxiu = xiuxiuxiu;
    }

    public int getGetFromShare() {
        return getFromShare;
    }

    public void setGetFromShare(int getFromShare) {
        this.getFromShare = getFromShare;
    }

    public int getPutMoney() {
        return putMoney;
    }

    public void setPutMoney(int putMoney) {
        this.putMoney = putMoney;
    }

    public int getBuyAndShare() {
        return buyAndShare;
    }

    public void setBuyAndShare(int buyAndShare) {
        this.buyAndShare = buyAndShare;
    }

    public int getRegSendRed() {
        return regSendRed;
    }

    public void setRegSendRed(int regSendRed) {
        this.regSendRed = regSendRed;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public int getHasShareRed() {
        return hasShareRed;
    }

    public void setHasShareRed(int hasShareRed) {
        this.hasShareRed = hasShareRed;
    }

    public int getHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(int hasPassword) {
        this.hasPassword = hasPassword;
    }

    /**
     * 领取别人的红包
     *

     * 开启：1 关闭：0
     */
    private int getFromShare;

    /**
     * 咻咻咻红包
     *
     * 开启：1 关闭：0
     */
    private int xiuxiuxiu;


    private MyAddressListModel appMyAddressListModel;

    public MyAddressListModel getAppMyAddressListModel() {
        return appMyAddressListModel;
    }

    public void setAppMyAddressListModel(MyAddressListModel appMyAddressListModel) {
        this.appMyAddressListModel = appMyAddressListModel;
    }
}
