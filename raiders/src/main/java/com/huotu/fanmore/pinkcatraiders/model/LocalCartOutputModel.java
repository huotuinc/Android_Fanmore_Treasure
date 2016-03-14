package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 本地
 */
public class LocalCartOutputModel extends BaseModel {

    private LocalCartInnerModel data;

    public LocalCartInnerModel getResultData() {
        return data;
    }

    public void setResultData(LocalCartInnerModel data) {
        this.data = data;
    }

    public class LocalCartInnerModel {
        public List<ListModel> getLists() {
            return lists;
        }

        private List<ListModel> lists;

        public void setLists(List<ListModel> lists) {
            this.lists = lists;
        }
    }
}
