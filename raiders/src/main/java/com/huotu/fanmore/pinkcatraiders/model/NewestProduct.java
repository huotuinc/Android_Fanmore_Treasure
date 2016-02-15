package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 最新揭晓model
 */
public class NewestProduct extends ProductModel {

    private long remainSecond;
    private long issueId;

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public long getRemainSecond() {
        return remainSecond;
    }

    public void setRemainSecond(long remainSecond) {
        this.remainSecond = remainSecond;
    }
}
