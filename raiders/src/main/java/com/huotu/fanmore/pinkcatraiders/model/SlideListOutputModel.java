package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by Administrator on 2016/2/24.
 */
public class SlideListOutputModel extends BaseModel {
    public SlideListInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(SlideListInnerModel resultData) {
        this.resultData = resultData;
    }

    private SlideListInnerModel resultData;
    public class SlideListInnerModel{
        private List<SlideListModel> list;

        public List<SlideListModel> getList() {
            return list;
        }

        public void setList(List<SlideListModel> list) {
            this.list = list;
        }
    }
}
