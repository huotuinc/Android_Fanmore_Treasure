package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 晒单
 */
public
class OrderOutputModel extends BaseModel {
    private OrderInnerModel resultData;

    public OrderInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(OrderInnerModel resultData) {
        this.resultData = resultData;
    }

    public class OrderInnerModel
    {

        private List< OrderModel > list;

        public
        List< OrderModel > getList ( ) {

            return list;
        }

        public
        void setList ( List< OrderModel > list ) {

            this.list = list;
        }
    }
}
