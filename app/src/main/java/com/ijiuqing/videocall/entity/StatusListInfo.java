package com.ijiuqing.videocall.entity;

import java.util.List;

/**
 * Porject CallVideo
 * Author by SongJiYuan on 2017/8/25.
 */

public class StatusListInfo {

    public List<UserInfo> getData() {
        return data;
    }

    public void setData(List<UserInfo> data) {
        this.data = data;
    }

    private List<UserInfo> data;
    private String state;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
