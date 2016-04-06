package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/3/7.
 */
public class ShareOutputModel extends BaseModel {
    private ShareInnerModel resultData;

    public ShareInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(ShareInnerModel resultData) {
        this.resultData = resultData;
    }

    public class ShareInnerModel
    {
        private ShareModel share;

        public ShareModel getShare() {
            return share;
        }

        public void setShare(ShareModel share) {
            this.share = share;
        }
    }
}
