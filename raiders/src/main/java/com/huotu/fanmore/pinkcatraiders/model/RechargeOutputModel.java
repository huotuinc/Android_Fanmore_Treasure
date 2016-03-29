package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 充值金额
 */
public
class RechargeOutputModel extends BaseModel {

    private RechargeInnerModel resultData;

    public RechargeInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(RechargeInnerModel resultData) {
        this.resultData = resultData;
    }

    public class RechargeInnerModel {

        private List< Long > list;

        public
        List< Long > getList ( ) {

            return list;
        }

        public
        void setList ( List< Long > list ) {

            this.list = list;
        }
    }

    }
