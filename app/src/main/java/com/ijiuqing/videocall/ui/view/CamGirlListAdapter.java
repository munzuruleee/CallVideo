package com.ijiuqing.videocall.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.AGApplication;
import com.ijiuqing.videocall.common.AppUrl;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.entity.StatusInfo;
import com.ijiuqing.videocall.entity.UserInfo;
import com.ijiuqing.videocall.model.OnLoginListener;
import com.ijiuqing.videocall.ui.NoticeActivity;
import com.ijiuqing.videocall.ui.VideoChatViewActivity;
import com.ijiuqing.videocall.util.SharedPreferencesUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by songjiyuan on 17/8/27.
 */

public class CamGirlListAdapter extends RecyclerView.Adapter {
    private List<List<UserInfo>> list;
    private Context mContext;
    private String roomId;

    public CamGirlListAdapter(Context context, List<List<UserInfo>> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        final List<UserInfo> mList = list.get(position);
        int sizeList = mList.size();
        if (sizeList == 0) {
            return;
        }
        if (sizeList > 0) {
            myHolder.linPosition1.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mList.get(0).getUiHeadimgurl(), myHolder.ivImg1);
            myHolder.tvNickName1.setText(mList.get(0).getUlNickname());
            myHolder.tvMoney1.setText(mList.get(0).getUlMoney());
            myHolder.ivImg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushUid(mList.get(0).getUlId());
                }
            });
        }else {
            myHolder.linPosition1.setVisibility(View.GONE);
        }
        if (sizeList > 1) {
            myHolder.linPosition2.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mList.get(1).getUiHeadimgurl(), myHolder.ivImg2);
            myHolder.tvNickName2.setText(mList.get(1).getUlNickname());
            myHolder.tvMoney2.setText(mList.get(1).getUlMoney());
            myHolder.ivImg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushUid(mList.get(1).getUlId());
                }
            });
        }else {
            myHolder.linPosition2.setVisibility(View.GONE);
        }
        if (sizeList > 2) {
            myHolder.linPosition3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mList.get(2).getUiHeadimgurl(), myHolder.ivImg3);
            myHolder.tvNickName3.setText(mList.get(2).getUlNickname());
            myHolder.tvMoney3.setText(mList.get(2).getUlMoney());
            myHolder.ivImg3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushUid(mList.get(2).getUlId());
                }
            });
        }else {
            myHolder.linPosition3.setVisibility(View.GONE);
        }
        if (sizeList > 3) {
            myHolder.linPosition4.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mList.get(3).getUiHeadimgurl(), myHolder.ivImg4);
            myHolder.tvNickName4.setText(mList.get(3).getUlNickname());
            myHolder.tvMoney4.setText(mList.get(3).getUlMoney());
            myHolder.ivImg4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pushUid(mList.get(3).getUlId());
                }
            });
        }else {
            myHolder.linPosition4.setVisibility(View.GONE);
        }
    }

    public void pushUid(final String uid) {
        roomId = uid + SharedPreferencesUtils.getParam(mContext, ConstantApp.ULID, "");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppUrl.link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(mContext, VideoChatViewActivity.class);
                intent.putExtra(ConstantApp.CHANNEL, roomId);
                mContext.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Params = new HashMap<String, String>();
                Params.put("uid", uid);
                Params.put("roomId", roomId);
                return Params;
            }
        };
        AGApplication.mQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linPosition1;
        LinearLayout linPosition2;
        LinearLayout linPosition3;
        LinearLayout linPosition4;
        ImageView ivImg1;
        ImageView ivImg2;
        ImageView ivImg3;
        ImageView ivImg4;
        TextView tvNickName1;
        TextView tvNickName2;
        TextView tvNickName3;
        TextView tvNickName4;
        TextView tvMoney1;
        TextView tvMoney2;
        TextView tvMoney3;
        TextView tvMoney4;

        MyViewHolder(View itemView) {
            super(itemView);
            linPosition1 = (LinearLayout) itemView.findViewById(R.id.lin_position1);
            linPosition2 = (LinearLayout) itemView.findViewById(R.id.lin_position2);
            linPosition3 = (LinearLayout) itemView.findViewById(R.id.lin_position3);
            linPosition4 = (LinearLayout) itemView.findViewById(R.id.lin_position4);
            ivImg1 = (ImageView) itemView.findViewById(R.id.iv_img1);
            ivImg2 = (ImageView) itemView.findViewById(R.id.iv_img2);
            ivImg3 = (ImageView) itemView.findViewById(R.id.iv_img3);
            ivImg4 = (ImageView) itemView.findViewById(R.id.iv_img4);
            tvNickName1 = (TextView) itemView.findViewById(R.id.tv_nick_name1);
            tvNickName2 = (TextView) itemView.findViewById(R.id.tv_nick_name2);
            tvNickName3 = (TextView) itemView.findViewById(R.id.tv_nick_name3);
            tvNickName4 = (TextView) itemView.findViewById(R.id.tv_nick_name4);
            tvMoney1 = (TextView) itemView.findViewById(R.id.tv_money1);
            tvMoney2 = (TextView) itemView.findViewById(R.id.tv_money2);
            tvMoney3 = (TextView) itemView.findViewById(R.id.tv_money3);
            tvMoney4 = (TextView) itemView.findViewById(R.id.tv_money4);
        }

    }
}
