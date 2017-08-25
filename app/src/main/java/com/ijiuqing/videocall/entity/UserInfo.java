package com.ijiuqing.videocall.entity;

/**
 * Porject CallVideo
 * Author by SongJiYuan on 2017/8/25.
 */

public class UserInfo {

    /**
     * ulId : ece7563d160
     * ulPlatfromId : oIH9W1XeVZeLh9ALBYg9aDMHZmPE
     * ulApplyId : ovU70wUx-nT59Iupc-Wk0sLLVlmI
     * uiHeadimgurl : http://wx.qlogo.cn/mmopen/XMIv7Cc1M9VibHJClBO6eDyPD668g0n8JGxH0QceyAojO7wGvXY2Oq6HwaOibv2g1tFpKk3kcp2xpsia8YHWpjS5LwEpsN431Sp/0
     * ulNickname : jiuqing
     * ulSex : 1
     * ulProvince :
     * ulCity :
     * ulCountry : AD
     * ulState : 1
     * createTime : 1503569727000
     * modifyTime : 1503644078000
     */

    private String ulId;
    private String ulPlatfromId;
    private String ulApplyId;
    private String uiHeadimgurl;
    private String ulNickname;
    private int ulSex;
    private String ulProvince;
    private String ulCity;
    private String ulCountry;
    private int ulState;
    private long createTime;
    private long modifyTime;

    @Override
    public String toString() {
        return "UserInfo{" +
                "ulId='" + ulId + '\'' +
                ", ulPlatfromId='" + ulPlatfromId + '\'' +
                ", ulApplyId='" + ulApplyId + '\'' +
                ", uiHeadimgurl='" + uiHeadimgurl + '\'' +
                ", ulNickname='" + ulNickname + '\'' +
                ", ulSex=" + ulSex +
                ", ulProvince='" + ulProvince + '\'' +
                ", ulCity='" + ulCity + '\'' +
                ", ulCountry='" + ulCountry + '\'' +
                ", ulState=" + ulState +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
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

    public int getUlState() {
        return ulState;
    }

    public void setUlState(int ulState) {
        this.ulState = ulState;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

}
