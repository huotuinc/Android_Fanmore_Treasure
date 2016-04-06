package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by lenovo on 2016/3/3.
 */
public class BalanceOutputModel extends BaseModel {

    private BalanceInnerModel resultData;

    public BalanceInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(BalanceInnerModel resultData) {
        this.resultData = resultData;
    }

    public class BalanceInnerModel
    {
        private AppBalanceModel data;

        public AppBalanceModel getData() {
            return data;
        }

        public void setData(AppBalanceModel data) {
            this.data = data;
        }
    }
}
