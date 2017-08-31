package com.ijiuqing.videocall.work;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.entity.PushMsg;
import com.ijiuqing.videocall.ui.NoticeActivity;
import com.ijiuqing.videocall.ui.SplashActivity;

import java.util.Map;

/**
 * Created by songjiyuan on 17/8/29.
 */

public class MsgReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";

    /**
     * 推送通知的回调方法
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        if ( null != extraMap ) {
            for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                Log.i(REC_TAG,"@Get diy param : Key=" + entry.getKey() + " , Value=" + entry.getValue());
            }
        } else {
            Log.i(REC_TAG,"@收到通知 && 自定义消息为空");
        }
        Log.i(REC_TAG,"收到一条推送通知 ： " + title );
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.i(REC_TAG,"onNotificationReceivedInApp ： " + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
    }

    /**
     * 推送消息的回调方法    *
     * @param context
     * @param cPushMessage
     */
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        if (!cPushMessage.getTitle().equals("LinkMai")){
            return;
        }
        try {
            Log.i(REC_TAG,"收到一条推送消息 ： " + cPushMessage.getTitle());
            Gson gson = new Gson();
            PushMsg pm = gson.fromJson(cPushMessage.getContent(), PushMsg.class);
            Intent intent = new Intent(context, NoticeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ConstantApp.ROOMNUM, pm.getRoomId());
            intent.putExtra(ConstantApp.LINKNAME, pm.getUserName());
            context.startActivity(intent);
        } catch (Exception e) {
            Log.i(REC_TAG, e.toString());
        }
    }

    /**
     * 从通知栏打开通知的扩展处理
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        CloudPushService cloudPushService = PushServiceFactory.getCloudPushService();
//        cloudPushService.setNotificationSoundFilePath();
        Log.i(REC_TAG,"onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
    }


    @Override
    public void onNotificationRemoved(Context context, String messageId) {
        Log.i(REC_TAG, "onNotificationRemoved ： " + messageId);
    }


    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.i(REC_TAG,"onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
    }
}
