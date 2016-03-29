package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 广播轮播model
 */
public class AdEntity extends BaseModel {

    private int image;

    public AdEntity(int image)
    {
        this.image = image;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
