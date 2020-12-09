package com.bkm.mobil.bkmexpress_sdk_sample.network;

import com.bkm.mobil.bkmexpress_sdk_sample.network.model.PairingRequest;
import com.bkm.mobil.bkmexpress_sdk_sample.network.model.PaymentRequest;
import com.bkm.mobil.bkmexpress_sdk_sample.network.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by bsoykal on 14/10/2016.
 */
public interface MerchantCalls {

    @POST("getTokenForInitPayment.do")
    Call<TokenResponse> requestPayment(@Body PaymentRequest paymentRequest);

    @POST("getConsumerId.do")
    Call<TokenResponse> requestPairing(@Body PairingRequest pairingRequest);
}