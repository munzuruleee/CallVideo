package com.ijiuqing.videocall.base;

import android.app.Application;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.model.WorkerThread;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;


public class AGApplication extends Application {

    private WorkerThread mWorkerThread;
    private static IWXAPI api;
    private static AGApplication agApplication = null;
    public static RequestQueue mQueue;

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
