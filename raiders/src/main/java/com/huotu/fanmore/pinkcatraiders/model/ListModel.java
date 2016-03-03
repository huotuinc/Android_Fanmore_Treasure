package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;

/**
 * 清单model
 */
public class ListModel extends ProductModel {

    private long attendAmount;
    private long userBuyAmount;
    private boolean isSelect;
    private long userBuyAmount;
    private BigDecimal pricePercentAmount;
    private long sid;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getUserBuyAmount() {
        return userBuyAmount;
    }

    public void setUserBuyAmount(long userBuyAmount) {
        this.userBuyAmount = userBuyAmount;
    }

    public BigDecimal getPricePercentAmount() {
        return pricePercentAmount;
    }

    public void setPricePercentAmount(BigDecimal pricePercentAmount) {
        this.pricePercentAmount = pricePercentAmount;
    }

    public long getUserBuyAmount() {
        return userBuyAmount;
    }

    public void setUserBuyAmount(long userBuyAmount) {
        this.userBuyAmount = userBuyAmount;
    }

    public
    boolean isSelect ( ) {

        return isSelect;
    }

    public
    void setIsSelect ( boolean isSelect ) {

        this.isSelect = isSelect;
    }

    public
    long getAttendAmount ( ) {


        return attendAmount;
    }

    public
    void setAttendAmount ( long attendAmount ) {

        this.attendAmount = attendAmount;
    }
}
