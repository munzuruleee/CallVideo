package com.ijiuqing.videocall.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.common.AppConfig;
import com.ijiuqing.videocall.common.Constant;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.common.ItemData;
import com.ijiuqing.videocall.ui.view.HomeItemCell;
import com.ijiuqing.videocall.ui.view.IRecycleCell;
import com.ijiuqing.videocall.ui.view.MyItemClickListener;
import com.ijiuqing.videocall.ui.view.MyRecycleAdapter;
import com.ijiuqing.videocall.util.PreviewUtils;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Blank1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Blank1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Blank1Fragment extends Fragment implements SurfaceHolder.Callback, Camera.PreviewCallback
        ,SeekBar.OnSeekBarChangeListener, MyItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private float mPinkValue = 0.4f;
    private float mWhitenValue = 0.7f;
    private float mReddenValue = 0.5f;
    private int mSoftenValue = 70;
    private int mFilterValue = 100;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private View mView;
    private PreviewUtils mPreviewUtils;
    private Context mContext;
    private SurfaceView mSurfaceView;
    private boolean mFirstFrame = true;
    private Button getPretty;
    private LinearLayout prettyMenu;
    private boolean isSticker = false;
    private boolean zipSuccess = false;
    private RecyclerView mListView;
    private MyRecycleAdapter mAdapter;
    private List<IRecycleCell> mListData;
    private String mCurFilterStrength;
    public Blank1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Blank1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Blank1Fragment newInstance(String param1, String param2) {
        Blank1Fragment fragment = new Blank1Fragment();
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
        mContext = getContext();
        mPreviewUtils = new PreviewUtils(getContext(), this);
        mPinkValue = (float) SharedPreferencesUtils.getParam(mContext, ConstantApp.PINKVALUE, 0.4f);
        mWhitenValue = (float) SharedPreferencesUtils.getParam(mContext, ConstantApp.WHITENVALUE, 0.4f);
        mReddenValue = (float) SharedPreferencesUtils.getParam(mContext, ConstantApp.REDDENVALUE, 0.4f);
        mSoftenValue = (int) SharedPreferencesUtils.getParam(mContext, ConstantApp.SOFTENVALUE, 70);
        mFilterValue = (int) SharedPreferencesUtils.getParam(mContext, ConstantApp.FILTERVALUE, 100);
        mCurFilterStrength = (String) SharedPreferencesUtils.getParam(mContext, ConstantApp.CURFILTERSTRENGTH, "Deep");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_blank1, container, false);
        initView(mView);
        return mView;
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    private void initView(View mView) {
        mSurfaceView = (SurfaceView) mView.findViewById(R.id.Preview);
        getPretty = (Button) mView.findViewById(R.id.get_pretty);
        prettyMenu = (LinearLayout) mView.findViewById(R.id.pretty_menu);
        setMargins(prettyMenu,0,0,0,Constant.navigationHeight);
        getPretty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prettyMenu.getVisibility() == View.VISIBLE) {
                    prettyMenu.setVisibility(View.GONE);
                }else {
                    prettyMenu.setVisibility(View.VISIBLE);
                }
            }
        });
        SeekBar m_Seekpink = (SeekBar) mView.findViewById(R.id.seek_pink);
        m_Seekpink.setOnSeekBarChangeListener(this);
        m_Seekpink.setProgress((int) (mPinkValue * 100));
        TextView tv_pink = (TextView) mView.findViewById(R.id.pink_value);
        tv_pink.setText(String.valueOf(mPinkValue));

        SeekBar m_Seekwhiten = (SeekBar) mView.findViewById(R.id.seek_whiten);
        m_Seekwhiten.setOnSeekBarChangeListener(this);
        m_Seekwhiten.setProgress((int) (mWhitenValue * 100));
        TextView tv_whiten = (TextView) mView.findViewById(R.id.whiten_value);
        tv_whiten.setText(String.valueOf(mWhitenValue));

        SeekBar m_Seekredden = (SeekBar) mView.findViewById(R.id.seek_redden);
        m_Seekredden.setOnSeekBarChangeListener(this);
        m_Seekredden.setProgress((int) (mReddenValue * 100));
        TextView tv_redden = (TextView) mView.findViewById(R.id.redden_value);
        tv_redden.setText(String.valueOf(mReddenValue));


        SeekBar m_Seeksoften = (SeekBar) mView.findViewById(R.id.seek_soften);
        m_Seeksoften.setOnSeekBarChangeListener(this);
        m_Seeksoften.setProgress(mSoftenValue);
        TextView tv_soften = (TextView) mView.findViewById(R.id.soften_value);
        tv_soften.setText(String.valueOf(mSoftenValue));

        SeekBar m_SeeksFilter = (SeekBar) mView.findViewById(R.id.filter_redden);
        m_SeeksFilter.setOnSeekBarChangeListener(this);
        m_SeeksFilter.setProgress(mFilterValue);
        TextView tv_filter = (TextView) mView.findViewById(R.id.filter_value);
        tv_filter.setText(String.valueOf(mFilterValue));

        mListView = (RecyclerView) mView.findViewById(R.id.listview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mListView.setLayoutManager(mLayoutManager);
        ConstructList();
        mAdapter = new MyRecycleAdapter(getActivity(), mListData);
        mListView.setAdapter(mAdapter);
        mListView.setItemAnimator(new DefaultItemAnimator());
    }

    private void ConstructList() {
        mListData = new ArrayList<>();
        for (int i = 0; i < AppConfig.mFilterName.length; ++i) {
            ItemData itemData = new ItemData();
            itemData.filterName = AppConfig.mFilterName[i];
            itemData.filterType = AppConfig.mFilterType[i];
            mListData.add(new HomeItemCell(getActivity(), itemData, this));
        }
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
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPreviewUtils.pause();
        mFirstFrame = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPreviewUtils.freeRes();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPreviewUtils.onresume();//启动照相机
        reStartEngine();
        // 注册方向回调，检测屏幕方向改变
    }

    public void reStartEngine() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float fScreenHeight = displayMetrics.heightPixels;

        int iLayoutHeight = (int) fScreenHeight;//- (int)(fScreenHeight * 0.2f);
        int iLayoutWidth = (int) ((iLayoutHeight / (float) mPreviewUtils.getCameraWidth()) * mPreviewUtils.getCameraHeight());

        mSurfaceView.getHolder().addCallback(this);

        ViewGroup.LayoutParams surfaceLayout;
        surfaceLayout = mSurfaceView.getLayoutParams();
        surfaceLayout.width = iLayoutWidth;
        surfaceLayout.height = iLayoutHeight;
        mSurfaceView.setLayoutParams(surfaceLayout);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        mPreviewUtils.frameProcess(data, 0, mFirstFrame, false);//data 可以传空 根据TextureId进行美颜
        mFirstFrame = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPreviewUtils.startCameraPreview(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mPreviewUtils.setCameraInfo(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("1", "1");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_pink: {
                if (fromUser) {
                    mPinkValue = progress / 100f;
                    mPreviewUtils.SetSkinColor(mPinkValue, mWhitenValue, mReddenValue);
                    TextView tv_pink = (TextView) mView.findViewById(R.id.pink_value);
                    tv_pink.setText(String.valueOf(mPinkValue));
                }
            }
            break;
            case R.id.seek_whiten: {
                if (fromUser) {
                    mWhitenValue = progress / 100f;
                    mPreviewUtils.SetSkinColor(mPinkValue, mWhitenValue, mReddenValue);
                    TextView tv_whiten = (TextView) mView.findViewById(R.id.whiten_value);
                    tv_whiten.setText(String.valueOf(mWhitenValue));
                }
            }
            break;
            case R.id.seek_redden: {
                if (fromUser) {
                    mReddenValue = progress / 100f;
                    mPreviewUtils.SetSkinColor(mPinkValue, mWhitenValue, mReddenValue);
                    TextView tv_redden = (TextView) mView.findViewById(R.id.redden_value);
                    tv_redden.setText(String.valueOf(mReddenValue));
                }
            }
            break;
            case R.id.filter_redden: {
                if (fromUser) {
                    mFilterValue = progress;
                    mPreviewUtils.SetColorFilterStrength(mFilterValue);
                    TextView tv_blur = (TextView) mView.findViewById(R.id.filter_value);
                    tv_blur.setText(String.valueOf(mFilterValue));
                }
            }
            break;
            case R.id.seek_soften: {
                if (fromUser) {
                    mSoftenValue = progress;
                    mPreviewUtils.SetSkinSoftenStrength(mSoftenValue);
                    TextView tv_soften = (TextView) mView.findViewById(R.id.soften_value);
                    tv_soften.setText(String.valueOf(mSoftenValue));
                }
            }
            break;
        }
        SharedPreferencesUtils.setParam(mContext, ConstantApp.PINKVALUE, mPinkValue);
        SharedPreferencesUtils.setParam(mContext, ConstantApp.WHITENVALUE, mWhitenValue);
        SharedPreferencesUtils.setParam(mContext, ConstantApp.REDDENVALUE, mReddenValue);
        SharedPreferencesUtils.setParam(mContext, ConstantApp.SOFTENVALUE, mSoftenValue);
        SharedPreferencesUtils.setParam(mContext, ConstantApp.FILTERVALUE, mFilterValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onItemClick(String filtertype) {
        if (null==filtertype)
            return;
        if (filtertype.equals(mCurFilterStrength)) {
            return;
        }
        mCurFilterStrength = filtertype;
        mPreviewUtils.SetColorFilterByName(filtertype);
        mPreviewUtils.SetColorFilterStrength(mFilterValue);
        mAdapter.notifyDataSetChanged();
        SharedPreferencesUtils.setParam(mContext, ConstantApp.CURFILTERSTRENGTH, filtertype);
    }

    @Override
    public String getCurFilterType() {
        return mCurFilterStrength;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
