package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by lenovo on 2016/3/24.
 */
public class ScanRedpackageModel extends BaseModel
{
    private ScanRedpackageInnerModel resultData;

    public ScanRedpackageInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(ScanRedpackageInnerModel resultData) {
        this.resultData = resultData;
    }

    public class ScanRedpackageInnerModel
    {
        private List<Double> redpackets;

        public List<Double> getRedpackets() {
            return redpackets;
        }

        public void setRedpackets(List<Double> redpackets) {
            this.redpackets = redpackets;
        }
    }
}
