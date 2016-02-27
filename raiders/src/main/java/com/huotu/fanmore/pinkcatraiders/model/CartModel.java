package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 购物车选择model
 */
public
class CartModel extends BaseModel {

    private ProductModel product;
    private int type;

    public
    int getType ( ) {

        return type;
    }

    public
    void setType ( int type ) {

        this.type = type;
    }

    public
    ProductModel getProduct ( ) {

        return product;
    }

    public
    void setProduct ( ProductModel product ) {

        this.product = product;
    }
}
