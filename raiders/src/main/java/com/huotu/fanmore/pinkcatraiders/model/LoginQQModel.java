package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/1/27.
 */
public class LoginQQModel extends BaseModel {
    private String icon;
    private String secret;
    private String expiewsTime;
    private String token;
    private String nickname;
    private String iconQzone;
    private String secretType;
    private String pfkey;
    private String gender;
    private String weibo;
    private String pf;
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getExpiewsTime() {
        return expiewsTime;
    }

    public void setExpiewsTime(String expiewsTime) {
        this.expiewsTime = expiewsTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIconQzone() {
        return iconQzone;
    }

    public void setIconQzone(String iconQzone) {
        this.iconQzone = iconQzone;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getExpiresln() {
        return expiresln;
    }

    public void setExpiresln(String expiresln) {
        this.expiresln = expiresln;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public String getPfkey() {
        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public String getSecretType() {
        return secretType;
    }

    public void setSecretType(String secretType) {
        this.secretType = secretType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    private String expiresln;
    private String pay_token;
}
