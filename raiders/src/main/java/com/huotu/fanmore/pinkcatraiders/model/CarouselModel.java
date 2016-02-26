package com.huotu.fanmore.pinkcatraiders.model;

import com.orm.SugarRecord;

/**
 * 轮播图片
 */
public
class CarouselModel extends SugarRecord {

    long goodsId;
    String link;
    String pictureUrl;
    long pid;

    public CarouselModel()
    {

    }

    public CarouselModel(long goodsId, String link, String pictureUrl, long pid)
    {
        this.goodsId = goodsId;
        this.link = link
        ;
        this.pictureUrl = pictureUrl;
        this.pid = pid;
    }

    public
    long getGoodsId ( ) {

        return goodsId;
    }

    public
    void setGoodsId ( long goodsId ) {

        this.goodsId = goodsId;
    }

    public
    String getLink ( ) {

        return link;
    }

    public
    void setLink ( String link ) {

        this.link = link;
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
    long getPid ( ) {

        return pid;
    }

    public
    void setPid ( long pid ) {

        this.pid = pid;
    }
}
