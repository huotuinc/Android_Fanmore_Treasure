package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 中奖额外model
 */
public class WinExesptionModel {

    private long deliveryId;
    private MyAddressListModel address;

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public MyAddressListModel getAddress() {
        return address;
    }

    public void setAddress(MyAddressListModel address) {
        this.address = address;
    }
}
