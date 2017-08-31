package com.ijiuqing.videocall.model.imp;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ijiuqing.videocall.base.AGApplication;
import com.ijiuqing.videocall.common.AppUrl;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.entity.Bean;
import com.ijiuqing.videocall.entity.StatusInfo;
import com.ijiuqing.videocall.entity.StatusListInfo;
import com.ijiuqing.videocall.entity.UserInfo;
import com.ijiuqing.videocall.model.CamGirlListModel;
import com.ijiuqing.videocall.model.OnGetCamGirListListener;
import com.ijiuqing.videocall.model.OnGetCamGirListener;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by songjiyuan on 17/8/29.
 */

public class CamGirlListImpl implements CamGirlListModel {
    @Override
    public void getCamGirList(final Context context, final OnGetCamGirListListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppUrl.CAMGIRLLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    listener.onFail();
                    return;
                }
                Gson gson = new Gson();
                StatusListInfo rep = gson.fromJson(response, StatusListInfo.class);
                if (!rep.getState().equals("200")) {
                    listener.onFail();
                    return;
                }
                listener.onSuccess(rep);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<String, String>();
                Params.put("num", "100");
                return Params;
            }
        };
        AGApplication.mQueue.add(stringRequest);
    }
//b162b4f76b9
    @Override
    public void getCamGirListForUid(final Context context, final String uid, final OnGetCamGirListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppUrl.searchUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    listener.onFail();
                    return;
                }
                Gson gson = new Gson();
                StatusInfo rep = gson.fromJson(response, StatusInfo.class);
                if (!rep.getState().equals("200")) {
                    listener.onFail();
                    return;
                }
                listener.onSuccess(rep);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<String, String>();
                Params.put("uid", uid);
                return Params;
            }
        };
        AGApplication.mQueue.add(stringRequest);
    }
}
