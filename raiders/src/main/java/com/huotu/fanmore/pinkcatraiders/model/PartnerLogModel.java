package com.huotu.fanmore.pinkcatraiders.model;

/**
 * 参与历史model
 */
public class PartnerLogModel extends BaseModel {

    //分组标记
    private String groupTime;
    //参与头像
    private String partnerLogo;
    //参与名称
    private String partnerName;
    //参与IP
    private String partnerIp;
    //参与次数
    private String partnerCount;
    //参与时间
    private String partnerTime;

    public String getGroupTime() {
        return groupTime;
    }

    public void setGroupTime(String groupTime) {
        this.groupTime = groupTime;
    }

    public String getPartnerLogo() {
        return partnerLogo;
    }

    public void setPartnerLogo(String partnerLogo) {
        this.partnerLogo = partnerLogo;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerIp() {
        return partnerIp;
    }

    public void setPartnerIp(String partnerIp) {
        this.partnerIp = partnerIp;
    }

    public String getPartnerCount() {
        return partnerCount;
    }

    public void setPartnerCount(String partnerCount) {
        this.partnerCount = partnerCount;
    }

    public String getPartnerTime() {
        return partnerTime;
    }

    public void setPartnerTime(String partnerTime) {
        this.partnerTime = partnerTime;
    }
}
