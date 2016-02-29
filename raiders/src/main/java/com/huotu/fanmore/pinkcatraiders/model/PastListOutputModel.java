package com.huotu.fanmore.pinkcatraiders.model;

import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
public class PastListOutputModel extends BaseModel {
    private PastListInner resultData;

    public PastListInner getResultData() {
        return resultData;
    }

    public void setResultData(PastListInner resultData) {
        this.resultData = resultData;
    }

    public class PastListInner
    {
        private List<PastListModel> list;
        long lastId;

        public long getLastId() {
            return lastId;
        }

        public void setLastId(long lastId) {
            this.lastId = lastId;
        }

        public List<PastListModel> getList() {
            return list;
        }

        public void setList(List<PastListModel> list) {
            this.list = list;
        }
    }
}
