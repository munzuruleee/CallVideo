package com.ijiuqing.videocall.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseActivity;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;
import com.ijiuqing.videocall.work.CountDownCallBack;
import com.ijiuqing.videocall.work.CountDownTask;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.videoprp.AgoraYuvEnhancer;

public class VideoChatViewActivity extends BaseActivity implements CountDownCallBack
        , SeekBar.OnSeekBarChangeListener {

    private static final String LOG_TAG = VideoChatViewActivity.class.getSimpleName();
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;
    FrameLayout containerLocal;
    FrameLayout containerRemote;
    SurfaceView suvRemote;
    SurfaceView suvLocal;
    TextView txv;
    RelativeLayout wait;
    private float mWhitenValue = 0f;
    private float mSoftenValue = 0f;
    boolean isSwitchFlag = false;
    private RtcEngine mRtcEngine;// Tutorial Step 1
    private AgoraYuvEnhancer yuvEnhancer = null;
    private String channel;
    private LinearLayout linWhiten, linDermabrasion;
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRemoteVideo(uid);
                    wait.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft();
                }
            });
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserVideoMuted(uid, muted);
                }
            });
        }

        @Override
        public void onFirstLocalVideoFrame(int width, int height, int elapsed) {
            super.onFirstLocalVideoFrame(width, height, elapsed);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video_chat_view);
        yuvEnhancer = new AgoraYuvEnhancer(VideoChatViewActivity.this);
        Intent intent = getIntent();
        if (intent!=null){
            channel = intent.getStringExtra(ConstantApp.CHANNEL);
        }
    }

    @Override
    protected void initUIandEvent() {
        initAgoraEngineAndJoinChannel();
        containerLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchView();
            }
        });
        txv = (TextView) findViewById(R.id.timer);
        wait = (RelativeLayout) findViewById(R.id.wait);
        linDermabrasion = (LinearLayout) findViewById(R.id.lin_dermabrasion);
        linWhiten = (LinearLayout) findViewById(R.id.lin_whiten);
        SeekBar m_Seekwhiten = (SeekBar) findViewById(R.id.seek_whiten);
        m_Seekwhiten.setOnSeekBarChangeListener(this);
        m_Seekwhiten.setProgress((int) (mWhitenValue * 100));
        TextView tv_whiten = (TextView) findViewById(R.id.whiten_value);
        tv_whiten.setText(String.valueOf(mWhitenValue));
        SeekBar m_Seeksoften = (SeekBar) findViewById(R.id.seek_soften);
        m_Seeksoften.setOnSeekBarChangeListener(this);
        m_Seeksoften.setProgress((int) mSoftenValue * 100);
        TextView tv_soften = (TextView) findViewById(R.id.soften_value);
        tv_soften.setText(String.valueOf(mSoftenValue));
    }

    private void switchView() {
        containerLocal.removeAllViews();
        containerRemote.removeAllViews();
        if (suvLocal != null) {
            if (!isSwitchFlag) {
                containerRemote.addView(suvLocal);
            } else {
                containerLocal.addView(suvLocal);
            }
        }
        if (suvRemote != null) {
            if (!isSwitchFlag) {
                containerLocal.addView(suvRemote);
            } else {
                containerRemote.addView(suvRemote);
            }
        }
        isSwitchFlag = !isSwitchFlag;
    }

    @Override
    protected void deInitUIandEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRtcEngine!=null) {
            mRtcEngine.muteLocalVideoStream(false);
            mRtcEngine.muteLocalAudioStream(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRtcEngine!=null) {
            mRtcEngine.muteLocalVideoStream(true);
            mRtcEngine.muteLocalAudioStream(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void initAgoraEngineAndJoinChannel() {
        containerLocal = (FrameLayout) findViewById(R.id.local_video_view_container);
        containerRemote = (FrameLayout) findViewById(R.id.remote_video_view_container);
        initializeAgoraEngine();     // Tutorial Step 1
        setupVideoProfile();         // Tutorial Step 2
        setupLocalVideo();           // Tutorial Step 3
        joinChannel();               // Tutorial Step 4
    }

    // Tutorial Step 10
    public void onLocalVideoMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }
        mRtcEngine.muteLocalVideoStream(iv.isSelected());
        containerLocal = (FrameLayout) findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = (SurfaceView) containerLocal.getChildAt(0);
        surfaceView.setZOrderMediaOverlay(!iv.isSelected());
        surfaceView.setVisibility(iv.isSelected() ? View.GONE : View.VISIBLE);
    }

    // Tutorial Step 9
    public void onLocalAudioMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }
        mRtcEngine.muteLocalAudioStream(iv.isSelected());
    }

    // Tutorial Step 8
    public void onSwitchCameraClicked(View view) {
        mRtcEngine.switchCamera();
    }

    // Tutorial Step 6
    public void onEncCallClicked(View view) {
        release();
        finish();
    }

    public void onWhitenClick(View view) {
        if (linWhiten.getVisibility()== View.GONE){
            linWhiten.setVisibility(View.VISIBLE);
        }else {
            linWhiten.setVisibility(View.GONE);
        }
        if (linDermabrasion.getVisibility() == View.VISIBLE){
            linDermabrasion.setVisibility(View.GONE);
        }
    }

    public void onDermabrasionClick(View view) {
        if (linDermabrasion.getVisibility() == View.GONE){
            linDermabrasion.setVisibility(View.VISIBLE);
        }else {
            linDermabrasion.setVisibility(View.GONE);
        }
        if (linWhiten.getVisibility()== View.VISIBLE){
            linWhiten.setVisibility(View.GONE);
        }
    }

    // Tutorial Step 1
    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.private_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    // Tutorial Step 2
    private void setupVideoProfile() {
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_720P, false);
        mRtcEngine.isTextureEncodeSupported();
    }

    // Tutorial Step 3
    private void setupLocalVideo() {
        suvLocal = RtcEngine.CreateRendererView(getBaseContext());
        suvLocal.setZOrderMediaOverlay(true);
        containerLocal.addView(suvLocal);
        mRtcEngine.setupLocalVideo(new VideoCanvas(suvLocal, VideoCanvas.RENDER_MODE_ADAPTIVE, 0));
        yuvEnhancer.StartPreProcess();
    }

    // Tutorial Step 4
    private void joinChannel() {
        mRtcEngine.joinChannel(null, channel, "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
    }

    // Tutorial Step 5
    private void setupRemoteVideo(int uid) {
        if (containerRemote.getChildCount() >= 1) {
            return;
        }
        suvRemote = RtcEngine.CreateRendererView(getBaseContext());
        containerRemote.addView(suvRemote);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(suvRemote, VideoCanvas.RENDER_MODE_ADAPTIVE, uid));
        suvRemote.setTag(uid); // for mark purpose
        // TODO: 17/8/27
        new CountDownTask(this).execute();
    }

    // Tutorial Step 6
    private void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    // Tutorial Step 7
    private void onRemoteUserLeft() {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        container.removeAllViews();
        finish();
    }

    // Tutorial Step 10
    private void onRemoteUserVideoMuted(int uid, boolean muted) {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);
        Object tag = surfaceView.getTag();
        if (tag != null && (Integer) tag == uid) {
            surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                release();
                finish();
                return true;    //已处理
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void release() {
        if (yuvEnhancer != null) yuvEnhancer.StopPreProcess();
        if (mRtcEngine == null) return;
        leaveChannel();
        mRtcEngine.destroy();
        mRtcEngine = null;
    }

    @Override
    public void onProgressUpdate(int second, boolean isFree) {
        if (isFree) {
            txv.setText("免费时间" + second + "s");
        } else {
            txv.setText("付费时间" + second + "s");
        }
    }

    @Override
    public void onPostExecute() {
        finish();
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {

            case R.id.seek_whiten: {
                if (fromUser) {
                    mWhitenValue = progress / 100f;
                    yuvEnhancer.SetLighteningFactor(mWhitenValue);
                    TextView tv_whiten = (TextView) findViewById(R.id.whiten_value);
                    tv_whiten.setText(String.valueOf(mWhitenValue));
                }
            }
            break;
            case R.id.seek_soften: {
                if (fromUser) {
                    mSoftenValue = progress / 100f;
                    yuvEnhancer.SetSmoothnessFactor(mSoftenValue);
                    TextView tv_soften = (TextView) findViewById(R.id.soften_value);
                    tv_soften.setText(String.valueOf(mSoftenValue));
                }
            }
            break;
        }
        SharedPreferencesUtils.setParam(VideoChatViewActivity.this, ConstantApp.WHITENVALUE, mWhitenValue);
        SharedPreferencesUtils.setParam(VideoChatViewActivity.this, ConstantApp.SOFTENVALUE, mSoftenValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
