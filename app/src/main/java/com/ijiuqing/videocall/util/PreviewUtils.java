package com.ijiuqing.videocall.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.nio.ByteBuffer;

import us.pinguo.pgskinprettifyengine.PGSkinPrettifyEngine;
import us.pinguo.prettifyengine.entity.CameraInfo;


/**
 * Created by xieyi on 2017/03/12.
 */

public class PreviewUtils {

    PGSkinUtils mPGSkinUtils;
    GlContextUtils mGlContextUtils;
    ICameraUtils mCameraUtils;

    private int mSurfaceWidth;
    private int mSurfaceHeight;

    public PreviewUtils(Context mContext, Camera.PreviewCallback callback) {
        mCameraUtils = new CameraUtils(callback);
        mGlContextUtils = new GlContextUtils();
        mPGSkinUtils = new PGSkinUtils(mContext);
    }

    public void switchCamera() {
        mCameraUtils.switchCamera();
        setCameraInfo(mSurfaceWidth, mSurfaceHeight);
        mPGSkinUtils.onSwitchCamera(mCameraInfo);
    }

    public void SetColorFilterStrength(int progress) {
        mPGSkinUtils.SetColorFilterStrength(progress);//滤镜强度
    }

    public void SetSkinColor(float fPinking, float fWhitening, float fRedden) {
        mPGSkinUtils.SetSkinColor(fPinking, fWhitening, fRedden);
    }

    public void SetSkinSoftenStrength(int iSoftenStrength) {
        mPGSkinUtils.SetSkinSoftenStrength(iSoftenStrength);
    }

    public void startCamera() {
        mCameraUtils.startCamera();
        setCameraInfo(mCameraUtils.getCameraWidth(), mCameraUtils.getCameraHeight());
    }

    private CameraInfo mCameraInfo;
    public void setCameraInfo(int screenWidth, int screenHeight) {
        mSurfaceWidth  = screenWidth;
        mSurfaceHeight  = screenHeight;

        mCameraInfo = new CameraInfo(
                mCameraUtils.getCameraWidth(),//预览宽
                mCameraUtils.getCameraHeight(),//预览高
                0, mCameraUtils.getCurrentOrientation(),//默认屏幕方向 系统相机方向
                mCameraUtils.getCameraID() == 1);//摄像头

        mPGSkinUtils.setCameraInfo(mCameraInfo);
    }


    public void startCameraPreview(SurfaceHolder holder) {
        mCameraUtils.startCameraPreview(mGlContextUtils.init(holder));
    }

    public void pause() {
        mCameraUtils.stopCamera();
//        mPGSkinUtils.pause();
    }

    public void freeRes() {
        mPGSkinUtils.removeSticker();
        mPGSkinUtils.pause();//释放
        mGlContextUtils.pause();
    }

    public void onresume() {
        startCamera();//启动照相机
        mPGSkinUtils.onresume();//初始化美肤引擎
    }


    public void SetColorFilterByName(String filtertype) {
        mPGSkinUtils.SetColorFilterByName(filtertype);
    }

    public void frameProcess(byte[] data, int textureId, boolean isFirstFrame, boolean bInitEGL) {
        mGlContextUtils.activateOurGLContext();
        mPGSkinUtils.frameProcess(data, textureId, isFirstFrame, bInitEGL);
        mPGSkinUtils.GetOutputToScreen(mSurfaceWidth, mSurfaceHeight);
        /**
         * FAQ
         * 渲染到SurfaceView上部出现白屏
         *
         * mPGSkinUtils.GetOutputToScreen宽高对换
         * */

        mGlContextUtils.presentSurface();

    }


    public void setSkinSoftenAlgorithm(PGSkinPrettifyEngine.PG_SoftenAlgorithm algorithm) {
        mPGSkinUtils.setSkinSoftenAlgorithm(algorithm);
    }

    public int getCameraWidth() {
        return mCameraUtils.getCameraWidth();
    }

    public int getCameraHeight() {
        return mCameraUtils.getCameraHeight();
    }


    public ByteBuffer SkinSoftenGetResult() {
        return mPGSkinUtils.SkinSoftenGetResult();
    }

    public byte[] getSkinSoftenByte() {
        return mPGSkinUtils.getSkinSoftenByte();
    }

    public int getSkinSoftenTextureId() {
        return mPGSkinUtils.getSkinSoftenTextureId();
    }

    public void setSticker(String path){
        mPGSkinUtils.setSticker(path);
    }

    public void removeSticker(){
        mPGSkinUtils.removeSticker();
    }

    public void onScreenOriChanged(int ori){
        mPGSkinUtils.onScreenOriChanged(ori);
    }

    public boolean setInputImageByJpegPath(String pJpegPath, int iScale) {
        return mPGSkinUtils.setInputImageByJpegPath(pJpegPath, iScale);
    }

    public boolean setInputImageByPngPath(String pPngPath) {
        return mPGSkinUtils.setInputImageByPngPath(pPngPath);
    }

    public boolean setInputImageByBitmap(Bitmap pBitmap) {
        return mPGSkinUtils.setInputImageByBitmap(pBitmap);
    }

    public boolean setInputImageByJpegBuffer(byte[] pJpegBuffer, int iScale) {
        return mPGSkinUtils.setInputImageByJpegBuffer(pJpegBuffer, iScale);
    }

    public boolean getOutputToJpegPath(String pJpegPath, int iQuality) {
        return mPGSkinUtils.getOutputToJpegPath(pJpegPath, iQuality);
    }

    public boolean getOutputToPngPath(String pPngPath, boolean bSaveAlphaChannel) {
        return mPGSkinUtils.getOutputToPngPath(pPngPath, bSaveAlphaChannel);
    }

    public boolean getOutputToBitmap(Bitmap pBitmap) {
        return mPGSkinUtils.getOutputToBitmap(pBitmap);
    }
}
