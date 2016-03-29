package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 夺宝号码model
 */
public class RaiderNumber extends BaseModel {

    private long amount;
    private String goodsTitle;
    private long issueId;
    private List<Long> numbers;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public List<Long> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Long> numbers) {
        this.numbers = numbers;
    }
}
