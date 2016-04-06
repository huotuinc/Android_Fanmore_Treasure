package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/3/7.
 */
public class CountResultOutputModel extends BaseModel {
    private CountResultInnerModel resultData;

    public CountResultInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(CountResultInnerModel resultData) {
        this.resultData = resultData;
    }
    public class CountResultInnerModel{
        private CountResultModel data;

        public CountResultModel getData() {
            return data;
        }

        public void setData(CountResultModel data) {
            this.data = data;
        }
    }
}
