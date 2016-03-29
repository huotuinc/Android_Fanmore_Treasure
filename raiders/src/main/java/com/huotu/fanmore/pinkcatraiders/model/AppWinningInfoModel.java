package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by lenovo on 2016/3/10.
 */
public class AppWinningInfoModel extends BaseModel {
    /**
     * 用户ID
     */
    long userId;
    long rid;
    /**
     * 红包信息
     */
    String winningInfo;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public String getWinningInfo() {
        return winningInfo;
    }

    public void setWinningInfo(String winningInfo) {
        this.winningInfo = winningInfo;
    }
}
