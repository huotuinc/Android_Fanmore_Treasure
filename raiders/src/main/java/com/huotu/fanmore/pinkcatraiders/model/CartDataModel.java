package com.huotu.fanmore.pinkcatraiders.model;

import com.orm.SugarRecord;

/**
 * 未登录状态下记录购物车数据
 */
public class CartDataModel extends SugarRecord {

    String cartData;

    public CartDataModel()
    {

    }

    public CartDataModel(String cartData)
    {
        this.cartData = cartData;
    }

    public String getCartData() {
        return cartData;
    }

    public void setCartData(String cartData) {
        this.cartData = cartData;
    }
}
