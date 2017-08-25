package com.ijiuqing.videocall.common;

import io.agora.rtc.Constants;

public class ConstantApp {
    public static final String APP_BUILD_DATE = "today";

    public static final int BASE_VALUE_PERMISSION = 0X0001;
    public static final int PERMISSION_REQ_ID_RECORD_AUDIO = BASE_VALUE_PERMISSION + 1;
    public static final int PERMISSION_REQ_ID_CAMERA = BASE_VALUE_PERMISSION + 2;
    public static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = BASE_VALUE_PERMISSION + 3;
    public static final int MAX_PEER_COUNT = 3;
    public static final String APP_ID = "wx39f0181d8f7b32ff";
    public static int[] VIDEO_PROFILES = new int[]{
            Constants.VIDEO_PROFILE_120P,
            Constants.VIDEO_PROFILE_180P,
            Constants.VIDEO_PROFILE_240P,
            Constants.VIDEO_PROFILE_360P,
            Constants.VIDEO_PROFILE_480P,
            Constants.VIDEO_PROFILE_720P};
    public static final int DEFAULT_PROFILE_IDX = 2; // default use 240P
    public static class PrefManager {
        public static final String PREF_PROPERTY_PROFILE_IDX = "pref_profile_index";
        public static final String PREF_PROPERTY_UID = "pOCXx_uid";
    }
    public static final String ACTION_KEY_CROLE = "C_Role";
    public static final String ACTION_KEY_ROOM_NAME = "ecHANEL";
    public static final String PINKVALUE = "PINKVALUE";
    public static final String WHITENVALUE = "WHITENVALUE";
    public static final String REDDENVALUE = "REDDENVALUE";
    public static final String SOFTENVALUE = "SOFTENVALUE";
    public static final String FILTERVALUE = "FILTERVALUE";
    public static final String CURFILTERSTRENGTH = "CURFILTERSTRENGTH";
    public static final String ULID = "ULID";
    public static final String ULPLATFROMID = "ULPLATFROMID";
    public static final String ULAPPLYID = "ULAPPLYID";
    public static final String UIHEADIMGURL = "UIHEADIMGURL";
    public static final String ULNICKNAME = "ULNICKNAME";
    public static final String ULSEX = "ULSEX";
    public static final String ULPROVINCE = "ULPROVINCE";
    public static final String ULCITY = "ULCITY";
    public static final String ULCOUNTRY = "ULCOUNTRY";
    public static final String ULSTATE = "ULSTATE";
    public static final String LOGINFLAG = "LOGINFLAG";
    public static final String CREATETIME = "CREATETIME";
    public static final String MODIFYTIME = "MODIFYTIME";
}
