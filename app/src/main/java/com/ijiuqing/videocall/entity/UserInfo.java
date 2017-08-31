package com.ijiuqing.videocall.entity;

/**
 * Porject CallVideo
 * Author by SongJiYuan on 2017/8/25.
 */

public class UserInfo {


    private String ulId;
    private String ulPlatfromId;
    private String ulApplyId;
    private Object uiUsername;
    private Object uiPassword;
    private String uiHeadimgurl;
    private String ulNickname;
    private String ulMoney = "100";
    private int ulSex;
    private String ulProvince;
    private String ulCity;
    private String ulCountry;
    private String ulPushDevice;
    private String ulPushAccount;
    private String ulPushAlias;
    private String ulPushTag;
    private String createTime;
    private String modifyTime;

    public String getUlMoney() {
        return ulMoney;
    }

    public void setUlMoney(String ulMoney) {
        this.ulMoney = ulMoney;
    }

    public String getUlId() {
        return ulId;
    }

    public void setUlId(String ulId) {
        this.ulId = ulId;
    }

    public String getUlPlatfromId() {
        return ulPlatfromId;
    }

    public void setUlPlatfromId(String ulPlatfromId) {
        this.ulPlatfromId = ulPlatfromId;
    }

    public String getUlApplyId() {
        return ulApplyId;
    }

    public void setUlApplyId(String ulApplyId) {
        this.ulApplyId = ulApplyId;
    }

    public Object getUiUsername() {
        return uiUsername;
    }

    public void setUiUsername(Object uiUsername) {
        this.uiUsername = uiUsername;
    }

    public Object getUiPassword() {
        return uiPassword;
    }

    public void setUiPassword(Object uiPassword) {
        this.uiPassword = uiPassword;
    }

    public String getUiHeadimgurl() {
        return uiHeadimgurl;
    }

    public void setUiHeadimgurl(String uiHeadimgurl) {
        this.uiHeadimgurl = uiHeadimgurl;
    }

    public String getUlNickname() {
        return ulNickname;
    }

    public void setUlNickname(String ulNickname) {
        this.ulNickname = ulNickname;
    }

    public int getUlSex() {
        return ulSex;
    }

    public void setUlSex(int ulSex) {
        this.ulSex = ulSex;
    }

    public String getUlProvince() {
        return ulProvince;
    }

    public void setUlProvince(String ulProvince) {
        this.ulProvince = ulProvince;
    }

    public String getUlCity() {
        return ulCity;
    }

    public void setUlCity(String ulCity) {
        this.ulCity = ulCity;
    }

    public String getUlCountry() {
        return ulCountry;
    }

    public void setUlCountry(String ulCountry) {
        this.ulCountry = ulCountry;
    }

    public String getUlPushDevice() {
        return ulPushDevice;
    }

    public void setUlPushDevice(String ulPushDevice) {
        this.ulPushDevice = ulPushDevice;
    }

    public String getUlPushAccount() {
        return ulPushAccount;
    }

    public void setUlPushAccount(String ulPushAccount) {
        this.ulPushAccount = ulPushAccount;
    }

    public String getUlPushAlias() {
        return ulPushAlias;
    }

    public void setUlPushAlias(String ulPushAlias) {
        this.ulPushAlias = ulPushAlias;
    }

    public String getUlPushTag() {
        return ulPushTag;
    }

    public void setUlPushTag(String ulPushTag) {
        this.ulPushTag = ulPushTag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "ulId='" + ulId + '\'' +
                ", ulPlatfromId='" + ulPlatfromId + '\'' +
                ", ulApplyId='" + ulApplyId + '\'' +
                ", uiUsername=" + uiUsername +
                ", uiPassword=" + uiPassword +
                ", uiHeadimgurl='" + uiHeadimgurl + '\'' +
                ", ulNickname='" + ulNickname + '\'' +
                ", ulMoney='" + ulMoney + '\'' +
                ", ulSex=" + ulSex +
                ", ulProvince='" + ulProvince + '\'' +
                ", ulCity='" + ulCity + '\'' +
                ", ulCountry='" + ulCountry + '\'' +
                ", ulPushDevice='" + ulPushDevice + '\'' +
                ", ulPushAccount='" + ulPushAccount + '\'' +
                ", ulPushAlias='" + ulPushAlias + '\'' +
                ", ulPushTag='" + ulPushTag + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
