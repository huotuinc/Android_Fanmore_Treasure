package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 参与历史记录获取model
 */
public class PartnerHistorysOutputModel extends BaseModel {

    private PartnerHistorysInner resultData;

    public PartnerHistorysInner getResultData() {
        return resultData;
    }

    public void setResultData(PartnerHistorysInner resultData) {
        this.resultData = resultData;
    }

    public class PartnerHistorysInner
    {
        private List<PartnerHistorysModel> list;

        public List<PartnerHistorysModel> getList() {
            return list;
        }

        public void setList(List<PartnerHistorysModel> list) {
            this.list = list;
        }
    }
}
