package com.huotu.fanmore.pinkcatraiders.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * 搜索历史model
 */
public class SearchHistoryModel extends SugarRecord {
    private String searchKey;
    private String time;

    public SearchHistoryModel()
    {

    }

    public SearchHistoryModel(String searchKey, String time)
    {
        this.searchKey = searchKey;
        this.time = time;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
