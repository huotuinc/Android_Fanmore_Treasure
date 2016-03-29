package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by Administrator on 2016/2/23.
 */
public class CateGoryOutputModel extends BaseModel {
    private CateGoryInnerModel resultData;

    public CateGoryInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(CateGoryInnerModel resultData) {
        this.resultData = resultData;
    }
    public class CateGoryInnerModel{
        private List<CategoryListModel> list;

        public List<CategoryListModel> getList() {
            return list;
        }

        public void setList(List<CategoryListModel> list) {
            this.list = list;
        }
    }
}
