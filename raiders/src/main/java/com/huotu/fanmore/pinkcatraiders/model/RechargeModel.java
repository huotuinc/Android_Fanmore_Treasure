package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 充值model
 */
public class RechargeModel extends BaseModel {

    /**
     * 0：微信
     * 1：支付宝
     */
    private int payChannel;
    private String payTime;
    private int payStatus;
    private double money;

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
