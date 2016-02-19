package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 订单实体
 */
public class OrderModel extends AccountModel {

    private long pid;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    private String orderTime;
    private String orderTitle;
    private String productDetail;
    private long issue;
    private String orderDetail;
    private List<String> orderImgs;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public long getIssue() {
        return issue;
    }

    public void setIssue(long issue) {
        this.issue = issue;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public List<String> getOrderImgs() {
        return orderImgs;
    }

    public void setOrderImgs(List<String> orderImgs) {
        this.orderImgs = orderImgs;
    }
}
