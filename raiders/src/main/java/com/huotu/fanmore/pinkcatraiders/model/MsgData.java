package com.huotu.fanmore.pinkcatraiders.model;

import java.util.Date;

/**
 * 系统消息model
 */
public class MsgData extends BaseModel {

    private String context;
    private String date ;
    private long messageid ;
    private long messageOrder;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getMessageid() {
        return messageid;
    }

    public void setMessageid(long messageid) {
        this.messageid = messageid;
    }

    public long getMessageOrder() {
        return messageOrder;
    }

    public void setMessageOrder(long messageOrder) {
        this.messageOrder = messageOrder;
    }
}
