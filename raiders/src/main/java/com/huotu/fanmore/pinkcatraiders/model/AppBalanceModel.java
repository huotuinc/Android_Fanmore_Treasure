package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;

/**
 * 确认支付结果
 */
public class AppBalanceModel extends BaseModel {

    //需要支付的金额
    private BigDecimal money;
    //红包结束时间
    private String	redPacketsEndTime;
    //需要达到的金额
    private BigDecimal	redPacketsFullMoney;
    // 红包id
    private long	redPacketsId;
    //红包抵扣金额
    private BigDecimal	redPacketsMinusMoney;
    //红包数量
    private int	redPacketsNumber;
    //红包备注
    private String	redPacketsRemark;
    //红包开始时间
    private String	redPacketsStartTime;
    //红包使用状态
    private RedPacketsStatus	redPacketsStatus;
    //红包标题(如满50减5)
    private String	redPacketsTitle;
    //总计
    private BigDecimal	totalMoney;

    public class RedPacketsStatus
    {
        private String	name;
        private int	value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRedPacketsEndTime() {
        return redPacketsEndTime;
    }

    public void setRedPacketsEndTime(String redPacketsEndTime) {
        this.redPacketsEndTime = redPacketsEndTime;
    }

    public BigDecimal getRedPacketsFullMoney() {
        return redPacketsFullMoney;
    }

    public void setRedPacketsFullMoney(BigDecimal redPacketsFullMoney) {
        this.redPacketsFullMoney = redPacketsFullMoney;
    }

    public long getRedPacketsId() {
        return redPacketsId;
    }

    public void setRedPacketsId(long redPacketsId) {
        this.redPacketsId = redPacketsId;
    }

    public BigDecimal getRedPacketsMinusMoney() {
        return redPacketsMinusMoney;
    }

    public void setRedPacketsMinusMoney(BigDecimal redPacketsMinusMoney) {
        this.redPacketsMinusMoney = redPacketsMinusMoney;
    }

    public int getRedPacketsNumber() {
        return redPacketsNumber;
    }

    public void setRedPacketsNumber(int redPacketsNumber) {
        this.redPacketsNumber = redPacketsNumber;
    }

    public String getRedPacketsRemark() {
        return redPacketsRemark;
    }

    public void setRedPacketsRemark(String redPacketsRemark) {
        this.redPacketsRemark = redPacketsRemark;
    }

    public String getRedPacketsStartTime() {
        return redPacketsStartTime;
    }

    public void setRedPacketsStartTime(String redPacketsStartTime) {
        this.redPacketsStartTime = redPacketsStartTime;
    }

    public RedPacketsStatus getRedPacketsStatus() {
        return redPacketsStatus;
    }

    public void setRedPacketsStatus(RedPacketsStatus redPacketsStatus) {
        this.redPacketsStatus = redPacketsStatus;
    }

    public String getRedPacketsTitle() {
        return redPacketsTitle;
    }

    public void setRedPacketsTitle(String redPacketsTitle) {
        this.redPacketsTitle = redPacketsTitle;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
