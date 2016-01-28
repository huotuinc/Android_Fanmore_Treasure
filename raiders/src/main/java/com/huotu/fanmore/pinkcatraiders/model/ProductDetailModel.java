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
