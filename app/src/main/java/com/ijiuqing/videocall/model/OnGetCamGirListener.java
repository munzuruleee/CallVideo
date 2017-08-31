package com.ijiuqing.videocall.model;

import com.ijiuqing.videocall.entity.StatusInfo;

/**
 * Created by songjiyuan on 17/8/29.
 */

public interface OnGetCamGirListener {
    void onSuccess(StatusInfo list);

    void onFail();
}
