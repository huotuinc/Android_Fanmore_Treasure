package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 充值model
 */
public class RechargeModel extends BaseModel {

    /**
     * 0：微信
     * 1：支付宝
     */
    private int moneyFlowType;
    private String time;
    private String remark;
    private double money;
    private long pid;

    public
    int getMoneyFlowType ( ) {

        return moneyFlowType;
    }

    public
    void setMoneyFlowType ( int moneyFlowType ) {

        this.moneyFlowType = moneyFlowType;
    }

    public
    String getTime ( ) {

        return time;
    }

    public
    void setTime ( String time ) {

        this.time = time;
    }

    public
    String getRemark ( ) {

        return remark;
    }

    public
    void setRemark ( String remark ) {

        this.remark = remark;
    }

    public
    double getMoney ( ) {

        return money;
    }

    public
    void setMoney ( double money ) {

        this.money = money;
    }

    public
    long getPid ( ) {

        return pid;
    }

    public
    void setPid ( long pid ) {

        this.pid = pid;
    }
}
