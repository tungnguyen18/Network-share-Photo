package com.ta.finalexam.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TungNguyen on 11/22/2016.
 */

public class UpdateProfileBean {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("avatar")
    @Expose
    public String avatar;
    @SerializedName("is_following")
    @Expose
    public Boolean isFollowing;
    @SerializedName("follow")
    @Expose
    public Integer follow;
    @SerializedName("follower")
    @Expose
    public Integer follower;
    @SerializedName("post")
    @Expose
    public Integer post;
}
