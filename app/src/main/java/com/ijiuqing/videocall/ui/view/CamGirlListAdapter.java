package com.ijiuqing.videocall.ui.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.entity.UserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songjiyuan on 17/8/27.
 */

public class CamGirlListAdapter extends RecyclerView.Adapter {
    private List<List<UserInfo>> list;
    private Context mContext;

    public CamGirlListAdapter(Context context,List<List<UserInfo>> list) {
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
        if (mList.get(0)!=null){
            myHolder.linPosition1.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mList.get(0).getUiHeadimgurl(),myHolder.ivImg1);
            myHolder.tvNickName1.setText(mList.get(0).getUlNickname());
            switch (mList.get(0).getUlSex()){
                case 1:
                    myHolder.ivSex1.setImageResource(R.drawable.sex_male);
                    break;
                case 2:
                    myHolder.ivSex1.setImageResource(R.drawable.sex_female);
                    break;
            }
            myHolder.tvMoney1.setText(mList.get(0).getUlMoney());
            myHolder.ibtn_join1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,mList.get(0).getUlId(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (mList.get(1)!=null){
            myHolder.linPosition2.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mList.get(1).getUiHeadimgurl(),myHolder.ivImg2);
            myHolder.tvNickName2.setText(mList.get(1).getUlNickname());
            switch (mList.get(1).getUlSex()){
                case 1:
                    myHolder.ivSex2.setImageResource(R.drawable.sex_male);
                    break;
                case 2:
                    myHolder.ivSex2.setImageResource(R.drawable.sex_female);
                    break;
            }
            myHolder.tvMoney2.setText(mList.get(1).getUlMoney());
            myHolder.ibtn_join2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,mList.get(1).getUlId(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (mList.get(2)!=null){
            myHolder.linPosition3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mList.get(2).getUiHeadimgurl(),myHolder.ivImg3);
            myHolder.tvNickName3.setText(mList.get(2).getUlNickname());
            switch (mList.get(2).getUlSex()){
                case 1:
                    myHolder.ivSex3.setImageResource(R.drawable.sex_male);
                    break;
                case 2:
                    myHolder.ivSex3.setImageResource(R.drawable.sex_female);
                    break;
            }
            myHolder.tvMoney3.setText(mList.get(2).getUlMoney());
            myHolder.ibtn_join3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,mList.get(2).getUlId(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (mList.get(3)!=null){
            myHolder.linPosition4.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(mList.get(3).getUiHeadimgurl(),myHolder.ivImg4);
            myHolder.tvNickName4.setText(mList.get(3).getUlNickname());
            switch (mList.get(3).getUlSex()){
                case 1:
                    myHolder.ivSex4.setImageResource(R.drawable.sex_male);
                    break;
                case 2:
                    myHolder.ivSex4.setImageResource(R.drawable.sex_female);
                    break;
            }
            myHolder.tvMoney4.setText(mList.get(3).getUlMoney());
            myHolder.ibtn_join4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,mList.get(3).getUlId(),Toast.LENGTH_SHORT).show();
                }
            });
        }
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
        ImageView ivSex1;
        ImageView ivSex2;
        ImageView ivSex3;
        ImageView ivSex4;
        TextView tvNickName1;
        TextView tvNickName2;
        TextView tvNickName3;
        TextView tvNickName4;
        TextView tvMoney1;
        TextView tvMoney2;
        TextView tvMoney3;
        TextView tvMoney4;
        ImageButton ibtn_join1;
        ImageButton ibtn_join2;
        ImageButton ibtn_join3;
        ImageButton ibtn_join4;
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
            ivSex1 = (ImageView) itemView.findViewById(R.id.iv_sex1);
            ivSex2 = (ImageView) itemView.findViewById(R.id.iv_sex2);
            ivSex3 = (ImageView) itemView.findViewById(R.id.iv_sex3);
            ivSex4 = (ImageView) itemView.findViewById(R.id.iv_sex4);
            tvNickName1 = (TextView) itemView.findViewById(R.id.tv_nick_name1);
            tvNickName2 = (TextView) itemView.findViewById(R.id.tv_nick_name2);
            tvNickName3 = (TextView) itemView.findViewById(R.id.tv_nick_name3);
            tvNickName4 = (TextView) itemView.findViewById(R.id.tv_nick_name4);
            tvMoney1 = (TextView) itemView.findViewById(R.id.tv_money1);
            tvMoney2 = (TextView) itemView.findViewById(R.id.tv_money2);
            tvMoney3 = (TextView) itemView.findViewById(R.id.tv_money3);
            tvMoney4 = (TextView) itemView.findViewById(R.id.tv_money4);
            ibtn_join1 = (ImageButton) itemView.findViewById(R.id.ibtn_join1);
            ibtn_join2 = (ImageButton) itemView.findViewById(R.id.ibtn_join2);
            ibtn_join3 = (ImageButton) itemView.findViewById(R.id.ibtn_join3);
            ibtn_join4 = (ImageButton) itemView.findViewById(R.id.ibtn_join4);
        }

    }
}
