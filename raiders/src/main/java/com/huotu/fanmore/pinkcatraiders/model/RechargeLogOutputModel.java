package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 充值记录model
 */
public
class RechargeLogOutputModel extends BaseModel {
    private RechargeLogInnerModel resultData;

    public RechargeLogInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(RechargeLogInnerModel resultData) {
        this.resultData = resultData;
    }

    public class RechargeLogInnerModel
    {

        private List< RechargeModel > list;

        public
        List< RechargeModel > getList ( ) {

            return list;
        }

        public
        void setList ( List< RechargeModel > list ) {

            this.list = list;
        }
    }
}
