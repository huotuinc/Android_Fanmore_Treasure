package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 夺宝记录http请求返回实体
 */
public class RaidersOutputModel extends BaseModel {

    private RaidersInnerModel resultData;

    public RaidersInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(RaidersInnerModel resultData) {
        this.resultData = resultData;
    }

    public class RaidersInnerModel
    {
        private List<RaidersModel> list;
        private long runNumber;
        private long allNumber;
        private long finishNumber;

        public List<RaidersModel> getList() {
            return list;
        }

        public void setList(List<RaidersModel> list) {
            this.list = list;
        }

        public long getRunNumber() {
            return runNumber;
        }

        public void setRunNumber(long runNumber) {
            this.runNumber = runNumber;
        }

        public long getAllNumber() {
            return allNumber;
        }

        public void setAllNumber(long allNumber) {
            this.allNumber = allNumber;
        }

        public long getFinishNumber() {
            return finishNumber;
        }

        public void setFinishNumber(long finishNumber) {
            this.finishNumber = finishNumber;
        }
    }
}
