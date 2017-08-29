package com.ijiuqing.videocall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseFragment;
import com.ijiuqing.videocall.common.Constant;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.ui.view.ViewPrama;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {
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
        FrameLayout content = (FrameLayout) mView.findViewById(R.id.content);
        ViewPrama.setMargins(content, 0, 0, 0, Constant.navigationHeight);
        ivHeadPortrait = (ImageView) mView.findViewById(R.id.head_portrait);
        tvNikeName = (TextView) mView.findViewById(R.id.nike_name);
        tvID = (TextView) mView.findViewById(R.id.id);
        tvDiamondNum = (TextView) mView.findViewById(R.id.diamond_num);
        tvTNum = (TextView) mView.findViewById(R.id.t_num);
        ivSex = (ImageView) mView.findViewById(R.id.sex);
        TextView setting = (TextView) mView.findViewById(R.id.setting);
        TextView tragt = (TextView) mView.findViewById(R.id.tragt);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),VideoChatViewActivity.class));
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

    }
}
