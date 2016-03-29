package com.huotu.fanmore.pinkcatraiders.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/2/29.
 */
public class PastListModel extends BaseModel {
    long attendAmount;
    String city;
    long   date;
    String ip;
    long issueId;
    long luckyNumber;
    String nickName;
    int status;
    String userHeadUrl;
    long userId;

    public long getAttendAmount() {
        return attendAmount;
    }

    public void setAttendAmount(long attendAmount) {
        this.attendAmount = attendAmount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public long getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(long luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
