package com.bkm.mobil.bkmexpress_sdk_sample.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ebayhan on 6.04.2020.
 */
public class BaseResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("resultCode")
    @Expose
    public int resultCode;

    @SerializedName("resultMsg")
    @Expose
    public String resultMsg;
}
