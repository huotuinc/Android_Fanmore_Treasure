package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by Administrator on 2016/2/25.
 */
public class NewOpenOutputModel  extends BaseModel {

    private ProductsInnerModel resultData;

    public ProductsInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(ProductsInnerModel resultData) {
        this.resultData = resultData;
    }

    public class ProductsInnerModel
    {
        private List<NewOpenListModel> list;
        private long lastId;

        public long getLastId() {
            return lastId;
        }

        public void setLastId(long lastId) {
            this.lastId = lastId;
        }

        public List<NewOpenListModel> getList() {
            return list;
        }

        public void setList(List<NewOpenListModel> list) {
            this.list = list;
        }
    }
}
