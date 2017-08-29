package com.ijiuqing.videocall.model;

import com.ijiuqing.videocall.entity.StatusListInfo;

import java.util.List;

/**
 * Created by songjiyuan on 17/8/29.
 */

public interface OnGetCamGirListListener {
    void onSuccess(StatusListInfo list);

    void onFail();
}
