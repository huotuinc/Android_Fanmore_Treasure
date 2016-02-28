package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 */
public class GoodsListByOtherOutputModel extends BaseModel{
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
        private long sort;

        public long getSort() {
            return sort;
        }

        public void setSort(long sort) {
            this.sort = sort;
        }

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
