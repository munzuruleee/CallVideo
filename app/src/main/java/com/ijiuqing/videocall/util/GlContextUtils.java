package com.ijiuqing.videocall.util;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.SurfaceHolder;

import com.ijiuqing.videocall.common.AppConfig;

import us.pinguo.prettifyengine.PGContextManager;


/**
 * Created by wh on 2017/03/12.
 */

public class GlContextUtils {

    private final String LOG_TAG = "GlContextUtils";

    private PGContextManager mGlContext;
    private SurfaceTexture mCameraTexture;
    private int mCameraTextureID;

    public GlContextUtils() {
        mGlContext = new PGContextManager();
    }

    public SurfaceTexture init(SurfaceHolder holder) {

        mGlContext.initGLContext(0);

        Log.i(LOG_TAG, "creating window surface");

        //如果不需要渲染，仅做数据处理， mGlContext.addSurface(null);
        mGlContext.addSurface(holder);
        mGlContext.activateOurGLContext();

        Log.i(LOG_TAG, "creating camera frame texture");
        mCameraTextureID = mGlContext.createGLExtTexture();
        mCameraTexture = new SurfaceTexture(mCameraTextureID);
        //  m_pCameraTexture.setOnFrameAvailableListener(this);

        Log.i(LOG_TAG, "created camera frame texture with id " + mCameraTextureID);
        return mCameraTexture;
    }

    public void pause() {
        if (mGlContext != null) {
            mGlContext.activateOurGLContext();
        }
        // 销毁 PGHelixEngine
        // 销毁 GL Texture
        if (mCameraTexture != null) {
            Log.i(LOG_TAG, "releasing camera frame texture");
            mGlContext.deleteGLExtTexture(mCameraTextureID);
            mCameraTexture.release();
            mCameraTexture = null;
        }
        // 销毁 GL Surface
        if (mGlContext != null) {
            Log.i(LOG_TAG, "releasing window surface");
            mGlContext.releaseSurface();
        }
        // 销毁 GL 上下文
        if (mGlContext != null) {
            mGlContext.releaseContext();
            mGlContext.finalEnd();
            mGlContext = null;
        }
    }

    public boolean activateOurGLContext(){
        if (mCameraTexture == null || mGlContext == null) return false;
        mGlContext.activateOurGLContext();
        if (AppConfig.IS_USE_INDEPENDENT_THREAD) {
            mGlContext.postTaskOnRenderThread(new Runnable() {
                @Override
                public void run() {
                    mCameraTexture.updateTexImage();
                }
            });
        } else {
            mCameraTexture.updateTexImage();
        }
        return true;
    }

    public void presentSurface(){
        mGlContext.presentSurface();
    }

    public int getTextureId(){
        return mCameraTextureID;
    }

}
