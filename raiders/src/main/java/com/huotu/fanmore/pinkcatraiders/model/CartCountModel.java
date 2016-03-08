package com.huotu.fanmore.pinkcatraiders.model;

import com.orm.SugarRecord;

/**
 * 记录购物车数量
 */
public class CartCountModel extends SugarRecord {

    long count;

    public CartCountModel()
    {

    }

    public CartCountModel(int count)
    {
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
