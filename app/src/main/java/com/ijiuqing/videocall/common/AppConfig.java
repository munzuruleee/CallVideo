package com.ijiuqing.videocall.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by wh on 2017/3/16.
 */

public class AppConfig {

    public static final String SDK_KEY_NEW = "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";

    //滤镜列表 17/05/11
    public static String[] mFilterName = {"深度美白", "清新丽人", "暖暖阳光", "香艳红唇", "艺术黑白",
            "甜美", "温暖", "果冻", "唯美", "淡雅",
            "清新", "Movie", "电影色FM2", "电影色FM7", "Vista(胶片)",
            "Chihiro(日系)", "lzyy(淡雅)", "旧时光OT4(怀旧)", "旧时光OT2(黑调)", "Pinky(少女)", "Zoe(初夏)", "超现实(绘画)" };
    public static String[] mFilterType = {"Deep", "Skinfresh", "Sunshine", "Sexylips", "Skinbw",
            "Sweet", "Lightwarm", "Jelly", "Grace", "Elegant",
            "Fresh", "Movie", "FM2", "FM7", "Vista",
            "Chihiro", "LZYY", "OldTimeFour", "OldTimeTwo", "PinkyFive", "Zoe", "SketchBW" };

    public static final String DEFAULT_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "sdk_demo" + File.separator;

    public static final String STICKER_NAME = "TestSticker";

    public static final String STICKER_LOCAL_PATH = DEFAULT_PATH + "sticker" + File.separator ;

    public static final String STICKER_NOMEDIA_PATH = DEFAULT_PATH + ".nomedia";

    public static final boolean IS_USE_INDEPENDENT_THREAD = true;

}
