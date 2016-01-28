package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 专区产品列表
 */
public class AreaProductsOutputModel extends BaseModel {
    private AreaProductsInnerModel resultData;

    public AreaProductsInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(AreaProductsInnerModel resultData) {
        this.resultData = resultData;
    }

    public class AreaProductsInnerModel
    {
        private List<ProductModel> list;
        private long count;

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public List<ProductModel> getList() {
            return list;
        }

        public void setList(List<ProductModel> list) {
            this.list = list;
        }
    }
}
