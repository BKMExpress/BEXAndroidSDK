package com.bkm.mobil.bkmexpress_sdk_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bkm.bexandroidsdk.core.BEXStarter;
import com.bkm.bexandroidsdk.core.BEXSubmitConsumerListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton appBtnCardSync;
    private AppCompatButton appBtnPymnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBtnCardSync = (AppCompatButton) findViewById(R.id.eslesme_btn);
        appBtnPymnt = (AppCompatButton) findViewById(R.id.payment_btn);

        appBtnCardSync.setOnClickListener(this);
        appBtnPymnt.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.eslesme_btn) {
            startCardSyncAction();
        } else if (v.getId() == R.id.payment_btn) {
            startPaymentAction();
        }
    }

    private void startCardSyncAction() {

        RestManager.getInstance().requestTokenForInitConsumer().enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Toast.makeText(MainActivity.this, "Token received :: " + response.body(), Toast.LENGTH_SHORT).show();

                if (isNotEmpty(response.body())) {

                    //START SUBMIT CONSUMER FOR QUICKPAY //

                    BEXStarter.startSDKForSubmitConsumer(MainActivity.this, response.body(), getString(R.string.dummyApiKey), new BEXSubmitConsumerListener() {

                        @Override
                        public void onSuccess() { //SUBMIT WAS SUCCESSFULL
                            Toast.makeText(MainActivity.this, "Sync Completed!!!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled() { //SUBMIT WAS CANCELED BY USER
                            Toast.makeText(MainActivity.this, "Sync Cancelled By User!!!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int errorId, String errorMsg) { //SUBMIT WAS INTERRUPTED BY FAILURE
                            Toast.makeText(MainActivity.this, "Sync failed!!! Cause :: " + errorMsg, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Token failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPaymentAction() {
        Intent paymentIntent = new Intent(MainActivity.this, Payment.class);
        startActivity(paymentIntent);
    }


    private boolean isNotEmpty(String test) {
        return test != null && !test.equals("");
    }
}
