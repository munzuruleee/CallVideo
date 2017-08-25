package com.ijiuqing.videocall.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.common.Constant;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.ui.view.ViewPrama;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Blank3Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Blank3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Blank3Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mView;
    private OnFragmentInteractionListener mListener;
    private ImageView ivHeadPortrait;
    private TextView tvNikeName;
    private TextView tvDiamondNum;
    private TextView tvTNum;
    private TextView tvID;
    private ImageView ivSex;

    public Blank3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Blank3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Blank3Fragment newInstance(String param1, String param2) {
        Blank3Fragment fragment = new Blank3Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_blank3, container, false);
        initView(mView);
        displayData();
        return mView;
    }

    private void initView(View mView) {
        FrameLayout content = (FrameLayout) mView.findViewById(R.id.content);
        ViewPrama.setMargins(content, 0, 0, 0, Constant.navigationHeight);
        ivHeadPortrait = (ImageView) mView.findViewById(R.id.head_portrait);
        tvNikeName = (TextView) mView.findViewById(R.id.nike_name);
        tvID = (TextView) mView.findViewById(R.id.id);
        tvDiamondNum = (TextView) mView.findViewById(R.id.diamond_num);
        tvTNum = (TextView) mView.findViewById(R.id.t_num);
        ivSex = (ImageView) mView.findViewById(R.id.sex);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
