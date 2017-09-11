package com.ijiuqing.videocall.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseFragment;
import com.ijiuqing.videocall.common.Constant;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.ui.view.CommomDialog;
import com.ijiuqing.videocall.ui.view.ViewPrama;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener, CommomDialog.OnDialogViewClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View mView;
    private ImageView ivHeadPortrait;
    private TextView tvNikeName;
    private TextView tvDiamondNum;
    private TextView tvTNum;
    private TextView tvID;
    private ImageView ivSex;
    private NestedScrollView content;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initView(view);
        displayData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    private void initView(View mView) {
        this.mView = mView;
        content = (NestedScrollView) mView.findViewById(R.id.nested_scrollview);
        ViewPrama.setMargins(content, 0, 0, 0, Constant.navigationHeight);
        ivHeadPortrait = (ImageView) mView.findViewById(R.id.head_portrait);
        tvNikeName = (TextView) mView.findViewById(R.id.nike_name);
        tvID = (TextView) mView.findViewById(R.id.id);
        tvDiamondNum = (TextView) mView.findViewById(R.id.diamond_num);
        tvTNum = (TextView) mView.findViewById(R.id.t_num);
        ivSex = (ImageView) mView.findViewById(R.id.sex);
        ivHeadPortrait.setOnClickListener(this);
        TextView setting = (TextView) mView.findViewById(R.id.setting);
        TextView tragt = (TextView) mView.findViewById(R.id.tragt);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),VideoChatViewActivity.class);
                intent.putExtra(ConstantApp.CHANNEL,"agora.io");
                getContext().startActivity(intent);
            }
        });
        tragt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void displayData() {
        int sex = (int) SharedPreferencesUtils.getParam(getContext(), ConstantApp.ULSEX, 0);
        String hiUrl = (String) SharedPreferencesUtils.getParam(getContext(), ConstantApp.UIHEADIMGURL, "");
        tvID.setText((String) SharedPreferencesUtils.getParam(getContext(), ConstantApp.ULID, ""));
        tvNikeName.setText((String) SharedPreferencesUtils.getParam(getContext(), ConstantApp.ULNICKNAME, ""));
        if (sex == 1){
            ivSex.setImageResource(R.drawable.sex_male);
        }else {
            ivSex.setImageResource(R.drawable.sex_female);
        }
        ImageLoader.getInstance().displayImage(hiUrl,ivHeadPortrait);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_portrait:
                new CommomDialog(getContext(),R.style.dialog,this).setTitle("设置头像").show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTakePhotoClick() {
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        tempUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "image.jpg"));
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @Override
    public void onChoosePhotoClick() {
        Intent openAlbumIntent = new Intent(
                Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    }

    @Override
    public void onCancelClick() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX
        // 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     *
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            ivHeadPortrait.setImageBitmap(photo);
        }
    }

}
