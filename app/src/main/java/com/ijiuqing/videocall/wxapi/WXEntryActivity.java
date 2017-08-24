package com.ijiuqing.videocall.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ijiuqing.videocall.base.AGApplication;
import com.ijiuqing.videocall.ui.MainActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private final static Logger log = LoggerFactory.getLogger(WXEntryActivity.class);
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    public String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果没回调onResp，八成是这句没有写
        AGApplication.getApi().handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        log.error(resp.errStr);
        log.error("错误码 : " + resp.errCode + "");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) log.error("分享失败");
                else log.error("登录失败");
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        code = ((SendAuth.Resp) resp).code;
                        log.error("code = " + code);
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                        "http://onpay.tpddns.cn:81/userInfo/getUser", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> Params = new HashMap<String, String>();
                                        Params.put("code", code);
                                        return Params;
                                    }
                                };
                                AGApplication.mQueue.add(stringRequest);
                            }
                        }.start();
//                        Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
//                        intent.putExtra("code", code);
//                        startActivity(intent);
                        finish();
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        log.error("微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }
}

