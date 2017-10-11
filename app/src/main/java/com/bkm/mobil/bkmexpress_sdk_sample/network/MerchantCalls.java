package com.bkm.mobil.bkmexpress_sdk_sample.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bsoykal on 14/10/2016.
 */

public interface MerchantCalls {
    /**
        Test amaçlı yazılmış servislerdir.
     */

    @GET("/ConsumerPreProdService/Service1.svc/Consumer")
    Call<String> requestTokenForInitConsumer();

    @GET("PreProdToken/Service1.svc/Amount/{amount}")
    Call<String> requestPurchasePreprodToken(@Path("amount") String amount);


    @GET("/ConsumerTestService/Service1.svc/Consumer")
    Call<String> requestTestTokenForInitConsumer();

    @GET("Token/Service1.svc/Amount/{amount}")
    Call<String> getPurchaseTestToken(@Path("amount") String amount);


}