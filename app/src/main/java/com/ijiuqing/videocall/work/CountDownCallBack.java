package com.ijiuqing.videocall.work;

/**
 * Created by songjiyuan on 17/8/27.
 */

public interface CountDownCallBack {
    void onProgressUpdate(int second, boolean isFree);

    void onPostExecute();

    void onCancelled();
}
