package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
public class CountResultModel extends BaseModel {
    String issueNo;
    long 	luckNumber;
    long numberA;
    long numberB;
    List<UserNumberModel> userNumbers;

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public long getLuckNumber() {
        return luckNumber;
    }

    public void setLuckNumber(long luckNumber) {
        this.luckNumber = luckNumber;
    }

    public long getNumberA() {
        return numberA;
    }

    public void setNumberA(long numberA) {
        this.numberA = numberA;
    }

    public long getNumberB() {
        return numberB;
    }

    public void setNumberB(long numberB) {
        this.numberB = numberB;
    }

    public List<UserNumberModel> getUserNumbers() {
        return userNumbers;
    }

    public void setUserNumbers(List<UserNumberModel> userNumbers) {
        this.userNumbers = userNumbers;
    }
}
