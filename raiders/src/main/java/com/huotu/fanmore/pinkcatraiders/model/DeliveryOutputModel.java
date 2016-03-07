package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
public class DeliveryOutputModel extends BaseModel {

    private DeliveryInnerModel resultData;

    public DeliveryInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(DeliveryInnerModel resultData) {
        this.resultData = resultData;
    }
    public class DeliveryInnerModel{
        private AppDeliveryModel data;

        public AppDeliveryModel getData() {
            return data;
        }

        public void setData(AppDeliveryModel data) {
            this.data = data;
        }
    }
}
