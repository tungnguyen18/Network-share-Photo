package com.ta.finalexam.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ta.finalexam.Adapter.FollowListAdapter;
import com.ta.finalexam.Bean.FollowlistBean.User;
import com.ta.finalexam.Constant.HeaderOption;
import com.ta.finalexam.R;
import com.ta.finalexam.api.FollowlistResponse;
import com.ta.finalexam.api.Request.FollowlistRequest;

import java.util.List;

import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.callback.OnRecyclerViewItemClick;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;

/**
 * Created by TungNguyen on 10/14/2016.
 */

public class FragmentFollowlist extends BaseHeaderListFragment {
    List<User> memberFollowList;
    FollowListAdapter followListAdapter;

    public FragmentFollowlist() {

    }

    public static FragmentFollowlist newInstance() {
        FragmentFollowlist newFragment = new FragmentFollowlist();
        return newFragment;
    }



    @Override
    protected void onRefreshData() {
        getFollowlistdata();
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        if (memberFollowList == null) {
            getFollowlistdata();
        }
    }

    @Override
    protected boolean isSkipGenerateBaseLayout() {
        return false;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        setCanRefresh(true);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onRefresh() {
        getFollowlistdata();
    }

    private void getFollowlistdata() {
        showCoverNetworkLoading();
        FollowlistRequest followlistRequest = new FollowlistRequest();
        followlistRequest.setRequestCallBack(new ApiObjectCallBack<FollowlistResponse>() {
            @Override
            public void onSuccess(FollowlistResponse data) {
                initialResponseHandled();
                memberFollowList = data.data;
                handleFollowlistdata(memberFollowList);
            }

            @Override
            public void onFail(int failCode, String message) {
                DebugLog.e("Error" + failCode + message);
            }
        });
        followlistRequest.execute();
    }

    private void handleFollowlistdata(List<User> dataFollowList) {
        followListAdapter = new FollowListAdapter(dataFollowList);
        followListAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO: chuyen man user
                FragmentUtil.pushFragmentWithAnimation(getActivity(),FragmentProfile.newInstance(memberFollowList.get(position).user.id),null);
            }
        });
        rvList.setAdapter(followListAdapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow_screen;
    }

    @Override
    protected int getLeftIcon() {
        return HeaderOption.LEFT_MENU;
    }

    @Override
    protected int getRightIcon() {
        return HeaderOption.RIGHT_NO_OPTION;
    }

    @Override
    public String getScreenTitle() {
        return "Follow";
    }

    @Override
    protected boolean isStartWithLoading() {
        return false;
    }


}
