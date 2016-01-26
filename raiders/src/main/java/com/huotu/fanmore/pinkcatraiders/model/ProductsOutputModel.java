package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 加载产品列表
 */
public class ProductsOutputModel extends BaseModel {

    private ProductsInnerModel resultData;

    public ProductsInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(ProductsInnerModel resultData) {
        this.resultData = resultData;
    }

    public class ProductsInnerModel
    {
        private List<ProductModel> list;

        public List<ProductModel> getList() {
            return list;
        }

        public void setList(List<ProductModel> list) {
            this.list = list;
        }
    }
}
