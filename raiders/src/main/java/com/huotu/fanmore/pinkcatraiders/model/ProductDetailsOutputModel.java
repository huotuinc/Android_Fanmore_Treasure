package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 产品详情http请求接受model
 */
public class ProductDetailsOutputModel extends BaseModel {

    private ProductDetailsInner resultData;

    public ProductDetailsInner getResultData() {
        return resultData;
    }

    public void setResultData(ProductDetailsInner resultData) {
        this.resultData = resultData;
    }

    public class ProductDetailsInner
    {
        private ProductDetailModel data;

        public ProductDetailModel getData() {
            return data;
        }

        public void setData(ProductDetailModel data) {
            this.data = data;
        }
    }
}
