package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 中奖纪录model
 */
public
class AppUserBuyFlowModel extends BaseModel {

    private long amount;
    private String awardingDate;
    private String defaultPictureUrl;
    private String issueId;
    private long luckyNumber;
    private long pid;
    private String title;
    private long toAmount;
    private String time;
    private long	deliveryId;
    //发货状态 (0, "获得奖品"), (1, "确认收货地址"), (2, "等待奖品派发"), (4, "确认收货"), (5, "已收货"), (6, "已晒单")
    private int	deliveryStatus;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public
    long getAmount ( ) {

        return amount;
    }

    public
    void setAmount ( long amount ) {

        this.amount = amount;
    }

    public
    String getAwardingDate ( ) {

        return awardingDate;
    }

    public
    void setAwardingDate ( String awardingDate ) {

        this.awardingDate = awardingDate;
    }

    public
    String getDefaultPictureUrl ( ) {

        return defaultPictureUrl;
    }

    public
    void setDefaultPictureUrl ( String defaultPictureUrl ) {

        this.defaultPictureUrl = defaultPictureUrl;
    }

    public
    String getIssueId ( ) {

        return issueId;
    }

    public
    void setIssueId ( String issueId ) {

        this.issueId = issueId;
    }

    public
    long getLuckyNumber ( ) {

        return luckyNumber;
    }

    public
    void setLuckyNumber ( long luckyNumber ) {

        this.luckyNumber = luckyNumber;
    }

    public
    long getPid ( ) {

        return pid;
    }

    public
    void setPid ( long pid ) {

        this.pid = pid;
    }

    public
    String getTitle ( ) {

        return title;
    }

    public
    void setTitle ( String title ) {

        this.title = title;
    }

    public
    long getToAmount ( ) {

        return toAmount;
    }

    public
    void setToAmount ( long toAmount ) {

        this.toAmount = toAmount;
    }
}
