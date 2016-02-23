package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 地址model
 */
public
class AddressModel extends BaseModel {

    private long pid;
    private String addressName;

    public
    long getPid ( ) {

        return pid;
    }

    public
    void setPid ( long pid ) {

        this.pid = pid;
    }

    public
    String getAddressName ( ) {

        return addressName;
    }

    public
    void setAddressName ( String addressName ) {

        this.addressName = addressName;
    }
}
