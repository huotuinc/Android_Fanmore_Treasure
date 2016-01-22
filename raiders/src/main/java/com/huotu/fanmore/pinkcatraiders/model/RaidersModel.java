package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 夺宝model
 */
public class RaidersModel extends ProductModel {

    //参与期号
    private String partnerNo;
    //本期参与人数
    private String partnerCount;
    //中奖人
    private WinnerModel winner;

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getPartnerCount() {
        return partnerCount;
    }

    public void setPartnerCount(String partnerCount) {
        this.partnerCount = partnerCount;
    }

    public WinnerModel getWinner() {
        return winner;
    }

    public void setWinner(WinnerModel winner) {
        this.winner = winner;
    }
}
