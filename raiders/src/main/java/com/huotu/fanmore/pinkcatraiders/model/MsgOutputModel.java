package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by lenovo on 2016/3/14.
 */
public class MsgOutputModel extends BaseModel {

    private MsgInnerModel resultData;

    public MsgInnerModel getResultData() {
        return resultData;
    }

    public void setResultData(MsgInnerModel resultData) {
        this.resultData = resultData;
    }

    public class MsgInnerModel
    {
        private List<MsgData> messages;

        public List<MsgData> getMessages() {
            return messages;
        }

        public void setMessages(List<MsgData> messages) {
            this.messages = messages;
        }
    }
}
