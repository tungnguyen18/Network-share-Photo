package com.ta.finalexam.api;

import com.google.gson.annotations.SerializedName;
import com.ta.finalexam.Bean.ImageUpload;

import java.util.ArrayList;
import java.util.List;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by TungNguyen on 11/1/2016.
 */

public class ImageUploadResponse extends BaseResponse {
    @SerializedName("data")
    public List<ImageUpload> data = new ArrayList<ImageUpload>();
}
