package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by lenovo on 2016/2/27.
 */
public
class ListOutputModel extends BaseModel {

    private ListInnerModel resultData;

    public ListInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(ListInnerModel resultData) {
        this.resultData = resultData;
    }

    public class ListInnerModel
    {

        private List< ListModel > list;

        public
        List< ListModel > getList ( ) {

            return list;
        }

        public
        void setList ( List< ListModel > list ) {

            this.list = list;
        }
    }
}
