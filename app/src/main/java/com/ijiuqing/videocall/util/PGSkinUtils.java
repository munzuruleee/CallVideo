package com.ijiuqing.videocall.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.common.AppConfig;
import com.ijiuqing.videocall.common.ConstantApp;

import java.nio.ByteBuffer;

import us.pinguo.pgskinprettifyengine.PGSkinPrettifyEngine;
import us.pinguo.prettifyengine.PGPrettifySDK;
import us.pinguo.prettifyengine.entity.CameraInfo;
import us.pinguo.prettifyengine.utils.FileReadUtils;

/**
 * Created by wh on 2017/03/12.
 */

public class PGSkinUtils {
    private final String LOG_TAG = "PGSkinUtils";

    //default value
    private float mPinkValue = 0.6f;
    private float mWhitenValue = 0.5f;
    private float mReddenValue = 0.6f;
    private int mSoftenValue = 70;
    private String mFilterName = "";
    private int mFilterStrength = 100;
    private PGSkinPrettifyEngine.PG_SoftenAlgorithm mAlgorithm;

    private boolean mFilterChange;
    private boolean mSoftenChange;
    private boolean mSkinChange;
    private boolean mFilterStrengthChange;
    private boolean mAlgorithmChange;


    private PGPrettifySDK mPGSkinUtils;
    private CameraInfo mCameraInfo;
    private Context mContext;

    public PGSkinUtils(Context context) {
        mContext = context;
        mPGSkinUtils = new PGPrettifySDK(context, AppConfig.IS_USE_INDEPENDENT_THREAD);
        mPinkValue = (float) SharedPreferencesUtils.getParam(mContext, ConstantApp.PINKVALUE, 0.4f);
        mWhitenValue = (float) SharedPreferencesUtils.getParam(mContext, ConstantApp.WHITENVALUE, 0.4f);
        mReddenValue = (float) SharedPreferencesUtils.getParam(mContext, ConstantApp.REDDENVALUE, 0.4f);
        mSoftenValue = (int) SharedPreferencesUtils.getParam(mContext, ConstantApp.SOFTENVALUE, 70);
        mFilterStrength = (int) SharedPreferencesUtils.getParam(mContext, ConstantApp.FILTERVALUE, 100);
        mFilterName = (String) SharedPreferencesUtils.getParam(mContext, ConstantApp.CURFILTERSTRENGTH, "Deep");
    }

    public void setCameraInfo(CameraInfo cameraInfo) {
        mPGSkinUtils.setCameraInfo(cameraInfo);
        mCameraInfo = cameraInfo;
    }

    public void onSwitchCamera(CameraInfo cameraInfo) {
        mPGSkinUtils.setCameraInfo(cameraInfo);
        mCameraInfo = cameraInfo;

        mPGSkinUtils.onCameraOriChanged(cameraInfo.cameraOri, cameraInfo.isFront);//摄像头

        if (cameraInfo.isFront) {
            mPGSkinUtils.SetOrientForAdjustInput(PGSkinPrettifyEngine.PG_Orientation.PG_OrientationRightRotate90);
        } else {
            mPGSkinUtils.SetOrientForAdjustInput(PGSkinPrettifyEngine.PG_Orientation.PG_OrientationRightRotate270Mirrored);
        }
        mPGSkinUtils.SetSizeForAdjustInput(cameraInfo.previewHeight, cameraInfo.previewWidth);
    }


    public void SetColorFilterStrength(int progress) {
        mFilterStrengthChange = true;
        mFilterStrength = progress;
    }


    public void SetSkinColor(float fPinking, float fWhitening, float fRedden) {
        mSkinChange = true;
        mPinkValue = fPinking;
        mWhitenValue = fWhitening;
        mReddenValue = fRedden;
    }

    public void SetSkinSoftenStrength(int iSoftenStrength) {
        mSoftenChange = true;
        mSoftenValue = iSoftenStrength;
    }

    public void SetColorFilterByName(String pName) {
        mFilterChange = true;
        mFilterName = pName;
    }

    public void setSkinSoftenAlgorithm(PGSkinPrettifyEngine.PG_SoftenAlgorithm algorithm) {
        mAlgorithmChange = true;
        mAlgorithm = algorithm;
    }


    public void setSticker(String path) {//设置贴纸
        mPGSkinUtils.setSticker(path);
    }


    public void GetOutputToScreen(int width, int height) {
        mPGSkinUtils.GetOutputToScreen(width, height);
    }

    public void onScreenOriChanged(int ori) {
        if (mCameraInfo != null) {
            mPGSkinUtils.onSreenOriChanged(ori, mCameraInfo.isFront);
        }
    }

    public void removeSticker() {//删除贴纸
        mPGSkinUtils.removeSticker();
    }

    /**
     * step 1
     * 初始化
     */
    private void initEngine(boolean isInitEGL) {
        /**
         * FAQ
         * 初始化引擎时卡住或闪退
         *
         * 1.检查key是否正确
         * 2.检查OpenGL是否初始化正确（bInitEGL传false表示外部已初始化OpenGL，内部不再初始化OpenGL，传true表示交由内部初始化OpenGL）
         * */

        mPGSkinUtils.InitialiseEngine(AppConfig.SDK_KEY_NEW, isInitEGL, FileReadUtils.getFileContent(mContext, R.raw.megvii_facepp_model)); // 带贴纸SDK初始化引擎
//        mPGSkinUtils.InitialiseEngine(AppConfig.SDK_KEY_NEW, isInitEGL); // 不带贴纸SDK初始化引擎
        mPGSkinUtils.SetSizeForAdjustInput(mCameraInfo.previewHeight, mCameraInfo.previewWidth);// 调整输入帧的宽高。Android默认摄像头采集数据是横向的，因此宽高对换
//        mPGSkinUtils.SetSizeForAdjustInput(mCameraInfo.previewWidth, mCameraInfo.previewHeight);
        /**
         * FAQ
         * 图片从上到下多层重叠
         *
         * 初始化SetSizeForAdjustInput宽高对换
         * */
        mPGSkinUtils.SetOrientForAdjustInput(PGSkinPrettifyEngine.PG_Orientation.PG_OrientationRightRotate90);
        mPGSkinUtils.SetOutputFormat(PGSkinPrettifyEngine.PG_PixelFormat.PG_Pixel_NV21);//设置美肤结果的输出格式
        mPGSkinUtils.SetSkinColor(mPinkValue, mWhitenValue, mReddenValue);//设置美颜参数
        /**
         * FAQ
         * Android美颜有没有推荐的参数
         *
         * 大部分机型上推荐的参数是：
         * 1.粉嫩0.4，红润0.7，白皙0.5，美肤70
         * 2.粉嫩0.6，红润0.5，白皙0.6，美肤70
         * 这两组参数附近都是较好的效果，美是一个很主观的问题，可根据实际自行调整
         * */
        mPGSkinUtils.SetSkinSoftenStrength(mSoftenValue);

        mPGSkinUtils.SetSkinSoftenAlgorithm(PGSkinPrettifyEngine.PG_SoftenAlgorithm.PG_SoftenAlgorithmContrast);//磨皮算法设置
        /**
         * FAQ
         * 输出图片纯粉红色
         *
         * 设置输出方向SetOutputOrientation
         * */
    }

    /**
     * step 2
     * 美颜 run
     * param  date 数据流， textureid 外部纹理id，
     */
    public void frameProcess(byte[] data, int textureId, boolean isFirstFrame, boolean isInitEGL) {
        /**
         * FAQ
         * 屏幕花屏
         *
         * 1.SetSizeForAdjustInput(宽,高)的宽高对调
         * 2.SetInputFrameByXXX(预览帧，宽,高)的宽高对调
         * */

        if (isFirstFrame) initEngine(isInitEGL);//  在第一帧视频到来时，初始化，指定需要的输出大小以及方向
        /**
         * FAQ
         * 调用崩溃E/PGHelix::SetMatrixForAdjustInput: Must Set Image First.
         *
         * 有可能是key不对或者在runEngine之前没有没有设置图片setInputFrameByXXX
         * */

        if (mFilterChange) {
            mPGSkinUtils.SetColorFilterByName(mFilterName);//滤镜
            mFilterChange = false;
        }
        if (mFilterStrengthChange) {
            mPGSkinUtils.SetColorFilterStrength(mFilterStrength);//滤镜强度
            mFilterStrengthChange = false;
        }
        if (mSoftenChange) {
            mPGSkinUtils.SetSkinSoftenStrength(mSoftenValue);//磨皮强度
            mSoftenChange = false;
        }
        if (mSkinChange) {
            mPGSkinUtils.SetSkinColor(mPinkValue, mWhitenValue, mReddenValue);//美颜参数
            mSkinChange = false;
        }
        if (mAlgorithmChange) {
            mPGSkinUtils.SetSkinSoftenAlgorithm(mAlgorithm);//磨皮算法设置
            mAlgorithmChange = false;
        }

        if (data != null) {
            mPGSkinUtils.SetInputFrameByNV21(data, mCameraInfo.previewWidth, mCameraInfo.previewHeight);
        } else if (textureId > 0) {
            mPGSkinUtils.SetInputFrameByTexture(textureId, mCameraInfo.previewWidth, mCameraInfo.previewHeight);
            // mPGSkinUtils.SetInputFrameByTexture(textureId, mCameraInfo.previewWidth, mCameraInfo.previewHeight, 1);//普通纹理id
            /**
             * FAQ
             * 推流SDK接美颜黑屏
             *
             * 使用的纹理类型不同，SetInputFrameByTexture设置纹理类型
             * */
        }

        mPGSkinUtils.renderSticker(data);//人脸识别 渲染贴纸（贴纸功能相关）

        mPGSkinUtils.RunEngine();

        /**
         * FAQ
         * 接入美颜SDK无美颜效果
         *
         * 1.接入方式是否正确（是否正确传入数据，是否有取出处理结果数据或纹理ID）
         * 2.检查手机系统时间是否是当前
         * 3.检查key是否正确
         * 4.引擎的初始化和数据处理是否是在同一个线程
         * */

        /**
         * FAQ
         * 图片显示黑白紫绿
         *
         * 输入输出图像格式不一致
         * 1.检查系统提供的数据格式
         * 2.检查SetOutputFormat
         * 3.检查SetInputFrameByXXX
         * */
    }

    /**
     * FAQ
     * 图像经过美颜SDK处理后出现其他异常
     * 先通过获取RunEngine前后图像对比确认是在哪一步出现异常
     * 通过纹理ID或视频帧保存成图片的代码可参考官网FAQ
     * */

    /**
     * step 3
     * 获取美颜后数据 1.buffer 2.byte 3.textureID
     */
    public ByteBuffer SkinSoftenGetResult() {
        return mPGSkinUtils.SkinSoftenGetResult();
    }

    public byte[] getSkinSoftenByte() {
        ByteBuffer buffer = mPGSkinUtils.SkinSoftenGetResult();
        buffer.clear();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes, 0, bytes.length);
        return bytes;
    }

    public byte[] changeByteBuffer2Bytes(ByteBuffer buffer) {
        buffer.clear();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes, 0, bytes.length);
        return bytes;
    }

    public int getSkinSoftenTextureId() {
        return mPGSkinUtils.GetOutputTextureID();
    }


    /**
     * 释放引擎
     */

    public void pause() {
        mPGSkinUtils.release();
    }

    /**
     * 恢复引擎
     */
    public void onresume() {
        mPGSkinUtils.prePare();
        mFilterChange = true;
        mFilterStrengthChange = true;
    }

    public boolean setInputImageByJpegPath(String pJpegPath, int iScale) {
        return mPGSkinUtils.SetInputImageByJpegPath(pJpegPath, iScale);
    }

    public boolean setInputImageByPngPath(String pPngPath) {
        return mPGSkinUtils.SetInputImageByPngPath(pPngPath);
    }

    public boolean setInputImageByBitmap(Bitmap pBitmap) {
        return mPGSkinUtils.SetInputImageByBitmap(pBitmap);
    }

    public boolean setInputImageByJpegBuffer(byte[] pJpegBuffer, int iScale) {
        return mPGSkinUtils.SetInputImageByJpegBuffer(pJpegBuffer, iScale);
    }

    public boolean getOutputToJpegPath(String pJpegPath, int iQuality) {
        return mPGSkinUtils.GetOutputToJpegPath(pJpegPath, iQuality);
    }

    public boolean getOutputToPngPath(String pPngPath, boolean bSaveAlphaChannel) {
        return mPGSkinUtils.GetOutputToPngPath(pPngPath, bSaveAlphaChannel);
    }

    public boolean getOutputToBitmap(Bitmap pBitmap) {
        return mPGSkinUtils.GetOutputToBitmap(pBitmap);
    }

}
