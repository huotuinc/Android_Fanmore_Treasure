package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 清单model
 */
public class ListModel extends ProductModel {

    private long attendAmount;
    private long userBuyAmount;
    private boolean isSelect;

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
