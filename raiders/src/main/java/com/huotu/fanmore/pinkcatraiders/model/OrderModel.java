package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * 订单实体
 */
public class OrderModel extends AccountModel {

    private long 	pid ;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    private String time;
    private String shareOrderTitle;
    private String pictureUrl;
    private String characters;
    private String issueNo;
    private String nickName;
    private String content;
    private List<String> pictureUrls;
    private String title;
    private long attendAmount;
    private String lotteryTime;
    private String luckNumber;

    public
    long getAttendAmount ( ) {

        return attendAmount;
    }

    public
    void setAttendAmount ( long attendAmount ) {

        this.attendAmount = attendAmount;
    }

    public
    String getLotteryTime ( ) {

        return lotteryTime;
    }

    public
    void setLotteryTime ( String lotteryTime ) {

        this.lotteryTime = lotteryTime;
    }

    public
    String getLuckNumber ( ) {

        return luckNumber;
    }

    public
    void setLuckNumber ( String luckNumber ) {

        this.luckNumber = luckNumber;
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
    String getShareOrderTitle ( ) {

        return shareOrderTitle;
    }

    public
    void setShareOrderTitle ( String shareOrderTitle ) {

        this.shareOrderTitle = shareOrderTitle;
    }

    public
    String getPictureUrl ( ) {

        return pictureUrl;
    }

    public
    void setPictureUrl ( String pictureUrl ) {

        this.pictureUrl = pictureUrl;
    }

    public
    String getCharacters ( ) {

        return characters;
    }

    public
    void setCharacters ( String characters ) {

        this.characters = characters;
    }

    public
    String getIssueNo ( ) {

        return issueNo;
    }

    public
    void setIssueNo ( String issueNo ) {

        this.issueNo = issueNo;
    }

    public
    String getNickName ( ) {

        return nickName;
    }

    public
    void setNickName ( String nickName ) {

        this.nickName = nickName;
    }

    public
    String getContent ( ) {

        return content;
    }

    public
    void setContent ( String content ) {

        this.content = content;
    }

    public
    List< String > getPictureUrls ( ) {

        return pictureUrls;
    }

    public
    void setPictureUrls ( List< String > pictureUrls ) {

        this.pictureUrls = pictureUrls;
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
