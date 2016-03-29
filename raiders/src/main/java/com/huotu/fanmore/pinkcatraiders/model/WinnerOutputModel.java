package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 中奖纪录输出model
 */
public
class WinnerOutputModel extends BaseModel {

    private WinnerInnerModel resultData;

    public WinnerInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(WinnerInnerModel resultData) {
        this.resultData = resultData;
    }

    public class WinnerInnerModel {

        private List< AppUserBuyFlowModel > list;

        public
        List< AppUserBuyFlowModel > getList ( ) {

            return list;
        }

        public
        void setList ( List< AppUserBuyFlowModel > list ) {

            this.list = list;
        }
    }
}
