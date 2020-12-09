package com.bkm.mobil.bkmexpress_sdk_sample.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ebayhan on 6.04.2020.
 */
public class TokenResponse extends BaseResponse {

    @SerializedName("token")
    @Expose
    public String token;
}
