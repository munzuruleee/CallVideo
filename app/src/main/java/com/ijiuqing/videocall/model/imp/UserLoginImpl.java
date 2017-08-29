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
import com.ijiuqing.videocall.entity.StatusInfo;
import com.ijiuqing.videocall.entity.UserInfo;
import com.ijiuqing.videocall.model.OnLoginListener;
import com.ijiuqing.videocall.model.UserLoginModel;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by songjiyuan on 17/8/26.
 */

public class UserLoginImpl implements UserLoginModel {
    @Override
    public void onWXLogin(final Context context, final String code, final OnLoginListener loginListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppUrl.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    loginListener.loginFail();
                    return;
                }
                Gson gson = new Gson();
                StatusInfo statusInfo = gson.fromJson(response, StatusInfo.class);
                if (!statusInfo.getState().equals("200")) {
                    loginListener.loginFail();
                    return;
                }
                UserInfo userInfo = statusInfo.getData();
                SharedPreferencesUtils.setParam(context, ConstantApp.ULID, userInfo.getUlId());
                SharedPreferencesUtils.setParam(context, ConstantApp.ULAPPLYID, userInfo.getUlApplyId());
                SharedPreferencesUtils.setParam(context, ConstantApp.ULPLATFROMID, userInfo.getUlPlatfromId());
                SharedPreferencesUtils.setParam(context, ConstantApp.ULCITY, userInfo.getUlCity());
                SharedPreferencesUtils.setParam(context, ConstantApp.ULPROVINCE, userInfo.getUlProvince());
                SharedPreferencesUtils.setParam(context, ConstantApp.ULNICKNAME, userInfo.getUlNickname());
                SharedPreferencesUtils.setParam(context, ConstantApp.ULCOUNTRY, userInfo.getUlCountry());
                SharedPreferencesUtils.setParam(context, ConstantApp.ULSEX, userInfo.getUlSex());
                SharedPreferencesUtils.setParam(context, ConstantApp.UIHEADIMGURL, userInfo.getUiHeadimgurl());
                SharedPreferencesUtils.setParam(context, ConstantApp.LOGINFLAG, true);
                AGApplication.getApplication().bindAccount(userInfo.getUlId());
                loginListener.loginSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginListener.loginFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<String, String>();
                Params.put("code", code);
                Params.put("type", "1");
                Params.put("deviceId", AGApplication.getApplication().getDeviceId());
                return Params;
            }
        };
        AGApplication.mQueue.add(stringRequest);
    }

    @Override
    public void onInitLogin(final Context context, final String uid, final OnLoginListener loginListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppUrl.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    loginListener.loginFail();
                    return;
                }
                Gson gson = new Gson();
                StatusInfo statusInfo = gson.fromJson(response, StatusInfo.class);
                if (!statusInfo.getState().equals("200")) {
                    loginListener.loginFail();
                    return;
                }
                loginListener.loginSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginListener.loginFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<String, String>();
                Params.put("uid", uid);
                Params.put("type", "2");
                Params.put("deviceId", AGApplication.getApplication().getDeviceId());
                return Params;
            }
        };
        AGApplication.mQueue.add(stringRequest);
    }
}
