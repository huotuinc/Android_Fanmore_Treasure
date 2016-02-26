package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by lenovo on 2016/2/25.
 */
public
class OrderDetailOutputModel extends BaseModel {

    private OrderInnerModel resultData;

    public OrderInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(OrderInnerModel resultData) {
        this.resultData = resultData;
    }

    public class OrderInnerModel
    {

        private OrderModel data;

        public
        OrderModel getData ( ) {

            return data;
        }

        public
        void setData ( OrderModel data ) {

            this.data = data;
        }
    }
}
