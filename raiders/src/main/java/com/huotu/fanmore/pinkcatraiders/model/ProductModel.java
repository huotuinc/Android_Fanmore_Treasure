package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 产品model
 */
public class ProductModel extends BaseModel {

    private long areaAmount;
    private String pictureUrl;
    private long pid;
    private String title;
    private long toAmount;
    private long remainAmount;
    private long stepAmount;
    private List<String> imgs;

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
