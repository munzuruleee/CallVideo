package com.ijiuqing.videocall.ui;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseActivity;
import com.ijiuqing.videocall.common.ConstantApp;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

public class NoticeActivity extends BaseActivity {
    String roomNum = "";
    String name = "";
    SoundPool mSoundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    Ringtone rt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Intent intent = getIntent();
        if (intent != null) {
            roomNum = intent.getStringExtra(ConstantApp.ROOMNUM);
            name = intent.getStringExtra(ConstantApp.LINKNAME);
        }
        Uri uri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
        playRingtone();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopRingtone();
    }

    //反射设置闹铃重复播放
    private void setRingtoneRepeat(Ringtone ringtone) {
        Class<Ringtone> clazz =Ringtone.class;
        try {
            Field field = clazz.getDeclaredField("mLocalPlayer");//返回一个 Field 对象，它反映此 Class 对象所表示的类或接口的指定公共成员字段（※这里要进源码查看属性字段）
            field.setAccessible(true);
            MediaPlayer target = (MediaPlayer) field.get(ringtone);//返回指定对象上此 Field 表示的字段的值
            target.setLooping(true);//设置循环
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    //播放铃声
    public void playRingtone() {
        rt.setStreamType(AudioManager.STREAM_RING);//因为rt.stop()使得MediaPlayer置null,所以要重新创建（具体看源码）
        setRingtoneRepeat(rt);//设置重复提醒
        rt.play();
    }


    //停止铃声
    public void stopRingtone() {
        rt.stop();
    }

}
