package com.ta.finalexam.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.ProfileBean;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by TungNguyen on 11/21/2016.
 */

public class ProfileResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    public ProfileBean profileBean;

}
