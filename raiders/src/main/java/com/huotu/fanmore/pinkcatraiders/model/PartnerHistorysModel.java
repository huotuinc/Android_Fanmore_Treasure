package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 参与历史
 */
public class PartnerHistorysModel extends BaseModel {

    //参与人数
    private long attendAmount;
    //城市
    private String city;
    //时间
    private String date;
    //ip
    private String ip;
    //昵称
    private String nickName;
    //pid
    private long pid;
    //头像
    private String userHeadUrl;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }
}
