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

import com.ijiuqing.videocall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songjiyuan on 17/8/27.
 */

public class CamGirlListAdapter extends RecyclerView.Adapter {
    private List<Integer> list;
    private Context mContext;

    public CamGirlListAdapter(Context context,List<Integer> list) {
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView gridView;

        MyViewHolder(View itemView) {
            super(itemView);
//            gridView = (RecyclerView) itemView.findViewById(R.id.grid);
        }

    }
}
