package com.ijiuqing.videocall.model;

import android.content.Context;

/**
 * Created by songjiyuan on 17/8/26.
 */

public interface UserLoginModel {
    void onWXLogin(Context context, String code, OnLoginListener loginListener);

    void onInitLogin(Context context,String uid,OnLoginListener loginListener);
}
