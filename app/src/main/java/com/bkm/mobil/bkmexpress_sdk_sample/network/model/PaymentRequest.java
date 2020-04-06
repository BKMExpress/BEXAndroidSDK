package com.bkm.mobil.bkmexpress_sdk_sample.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ebayhan on 6.04.2020.
 */
public class PaymentRequest {

    @SerializedName("bankList")
    @Expose
    private ArrayList<Bank> bankList;

    @SerializedName("cAmount")
    @Expose
    private String shippingAmount;

    @SerializedName("sAmount")
    @Expose
    private String amount;

    public PaymentRequest(ArrayList<Bank> bankList, String shippingAmount, String amount) {
        this.bankList = bankList;
        this.shippingAmount = shippingAmount;
        this.amount = amount;
    }
}
