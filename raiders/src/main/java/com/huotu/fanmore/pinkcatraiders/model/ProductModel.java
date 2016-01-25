package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 产品model
 */
public class ProductModel extends BaseModel {

    private int productTag;
    private String productIcon;
    private String productName;
    private double lotterySchedule;
    private long total;
    private long surplus;

    public long getSurplus() {
        return surplus;
    }

    public void setSurplus(long surplus) {
        this.surplus = surplus;
    }

    public long getTotal() {
        return total;

    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getProductTag() {
        return productTag;
    }

    public void setProductTag(int productTag) {
        this.productTag = productTag;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getLotterySchedule() {
        return lotterySchedule;
    }

    public void setLotterySchedule(double lotterySchedule) {
        this.lotterySchedule = lotterySchedule;
    }
}
