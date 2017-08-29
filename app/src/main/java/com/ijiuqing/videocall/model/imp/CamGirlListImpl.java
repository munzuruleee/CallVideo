package com.ijiuqing.videocall.model.imp;

import android.content.Context;

import com.google.gson.Gson;
import com.ijiuqing.videocall.entity.Bean;
import com.ijiuqing.videocall.entity.StatusListInfo;
import com.ijiuqing.videocall.model.CamGirlListModel;
import com.ijiuqing.videocall.model.OnGetCamGirListListener;

/**
 * Created by songjiyuan on 17/8/29.
 */

public class CamGirlListImpl implements CamGirlListModel {
    @Override
    public void getCamGirList(final Context context, final OnGetCamGirListListener listener) {
        Gson gson = new Gson();
        StatusListInfo sli = gson.fromJson(Bean.list, StatusListInfo.class);
        listener.onSuccess(sli);
    }
}
