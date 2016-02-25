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
