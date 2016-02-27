package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 清单model
 */
public class ListModel extends ProductModel {

    private long attendAmount;
    private long buyAmount;
    private boolean isSelect;

    public
    long getBuyAmount ( ) {

        return buyAmount;
    }

    public
    void setBuyAmount ( long buyAmount ) {

        this.buyAmount = buyAmount;
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
