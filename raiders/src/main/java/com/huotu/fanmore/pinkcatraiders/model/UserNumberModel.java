package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/3/7.
 */
public class UserNumberModel extends BaseModel {
    String buyTime;
    String nickName;
    String number;

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
