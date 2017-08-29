package com.ijiuqing.videocall.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.ijiuqing.videocall.base.AGApplication;
import com.ijiuqing.videocall.model.OnLoginListener;
import com.ijiuqing.videocall.model.UserLoginModel;
import com.ijiuqing.videocall.model.imp.UserLoginImpl;
import com.ijiuqing.videocall.ui.LoginActivity;
import com.ijiuqing.videocall.ui.MainActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler,OnLoginListener {
    private final static Logger log = LoggerFactory.getLogger(WXEntryActivity.class);
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    public String code;
    private View mProgressView;
    private UserLoginModel userLoginModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLoginModel = new UserLoginImpl();
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
                        code = ((SendAuth.Resp) resp).code;
                        log.error("code = " + code);
                        userLoginModel.onWXLogin(getApplicationContext(),code,this);
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        log.error("微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail() {
        Intent intent = new Intent(WXEntryActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

