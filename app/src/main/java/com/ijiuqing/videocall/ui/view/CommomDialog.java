package com.ijiuqing.videocall.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ijiuqing.videocall.R;

/**
 * Created by songjiyuan on 17/9/8.
 */

public class CommomDialog extends Dialog implements View.OnClickListener {
    private TextView tvTitle;
    private TextView tvTakePhoto;
    private TextView tvChoosePhoto;
    private TextView tvCancel;
    private Context mContext;
    private OnDialogViewClickListener listener;
    private String title;

    public CommomDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public CommomDialog(Context context, int themeResId, OnDialogViewClickListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    protected CommomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomDialog setTitle(String title){
        this.title = title;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        tvTitle = (TextView)findViewById(R.id.title);
        tvTakePhoto = (TextView) findViewById(R.id.btn_take_photo);
        tvChoosePhoto = (TextView) findViewById(R.id.btn_choose_photo);
        tvCancel = (TextView) findViewById(R.id.btn_cancel);
        tvTakePhoto.setOnClickListener(this);
        tvChoosePhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_take_photo:
                if (listener!=null){
                    listener.onTakePhotoClick();
                }
                break;
            case R.id.btn_choose_photo:
                if (listener!=null){
                    listener.onChoosePhotoClick();
                }
                break;
            case R.id.btn_cancel:
                if (listener!=null){
                    listener.onCancelClick();
                }
                break;
        }
        dismiss();
    }

    public interface OnDialogViewClickListener{
        void onTakePhotoClick();

        void onChoosePhotoClick();

        void onCancelClick();
    }
}
