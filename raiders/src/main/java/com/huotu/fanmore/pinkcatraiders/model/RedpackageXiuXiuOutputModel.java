package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 红包xiuxiu model
 */
public class RedpackageXiuXiuOutputModel extends BaseModel {

    private RedpackageXiuXiuInnerModel resultData;

    public RedpackageXiuXiuInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(RedpackageXiuXiuInnerModel resultData) {
        this.resultData = resultData;
    }

    public class RedpackageXiuXiuInnerModel {

        private long flag;
        private AppWinningInfoModel[] data;
        private long sourceId;

        public long getSourceId() {
            return sourceId;
        }

        public void setSourceId(long sourceId) {
            this.sourceId = sourceId;
        }

        public long getFlag() {
            return flag;
        }

        public void setFlag(long flag) {
            this.flag = flag;
        }

        public AppWinningInfoModel[] getData() {
            return data;
        }

        public void setData(AppWinningInfoModel[] data) {
            this.data = data;
        }
    }
}
