package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by lenovo on 2016/3/10.
 */
public class AppWinningInfoModel extends BaseModel {
    /**
     * 用户ID
     */
    Long userId;

    /**
     * 红包信息
     */
    String winningInfo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWinningInfo() {
        return winningInfo;
    }

    public void setWinningInfo(String winningInfo) {
        this.winningInfo = winningInfo;
    }
}
