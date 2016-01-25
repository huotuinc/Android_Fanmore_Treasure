package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 获奖人
 */
public class WinnerModel extends AccountModel {

    private String winnerName;
    private double period;
    private String luckyNo;
    private String announcedTime;

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    public String getLuckyNo() {
        return luckyNo;
    }

    public void setLuckyNo(String luckyNo) {
        this.luckyNo = luckyNo;
    }

    public String getAnnouncedTime() {
        return announcedTime;
    }

    public void setAnnouncedTime(String announcedTime) {
        this.announcedTime = announcedTime;
    }
}
