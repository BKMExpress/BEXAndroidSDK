package com.bkm.mobil.bkmexpress_sdk_sample.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bsoykal on 26/08/16.
 */
public class RestManager {

private static MerchantCalls instance;


    public static MerchantCalls getInstance(){
        if(instance==null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://31.145.171.116:8081")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new OkHttpClient().newBuilder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS).build())
                    .build();

            instance = retrofit.create(MerchantCalls.class);
        }

        return instance;
    }



}
