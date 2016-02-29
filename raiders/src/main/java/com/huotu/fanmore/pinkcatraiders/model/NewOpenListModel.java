package com.huotu.fanmore.pinkcatraiders.model;



import com.mob.tools.utils.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/25.
 */
public class NewOpenListModel extends BaseModel {
    BigDecimal areaAmount;
    long attendAmount;
    long issueId;
    long luckyNumber;
    String nickName;
    String pictureUrl;
    long pid;
    int status;
    long time;
    String title;
    long toAwardingTime;

    public BigDecimal getAreaAmount() {
        return areaAmount;
    }

    public void setAreaAmount(BigDecimal areaAmount) {
        this.areaAmount = areaAmount;
    }

    public long getAttendAmount() {
        return attendAmount;
    }

    public void setAttendAmount(long attendAmount) {
        this.attendAmount = attendAmount;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getToAwardingTime() {
        return toAwardingTime;
    }

    public void setToAwardingTime(long toAwardingTime) {
        this.toAwardingTime = toAwardingTime;
    }
}
