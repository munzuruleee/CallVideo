package com.ijiuqing.videocall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseActivity;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;

public class SplashActivity extends BaseActivity {
    //延时跳转到主页面，splash用来做引导页
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initUIandEvent() {
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
            }
        }, 1000 * 5);
    }

    @Override
    protected void deInitUIandEvent() {

    }
}
