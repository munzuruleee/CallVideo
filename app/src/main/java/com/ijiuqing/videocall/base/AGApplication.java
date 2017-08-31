package com.ijiuqing.videocall.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;
import com.ijiuqing.videocall.work.WorkerThread;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class AGApplication extends Application {
    private static final String TAG = "AGApplication";
    private WorkerThread mWorkerThread;
    private static IWXAPI api;
    private static AGApplication agApplication = null;
    public static RequestQueue mQueue;
    public static CloudPushService pushService;
    public static AGApplication getApplication() {
        return agApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        agApplication = this;
        mQueue = Volley.newRequestQueue(getApplicationContext());
        registToWX();
        initImageLoader();
        initCloudChannel(getApplicationContext());
    }

    private void initImageLoader() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(getApplicationContext());
        builder.memoryCacheExtraOptions(480, 800);
        builder.threadPoolSize(3);
        builder.threadPriority(Thread.NORM_PRIORITY - 1);
        builder.tasksProcessingOrder(QueueProcessingType.FIFO);
        builder.denyCacheImageMultipleSizesInMemory();
        builder.memoryCache(new LruMemoryCache(2 * 1024 * 1024));
        builder.memoryCacheSize(2 * 1024 * 1024);
        builder.memoryCacheSizePercentage(13);
        builder.diskCacheSize(50 * 1024 * 1024);
        builder.diskCacheFileCount(100);
        builder.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
        builder.imageDownloader(new BaseImageDownloader(getApplicationContext()));
        builder.imageDecoder(new BaseImageDecoder(true));
        builder.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
        builder.writeDebugLogs();
        ImageLoaderConfiguration config = builder.build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 初始化云推送通道
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
            }
            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        Log.d(TAG, pushService.getDeviceId());
        pushService.setLogLevel(CloudPushService.LOG_ERROR);
        String account = (String) SharedPreferencesUtils.getParam(getApplicationContext(), ConstantApp.ULID, "");
        if (!TextUtils.isEmpty(account)){
            bindAccount(account);
        }
    }

    public void bindAccount(String account) {
        pushService.bindAccount(account, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, " bindAccount success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, " bindAccount failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    public String getDeviceId() {
        if (pushService!=null) return pushService.getDeviceId();
        else return null;
    }

    private IWXAPI registToWX() {
        api = WXAPIFactory.createWXAPI(this, ConstantApp.APP_ID, false);
        api.registerApp(ConstantApp.APP_ID);
        return api;
    }

    public static IWXAPI getApi() {
        return api;
    }

    public synchronized void initWorkerThread() {
//        if (mWorkerThread == null) {
//            mWorkerThread = new WorkerThread(getApplicationContext());
//            mWorkerThread.start();
//            mWorkerThread.waitForReady();
//        }

    }

    //
    public synchronized WorkerThread getWorkerThread() {
//        return mWorkerThread;
        return null;
    }

    //
    public synchronized void deInitWorkerThread() {
//        mWorkerThread.exit();
//        try {
//            mWorkerThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        mWorkerThread = null;
    }
}
