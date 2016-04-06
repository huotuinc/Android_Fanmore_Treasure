package com.huotu.fanmore.pinkcatraiders.model;

import java.math.BigDecimal;

/**
 * 支付订单信息model
 */
public
class PayModel extends BaseModel {

    private String alipayCallbackUrl;
    private String alipayFee;
    private String wxFee;

    public String getAlipayFee() {
        return alipayFee;
    }

    public void setAlipayFee(String alipayFee) {
        this.alipayFee = alipayFee;
    }

    public String getWxFee() {
        return wxFee;
    }

    public void setWxFee(String wxFee) {
        this.wxFee = wxFee;
    }

    private String orderNo;
    private String remainPayUrl;
    private String wxCallbackUrl;
    private String attach;
    private String detail;
    private int productType;
    private long productId;
    private String tag;

    public
    int getProductType ( ) {

        return productType;
    }

    public
    void setProductType ( int productType ) {

        this.productType = productType;
    }

    public
    long getProductId ( ) {

        return productId;
    }

    public
    void setProductId ( long productId ) {

        this.productId = productId;
    }

    public
    String getTag ( ) {

        return tag;
    }

    public
    void setTag ( String tag ) {

        this.tag = tag;
    }

    public
    String getDetail ( ) {

        return detail;
    }

    public
    void setDetail ( String detail ) {

        this.detail = detail;
    }

    public
    String getAttach ( ) {

        return attach;
    }

    public
    void setAttach ( String attach ) {

        this.attach = attach;
    }

    public
    String getAlipayCallbackUrl ( ) {

        return alipayCallbackUrl;
    }

    public
    void setAlipayCallbackUrl ( String alipayCallbackUrl ) {

        this.alipayCallbackUrl = alipayCallbackUrl;
    }

    public
    String getOrderNo ( ) {

        return orderNo;
    }

    public
    void setOrderNo ( String orderNo ) {

        this.orderNo = orderNo;
    }

    public
    String getRemainPayUrl ( ) {

        return remainPayUrl;
    }

    public
    void setRemainPayUrl ( String remainPayUrl ) {

        this.remainPayUrl = remainPayUrl;
    }

    public
    String getWxCallbackUrl ( ) {

        return wxCallbackUrl;
    }

    public
    void setWxCallbackUrl ( String wxCallbackUrl ) {

        this.wxCallbackUrl = wxCallbackUrl;
    }
}
