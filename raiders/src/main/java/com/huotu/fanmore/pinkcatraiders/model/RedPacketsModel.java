package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/18.
 */
public class RedPacketsModel extends BaseModel {
    private Date endTime;
    private BigDecimal fullMoney;
    private BigDecimal minusMoney;
    private String remark;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getFullMoney() {
        return fullMoney;
    }

    public void setFullMoney(BigDecimal fullMoney) {
        this.fullMoney = fullMoney;
    }

    public BigDecimal getMinusMoney() {
        return minusMoney;
    }

    public void setMinusMoney(BigDecimal minusMoney) {
        this.minusMoney = minusMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Date startTime;
    private String title;
    private Long id;
}
