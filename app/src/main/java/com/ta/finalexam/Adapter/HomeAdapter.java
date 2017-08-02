package com.ta.finalexam.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ta.finalexam.Adapter.ViewHolder.HomeViewHolder;
import com.ta.finalexam.Bean.HomeBean.HomeBean;
import com.ta.finalexam.R;
import com.ta.finalexam.callback.OnClickRecycleView;

import java.util.List;

import vn.app.base.adapter.AdapterWithItemClick;

/**
 * Created by TungNguyen on 10/30/2016.
 */

public class HomeAdapter extends AdapterWithItemClick<HomeViewHolder> {
    List<HomeBean> homeBeanList;

    OnClickRecycleView onClickCallBack;

    public void setOnClickCallBack(OnClickRecycleView onClickCallBack) {
        this.onClickCallBack = onClickCallBack;
    }

    public HomeAdapter(List<HomeBean> homeBeanList) {
        this.homeBeanList = homeBeanList;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new HomeViewHolder(layoutInflater.inflate(R.layout.item_list_home, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.bind(homeBeanList.get(position), onClickCallBack);
    }

    @Override
    public int getItemCount() {
        return homeBeanList.size();
    }
}
