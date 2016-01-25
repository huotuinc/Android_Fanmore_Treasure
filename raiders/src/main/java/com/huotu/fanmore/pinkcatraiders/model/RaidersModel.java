package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 夺宝model
 */
public class RaidersModel extends ProductModel {

    private long pid;
    private long issueId;
    private String partnerNo;
    private long attendAmount;
    private String awardingDate;
    private long lunkyNumber;
    private long raidersType;
    private String pictureUrl;
    private long remainAmount;
    private String title;
    private long toAmount;
    private String winner;
    private long winnerAttendAmount;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public long getAttendAmount() {
        return attendAmount;
    }

    public void setAttendAmount(long attendAmount) {
        this.attendAmount = attendAmount;
    }

    public String getAwardingDate() {
        return awardingDate;
    }

    public void setAwardingDate(String awardingDate) {
        this.awardingDate = awardingDate;
    }

    public long getLunkyNumber() {
        return lunkyNumber;
    }

    public void setLunkyNumber(long lunkyNumber) {
        this.lunkyNumber = lunkyNumber;
    }

    public long getRaidersType() {
        return raidersType;
    }

    public void setRaidersType(long raidersType) {
        this.raidersType = raidersType;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getToAmount() {
        return toAmount;
    }

    public void setToAmount(long toAmount) {
        this.toAmount = toAmount;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public long getWinnerAttendAmount() {
        return winnerAttendAmount;
    }

    public void setWinnerAttendAmount(long winnerAttendAmount) {
        this.winnerAttendAmount = winnerAttendAmount;
    }
}
