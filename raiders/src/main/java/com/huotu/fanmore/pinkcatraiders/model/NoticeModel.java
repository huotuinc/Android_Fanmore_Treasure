package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 消息通知model
 */
public
class NoticeModel extends BaseModel {

    private String name;
    private String time;
    private String title;

    public
    String getName ( ) {

        return name;
    }

    public
    void setName ( String name ) {

        this.name = name;
    }

    public
    String getTime ( ) {

        return time;
    }

    public
    void setTime ( String time ) {

        this.time = time;
    }

    public
    String getTitle ( ) {

        return title;
    }

    public
    void setTitle ( String title ) {

        this.title = title;
    }
}
