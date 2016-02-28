package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/2/22.
 */
public class CategoryListModel extends BaseModel {
    String iconUrl;
    long pid;
    String title;
    Long type;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
