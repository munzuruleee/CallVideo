package com.ijiuqing.videocall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseActivity;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.model.OnLoginListener;
import com.ijiuqing.videocall.model.UserLoginModel;
import com.ijiuqing.videocall.model.imp.UserLoginImpl;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;

public class SplashActivity extends BaseActivity implements OnLoginListener{
    //延时跳转到主页面，splash用来做引导页
    Handler handler = new Handler();
    UserLoginModel userLoginModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        userLoginModel = new UserLoginImpl();
    }

    @Override
    protected void initUIandEvent() {
        String uid = (String) SharedPreferencesUtils
                .getParam(getApplicationContext(), ConstantApp.ULID, "");
        userLoginModel.onInitLogin(getApplicationContext(),uid,this);
    }

    @Override
    protected void deInitUIandEvent() {

    }

    @Override
    public void loginSuccess() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean isLogged = (Boolean) SharedPreferencesUtils
                        .getParam(getApplicationContext(), ConstantApp.LOGINFLAG, false);
                Intent intent;
                if (isLogged) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000 * 5);
    }

    @Override
    public void loginFail() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean isLogged = (Boolean) SharedPreferencesUtils
                        .getParam(getApplicationContext(), ConstantApp.LOGINFLAG, false);
                Intent intent;
                if (isLogged) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000 * 5);
    }
}
