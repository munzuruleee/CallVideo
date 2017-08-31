package com.ijiuqing.videocall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseActivity;
import com.ijiuqing.videocall.common.ConstantApp;

public class NoticeActivity extends BaseActivity {
    String roomNum = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Intent intent = getIntent();
        if (intent != null) {
            roomNum = intent.getStringExtra(ConstantApp.ROOMNUM);
            name = intent.getStringExtra(ConstantApp.LINKNAME);
        }
    }

    @Override
    protected void initUIandEvent() {
        Button button = (Button) findViewById(R.id.link);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeActivity.this, VideoChatViewActivity.class);
                intent.putExtra(ConstantApp.CHANNEL, roomNum);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void deInitUIandEvent() {

    }
}
