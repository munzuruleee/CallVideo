package com.ijiuqing.videocall.work;

import android.os.AsyncTask;

/**
 * Created by songjiyuan on 17/8/27.
 */

public class CountDownTask extends AsyncTask<Void,Integer,Void> {
    private CountDownCallBack countDownCallBack;
    private int freeTime = 15;
    private int PayTime = 15;
    private boolean isFree = true;
    public CountDownTask(CountDownCallBack countDownCallBack) {
        this.countDownCallBack = countDownCallBack;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (freeTime>0){
            isFree = true;
            publishProgress(freeTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            freeTime--;
        }
        while (PayTime>0){
            isFree = false;
            publishProgress(PayTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PayTime--;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        countDownCallBack.onProgressUpdate(values[0],isFree);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        countDownCallBack.onPostExecute();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        countDownCallBack.onCancelled();
    }
}
