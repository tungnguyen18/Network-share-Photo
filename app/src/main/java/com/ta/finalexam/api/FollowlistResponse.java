package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.FollowlistBean.User;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by TungNguyen on 11/16/2016.
 */

public class FollowlistResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public List<User> data = new ArrayList<User>();
}
