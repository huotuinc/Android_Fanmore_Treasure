package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by lenovo on 2016/2/26.
 */
public
class NoticeOutputModel extends BaseModel {

    private NoticeInnerModel resultData;

    public NoticeInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(NoticeInnerModel resultData) {
        this.resultData = resultData;
    }

    public class NoticeInnerModel
    {

        private List<NoticeModel> list;

        public
        List< NoticeModel > getList ( ) {

            return list;
        }

        public
        void setList ( List< NoticeModel > list ) {

            this.list = list;
        }
    }
}
