package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品model
 */
public class ProductModel extends BaseModel {

    private long areaAmount;
    private String pictureUrl;
    private long pid;
    private long issueId;
    private String title;
    private long toAmount;
    private long remainAmount;
    private long stepAmount;
    private List<String> imgs;
    private long sort;
    private long	attendAmount;
    private long	buyAmount;
    private long	defaultAmount;
    private boolean	isSelect;
    private BigDecimal pricePercentAmount;
    private long	sid;
    private long	userBuyAmount;

    public long getSort() {
        return sort;
    }

    public long getAttendAmount() {
        return attendAmount;
    }

    public void setAttendAmount(long attendAmount) {
        this.attendAmount = attendAmount;
    }

    public long getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(long buyAmount) {
        this.buyAmount = buyAmount;
    }

    public long getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(long defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public BigDecimal getPricePercentAmount() {
        return pricePercentAmount;
    }

    public void setPricePercentAmount(BigDecimal pricePercentAmount) {
        this.pricePercentAmount = pricePercentAmount;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public void setUserBuyAmount(long userBuyAmount) {
        this.userBuyAmount = userBuyAmount;
    }

    public long getUserBuyAmount() {
        return userBuyAmount;
    }

    public void setSort(long sort) {
        this.sort = sort;
    }

    public
    long getIssueId ( ) {

        return issueId;
    }

    public
    void setIssueId ( long issueId ) {

        this.issueId = issueId;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public long getAreaAmount() {
        return areaAmount;
    }

    public void setAreaAmount(long areaAmount) {
        this.areaAmount = areaAmount;
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

    public long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public long getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(long stepAmount) {
        this.stepAmount = stepAmount;
    }
}
