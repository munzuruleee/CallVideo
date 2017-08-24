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
