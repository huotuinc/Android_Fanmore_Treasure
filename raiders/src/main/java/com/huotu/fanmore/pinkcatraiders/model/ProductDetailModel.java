package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 商品详情model
 */
public class ProductDetailModel extends BaseModel {

    private String character;
    private long issueId;
    private String link;
    private List<String> pictureUrl;
    private long pid;
    private long remainAmount;
    private int status;
    private String statusName;
    private long stepAmount;
    private String title;
    private long toAmount;
    private String awardingDate;
    private String awardingUserBuyCount;
    private String awardingUserCityName;
    private String awardingUserHead;
    private long awardingUserId;
    private String awardingUserIp;
    private String awardingUserName;
    private String firstBuyTime;
    private long 	luckyNumber;
    private List<Long> numbers;
    private long 	remainSecond;

    public String getAwardingDate() {
        return awardingDate;
    }

    public void setAwardingDate(String awardingDate) {
        this.awardingDate = awardingDate;
    }

    public String getAwardingUserBuyCount() {
        return awardingUserBuyCount;
    }

    public void setAwardingUserBuyCount(String awardingUserBuyCount) {
        this.awardingUserBuyCount = awardingUserBuyCount;
    }

    public String getAwardingUserCityName() {
        return awardingUserCityName;
    }

    public void setAwardingUserCityName(String awardingUserCityName) {
        this.awardingUserCityName = awardingUserCityName;
    }

    public String getAwardingUserHead() {
        return awardingUserHead;
    }

    public void setAwardingUserHead(String awardingUserHead) {
        this.awardingUserHead = awardingUserHead;
    }

    public long getAwardingUserId() {
        return awardingUserId;
    }

    public void setAwardingUserId(long awardingUserId) {
        this.awardingUserId = awardingUserId;
    }

    public String getAwardingUserIp() {
        return awardingUserIp;
    }

    public void setAwardingUserIp(String awardingUserIp) {
        this.awardingUserIp = awardingUserIp;
    }

    public String getAwardingUserName() {
        return awardingUserName;
    }

    public void setAwardingUserName(String awardingUserName) {
        this.awardingUserName = awardingUserName;
    }

    public String getFirstBuyTime() {
        return firstBuyTime;
    }

    public void setFirstBuyTime(String firstBuyTime) {
        this.firstBuyTime = firstBuyTime;
    }

    public long getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(long luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public List<Long> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Long> numbers) {
        this.numbers = numbers;
    }

    public long getRemainSecond() {
        return remainSecond;
    }

    public void setRemainSecond(long remainSecond) {
        this.remainSecond = remainSecond;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(List<String> pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public long getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(long stepAmount) {
        this.stepAmount = stepAmount;
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
}
