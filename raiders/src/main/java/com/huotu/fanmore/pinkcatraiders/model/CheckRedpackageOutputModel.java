package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by lenovo on 2016/3/10.
 */
public class CheckRedpackageOutputModel extends BaseModel {

    private CheckRedpackageInnerModel resultData;

    public CheckRedpackageInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(CheckRedpackageInnerModel resultData) {
        this.resultData = resultData;
    }
    public class CheckRedpackageInnerModel{
        private AppRedPactketsDistributeSourceModel data;
        private long flag;
        private String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public AppRedPactketsDistributeSourceModel getData() {
            return data;
        }

        public void setData(AppRedPactketsDistributeSourceModel data) {
            this.data = data;
        }

        public long getFlag() {
            return flag;
        }

        public void setFlag(long flag) {
            this.flag = flag;
        }
    }
}
