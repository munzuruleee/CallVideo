package com.ijiuqing.videocall.base;

import android.app.Application;

import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.model.WorkerThread;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class AGApplication extends Application {

    private WorkerThread mWorkerThread;
    private static IWXAPI api;


    @Override
    public void onCreate() {
        super.onCreate();
        registToWX();
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
