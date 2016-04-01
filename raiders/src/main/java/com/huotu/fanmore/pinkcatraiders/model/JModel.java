package com.huotu.fanmore.pinkcatraiders.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/3/31.
 */
public class JModel implements Serializable
{

    /**
     * @field:serialVersionUID:TODO
     * @since
     */

    private static final long serialVersionUID = 8137753696453182798L;

    private String title;

    private String type;

    private String data;

    private String username;

    /**
     * 物品图片路径
     */
    private String goodsUrl;

    /**
     * 物品名字
     */
    private String goodsName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getNeedNumber() {
        return needNumber;
    }

    public void setNeedNumber(Long needNumber) {
        this.needNumber = needNumber;
    }

    public Long getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(Long luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public Long getJoinNumber() {
        return joinNumber;
    }

    public void setJoinNumber(Long joinNumber) {
        this.joinNumber = joinNumber;
    }

    public String getAnnounceTime() {
        return announceTime;
    }

    public void setAnnounceTime(String announceTime) {
        this.announceTime = announceTime;
    }

    /**
     * 期号
     */
    private Long issueId;

    /**
     * 总需人数
     */
    private Long needNumber;

    /**
     * 幸运号码
     */
    private Long luckyNumber;

    /**
     * 本期参与人数
     */
    private Long joinNumber;

    /**
     * 揭晓时间
     */
    private String announceTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

}
