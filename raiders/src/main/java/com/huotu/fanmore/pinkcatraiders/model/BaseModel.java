package com.huotu.fanmore.pinkcatraiders.model;

import java.io.Serializable;

/**
 * BaseModel
 */
public class BaseModel implements Serializable {

    private int resultCode;
    private int systemResultCode;
    private String resultDescription;
    private String systemResultDescription;


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getSystemResultCode() {
        return systemResultCode;
    }

    public void setSystemResultCode(int systemResultCode) {
        this.systemResultCode = systemResultCode;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getSystemResultDescription() {
        return systemResultDescription;
    }

    public void setSystemResultDescription(String systemResultDescription) {
        this.systemResultDescription = systemResultDescription;
    }
}