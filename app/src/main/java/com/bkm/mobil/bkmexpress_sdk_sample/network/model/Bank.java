package com.bkm.mobil.bkmexpress_sdk_sample.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ebayhan on 6.04.2020.
 */
public class Bank {

    @SerializedName("bankCode")
    @Expose
    private String bankCode;

    @Expose(serialize = false)
    private String bankName;

    @Expose(serialize = false)
    private String installmentAmount;

    @SerializedName("instList")
    @Expose
    private ArrayList<Integer> installmentList;

    public Bank(String bankCode, String bankName, String installmentAmount, ArrayList<Integer> installmentList) {
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.installmentAmount = installmentAmount;
        this.installmentList = installmentList;
    }
}
