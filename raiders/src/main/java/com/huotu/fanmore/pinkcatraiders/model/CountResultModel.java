package com.huotu.fanmore.pinkcatraiders.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
public class CountResultModel extends BaseModel {
    String issueNo;
    long 	luckNumber;
    String numberA;
    String numberB;
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

    public String getNumberA() {
        return numberA;
    }

    public void setNumberA(String numberA) {
        this.numberA = numberA;
    }

    public String getNumberB() {
        return numberB;
    }

    public void setNumberB(String numberB) {
        this.numberB = numberB;
    }

    public List<UserNumberModel> getUserNumbers() {
        return userNumbers;
    }

    public void setUserNumbers(List<UserNumberModel> userNumbers) {
        this.userNumbers = userNumbers;
    }
}
