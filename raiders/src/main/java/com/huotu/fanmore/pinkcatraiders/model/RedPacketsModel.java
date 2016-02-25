package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的红包model
 */
public class RedPacketsModel extends BaseModel {
    private String endTime;
    private double fullMoney;
    private double minusMoney;
    private String remark;
    private String startTime;
    private String title;
    private Long pid;

    public
    String getEndTime ( ) {

        return endTime;
    }

    public
    void setEndTime ( String endTime ) {

        this.endTime = endTime;
    }

    public
    double getFullMoney ( ) {

        return fullMoney;
    }

    public
    void setFullMoney ( double fullMoney ) {

        this.fullMoney = fullMoney;
    }

    public
    double getMinusMoney ( ) {

        return minusMoney;
    }

    public
    void setMinusMoney ( double minusMoney ) {

        this.minusMoney = minusMoney;
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
    String getStartTime ( ) {

        return startTime;
    }

    public
    void setStartTime ( String startTime ) {

        this.startTime = startTime;
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
    Long getPid ( ) {

        return pid;
    }

    public
    void setPid ( Long pid ) {

        this.pid = pid;
    }
}
