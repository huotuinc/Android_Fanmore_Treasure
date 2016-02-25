package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by Administrator on 2016/2/24.
 */
public class AdEntityoutput extends BaseModel {
    private AdEntityInnerModel resultData;

    public AdEntityInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(AdEntityInnerModel resultData) {
        this.resultData = resultData;
    }
    public class AdEntityInnerModel{
        private List<AdEntity> list;

        public List<AdEntity> getList() {
            return list;
        }

        public void setList(List<AdEntity> list) {
            this.list = list;
        }
    }
}