package com.huotu.fanmore.pinkcatraiders.model;

/**
 * Created by Administrator on 2016/2/22.
 */
public class CategoryListModel extends BaseModel {
    String iconUrl;
    Long pid;
    String title;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
