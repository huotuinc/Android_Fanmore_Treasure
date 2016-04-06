package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by lenovo on 2016/3/5.
 */
public class AppDeliveryModel extends BaseModel {

    //中奖时间
    private String	awardingDate;
    //确认收货地址时间
    private String	confirmAddressTime;
    //发货状态
    private int	deliveryStatus;
    //发货时间
    private String	deliveryTime;
    //详细地址
    private String	details;
    //期号
    private long	issueId;
    //联系电话
    private String	mobile;

    private long	pid;
    //收货人
    private String	receiver;
    //确认收货时间
    private String	RecieveGoodsTime;
    private long	userId;
    private String	username;

    public String getAwardingDate() {
        return awardingDate;
    }

    public void setAwardingDate(String awardingDate) {
        this.awardingDate = awardingDate;
    }

    public String getConfirmAddressTime() {
        return confirmAddressTime;
    }

    public void setConfirmAddressTime(String confirmAddressTime) {
        this.confirmAddressTime = confirmAddressTime;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRecieveGoodsTime() {
        return RecieveGoodsTime;
    }

    public void setRecieveGoodsTime(String recieveGoodsTime) {
        RecieveGoodsTime = recieveGoodsTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
