package com.ijiuqing.videocall.entity;

/**
 * Porject CallVideo
 * Author by SongJiYuan on 2017/8/25.
 */

public class StatusInfo {

    private UserInfo data;
    private String state;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
