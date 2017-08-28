package com.ijiuqing.videocall.ui;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseFragment;
import com.ijiuqing.videocall.common.AppConfig;
import com.ijiuqing.videocall.common.Constant;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.entity.ItemData;
import com.ijiuqing.videocall.ui.view.HomeItemCell;
import com.ijiuqing.videocall.ui.view.IRecycleCell;
import com.ijiuqing.videocall.ui.view.MyItemClickListener;
import com.ijiuqing.videocall.ui.view.MyRecycleAdapter;
import com.ijiuqing.videocall.ui.view.ViewPrama;
import com.ijiuqing.videocall.util.PreviewUtils;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.videoprp.AgoraYuvEnhancer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviewFragment extends BaseFragment implements
        SeekBar.OnSeekBarChangeListener, MyItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private float mWhitenValue = 0f;
    private float mSoftenValue = 0f;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mView;
    private PreviewUtils mPreviewUtils;
    private Context mContext;
    private boolean mFirstFrame = true;
    private Button getPretty;
    private LinearLayout prettyMenu;
    private boolean isSticker = false;
    private boolean zipSuccess = false;
    private RecyclerView mListView;
    private MyRecycleAdapter mAdapter;
    private List<IRecycleCell> mListData;
    private String mCurFilterStrength;
    private FrameLayout mFrameLayout;
    private RtcEngine mRtcEngine;// Tutorial Step 1
    private AgoraYuvEnhancer yuvEnhancer = null;
    private SurfaceView mSurfaceView = null;
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
    };

    public PreviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviewFragment newInstance(String param1, String param2) {
        PreviewFragment fragment = new PreviewFragment();
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
        mWhitenValue = (float) SharedPreferencesUtils.getParam(mContext, ConstantApp.WHITENVALUE, 0f);
        mSoftenValue = (float) SharedPreferencesUtils.getParam(mContext, ConstantApp.SOFTENVALUE, 0f);
        yuvEnhancer = new AgoraYuvEnhancer(mContext);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        this.mView = view;
        mFrameLayout = (FrameLayout) mView.findViewById(R.id.Preview);
        getPretty = (Button) mView.findViewById(R.id.get_pretty);
        prettyMenu = (LinearLayout) mView.findViewById(R.id.pretty_menu);
        ViewPrama.setMargins(prettyMenu, 0, 0, 0, Constant.navigationHeight);
        getPretty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prettyMenu.getVisibility() == View.VISIBLE) {
                    prettyMenu.setVisibility(View.GONE);
                } else {
                    prettyMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        SeekBar m_Seekwhiten = (SeekBar) mView.findViewById(R.id.seek_whiten);
        m_Seekwhiten.setOnSeekBarChangeListener(this);
        m_Seekwhiten.setProgress((int) (mWhitenValue * 100));
        TextView tv_whiten = (TextView) mView.findViewById(R.id.whiten_value);
        tv_whiten.setText(String.valueOf(mWhitenValue));

        SeekBar m_Seeksoften = (SeekBar) mView.findViewById(R.id.seek_soften);
        m_Seeksoften.setOnSeekBarChangeListener(this);
        m_Seeksoften.setProgress((int) mSoftenValue * 100);
        TextView tv_soften = (TextView) mView.findViewById(R.id.soften_value);
        tv_soften.setText(String.valueOf(mSoftenValue));

        mListView = (RecyclerView) mView.findViewById(R.id.listview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mListView.setLayoutManager(mLayoutManager);
        ConstructList();
        mAdapter = new MyRecycleAdapter(getActivity(), mListData);
        mListView.setAdapter(mAdapter);
        mListView.setItemAnimator(new DefaultItemAnimator());
    }


    // Tutorial Step 1
    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getContext(), getString(R.string.private_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e("", Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    // Tutorial Step 2
    private void setupVideoProfile() {
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_480P_8, false);
        mRtcEngine.isTextureEncodeSupported();
    }

    // Tutorial Step 3
    private void setupLocalVideo() {
        mFrameLayout.removeAllViews();
        mSurfaceView = RtcEngine.CreateRendererView(getContext());
        mSurfaceView.setZOrderMediaOverlay(true);
        mFrameLayout.addView(mSurfaceView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(mSurfaceView, VideoCanvas.RENDER_MODE_ADAPTIVE, 0));
        yuvEnhancer.StartPreProcess();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_preview;
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


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        release();
    }

    private void release() {
        if (yuvEnhancer != null) yuvEnhancer.StopPreProcess();
        if (mRtcEngine == null) return;
        mRtcEngine.destroy();
        mRtcEngine = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeAgoraEngine();     // Tutorial Step 1
        setupVideoProfile();         // Tutorial Step 2
        setupLocalVideo();           // Tutorial Step 3
        mRtcEngine.startPreview();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_whiten: {
                if (fromUser) {
                    mWhitenValue = progress / 100f;
                    yuvEnhancer.SetLighteningFactor(mWhitenValue);
                    TextView tv_whiten = (TextView) mView.findViewById(R.id.whiten_value);
                    tv_whiten.setText(String.valueOf(mWhitenValue));
                    SharedPreferencesUtils.setParam(mContext, ConstantApp.WHITENVALUE, mWhitenValue);
                }
            }
            break;
            case R.id.seek_soften: {
                if (fromUser) {
                    mSoftenValue = progress / 100f;
                    yuvEnhancer.SetSmoothnessFactor(mSoftenValue);
                    TextView tv_soften = (TextView) mView.findViewById(R.id.soften_value);
                    tv_soften.setText(String.valueOf(mSoftenValue));
                    SharedPreferencesUtils.setParam(mContext, ConstantApp.SOFTENVALUE, mSoftenValue);
                }
            }
            break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onItemClick(String filtertype) {
        if (null == filtertype)
            return;
        if (filtertype.equals(mCurFilterStrength)) {
            return;
        }
        mCurFilterStrength = filtertype;
        mPreviewUtils.SetColorFilterByName(filtertype);
        mAdapter.notifyDataSetChanged();
        SharedPreferencesUtils.setParam(mContext, ConstantApp.CURFILTERSTRENGTH, filtertype);
    }

    @Override
    public String getCurFilterType() {
        return mCurFilterStrength;
    }
}
