package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by lenovo on 2016/3/1.
 */
public class RaiderNumberOutputModel extends BaseModel {

    private RaiderNumberInnerModel resultData;

    public RaiderNumberInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(RaiderNumberInnerModel resultData) {
        this.resultData = resultData;
    }

    public class RaiderNumberInnerModel
    {
        private RaiderNumber list;

        public RaiderNumber getList() {
            return list;
        }

        public void setList(RaiderNumber list) {
            this.list = list;
        }
    }
}
