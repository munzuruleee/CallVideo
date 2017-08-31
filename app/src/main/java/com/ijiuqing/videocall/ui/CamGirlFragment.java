package com.ijiuqing.videocall.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseFragment;
import com.ijiuqing.videocall.common.Constant;
import com.ijiuqing.videocall.entity.Bean;
import com.ijiuqing.videocall.entity.StatusInfo;
import com.ijiuqing.videocall.entity.StatusListInfo;
import com.ijiuqing.videocall.entity.UserInfo;
import com.ijiuqing.videocall.model.CamGirlListModel;
import com.ijiuqing.videocall.model.OnGetCamGirListListener;
import com.ijiuqing.videocall.model.OnGetCamGirListener;
import com.ijiuqing.videocall.model.imp.CamGirlListImpl;
import com.ijiuqing.videocall.ui.view.CamGirlListAdapter;
import com.ijiuqing.videocall.ui.view.ViewPrama;
import com.ijiuqing.videocall.util.KeyBoardUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CamGirlFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CamGirlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CamGirlFragment extends BaseFragment implements
        View.OnClickListener, OnSwipeListener<List<UserInfo>> ,OnRefreshListener
        ,OnGetCamGirListListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mView;
    private String mParam1;
    private String mParam2;
    private RecyclerView rcvCamGirlList;
    private SmartRefreshLayout refreshLayout;
    private List<List<UserInfo>> mList = new ArrayList<>();
    private CamGirlListModel camGirlListModel;
    private EditText edSearch;
    private ImageView ivSearch;
    public CamGirlFragment() {
        // Required empty public constructor
    }

    public static CamGirlFragment newInstance(String param1, String param2) {
        CamGirlFragment fragment = new CamGirlFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        camGirlListModel = new CamGirlListImpl();
    }

    @Override
    public void onResume() {
        super.onResume();
        edSearch.clearFocus();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        this.mView = view;
        initData();
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);
        ViewPrama.setMargins(refreshLayout,0,0,0, Constant.navigationHeight);
        refreshLayout.setOnRefreshListener(this);
        rcvCamGirlList = (RecyclerView) view.findViewById(R.id.cam_girl_list);
        rcvCamGirlList.setItemAnimator(new DefaultItemAnimator());
        rcvCamGirlList.setAdapter(new CamGirlListAdapter(getContext(),mList));
        CardItemTouchHelperCallback cardCallback =
                new CardItemTouchHelperCallback(rcvCamGirlList.getAdapter(), mList);
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(rcvCamGirlList, touchHelper);
        rcvCamGirlList.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(rcvCamGirlList);
        edSearch = (EditText) mView.findViewById(R.id.edit_search);
        ivSearch = (ImageView) mView.findViewById(R.id.iv_search);
        edSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    ivSearch.setVisibility(View.VISIBLE);
                }else {
                    edSearch.setText("");
                    ivSearch.setVisibility(View.GONE);
                    KeyBoardUtils.closeKeybord((EditText) view,getContext());
                }
            }
        });
        ivSearch.setOnClickListener(this);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                camGirlListModel.getCamGirListForUid(getContext(), charSequence.toString(), new OnGetCamGirListener() {
                    @Override
                    public void onSuccess(StatusInfo list) {
                        if (list.getData()!=null) {
                            List<UserInfo> listItem = new ArrayList<UserInfo>();
                            listItem.add(list.getData());
                            mList.clear();
                            mList.add(listItem);
                            rcvCamGirlList.getAdapter().notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initData() {
//        Gson gson = new Gson();
//        StatusListInfo sli = gson.fromJson(Bean.list, StatusListInfo.class);
//        mList.clear();
//        mList.add(sli.getData());
        camGirlListModel.getCamGirList(getContext(),this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_camgirl;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_search:
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                view.requestFocusFromTouch();
                break;
        }
    }

    @Override
    public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
        CamGirlListAdapter.MyViewHolder myHolder = (CamGirlListAdapter.MyViewHolder) viewHolder;
        viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
        if (direction == CardConfig.SWIPING_LEFT) {
            Log.e("onSwiping", "SWIPING_LEFT");
        } else if (direction == CardConfig.SWIPING_RIGHT) {
            Log.e("onSwiping", "SWIPING_RIGHT");
        } else {
            Log.e("onSwiping", "else");
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, List<UserInfo> userInfos, int direction) {
        CamGirlListAdapter.MyViewHolder myHolder = (CamGirlListAdapter.MyViewHolder) viewHolder;
        viewHolder.itemView.setAlpha(1f);
        Log.e("onSwiped", "else");
    }

    @Override
    public void onSwipedClear() {
        Toast.makeText(getContext(),"onSwipedClear",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        camGirlListModel.getCamGirList(getContext(),this);
        refreshlayout.finishRefresh();
    }

    @Override
    public void onSuccess(StatusListInfo list) {
        mList.clear();
        if (list.getData()==null) {
            return;
        }
        List<UserInfo> listItem = new ArrayList<>();
        for (UserInfo item : list.getData()) {
            listItem.add(item);
            if (listItem.size() == 4) {
                mList.add(listItem);
                listItem = new ArrayList<>();
            }
        }
        if (listItem.size()>0){
            mList.add(listItem);
        }
        rcvCamGirlList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onFail() {
        Toast.makeText(getContext(), "主播列表获取失败", Toast.LENGTH_LONG).show();
    }
}
