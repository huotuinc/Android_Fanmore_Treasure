package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/2/24.
 */
public class SlideListModel extends BaseModel {
    Long goodsId;
    String link;
    String pictureUrl;
    Long pid;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
