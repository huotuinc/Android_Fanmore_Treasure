package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 红包获取接口model
 */
public
class RedPacketOutputModel extends BaseModel {

    private RedPacketInnerModel resultData;

    public RedPacketInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(RedPacketInnerModel resultData) {
        this.resultData = resultData;
    }

    public class RedPacketInnerModel {

        private List< RedPacketsModel > list;
        private int usedOrExpire;
        private int unused;

        public int getUsedOrExpire() {
            return usedOrExpire;
        }

        public void setUsedOrExpire(int usedOrExpire) {
            this.usedOrExpire = usedOrExpire;
        }

        public int getUnused() {
            return unused;
        }

        public void setUnused(int unused) {
            this.unused = unused;
        }

        public
        List< RedPacketsModel > getList ( ) {

            return list;
        }

        public
        void setList ( List< RedPacketsModel > list ) {

            this.list = list;
        }
    }
}
