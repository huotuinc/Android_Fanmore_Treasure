package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 支付输出model
 */
public
class PayOutputModel extends BaseModel {

    private PayInnerModel resultData;

    public PayInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(PayInnerModel resultData) {
        this.resultData = resultData;
    }

    public class PayInnerModel
    {

        private  PayModel  data;

        public
        PayModel getData ( ) {

            return data;
        }

        public
        void setData ( PayModel data ) {

            this.data = data;
        }
    }
}
