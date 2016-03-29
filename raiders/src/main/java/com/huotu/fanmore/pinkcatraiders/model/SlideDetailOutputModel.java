package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by lenovo on 2016/2/26.
 */
public
class SlideDetailOutputModel extends BaseModel {

    private SlideDetailInnerModel resultData;

    public SlideDetailInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(SlideDetailInnerModel resultData) {
        this.resultData = resultData;
    }

    public class SlideDetailInnerModel
    {
        private String data;

        public
        String getData ( ) {

            return data;
        }

        public
        void setData ( String data ) {

            this.data = data;
        }
    }
}
