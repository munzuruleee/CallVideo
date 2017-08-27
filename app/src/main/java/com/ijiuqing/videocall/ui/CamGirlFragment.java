package com.ijiuqing.videocall.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseFragment;
import com.ijiuqing.videocall.common.Constant;
import com.ijiuqing.videocall.ui.view.CamGirlListAdapter;
import com.ijiuqing.videocall.ui.view.ViewPrama;
import com.ijiuqing.videocall.ui.view.ViewUtil;
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
        View.OnClickListener, OnSwipeListener<Integer> ,OnRefreshListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mView;
    private String mParam1;
    private String mParam2;
    private RecyclerView rcvCamGirlList;
    private SmartRefreshLayout refreshLayout;
    private List<Integer> mList = new ArrayList<>();

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
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        this.mView = view;
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
        initData();
    }

    private void initData() {
        mList.add(1);
        mList.add(1);
        mList.add(1);
        mList.add(1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_camgirl;
    }


    @Override
    public void onClick(View view) {

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
    public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer integer, int direction) {
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
        initData();
        refreshlayout.finishRefresh();
        rcvCamGirlList.getAdapter().notifyDataSetChanged();
    }
}
