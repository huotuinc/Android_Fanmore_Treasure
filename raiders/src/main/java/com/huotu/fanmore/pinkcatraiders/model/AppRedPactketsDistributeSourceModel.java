package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 判断是否有红包活动
 */
public class AppRedPactketsDistributeSourceModel extends BaseModel{

    /**
     * 数量
     */
    private Long amount;

    /**
     * 开始倒计时
     */
    private Long startTime;

    /**
     * 结束倒计时
     */
    private Long endTime;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
