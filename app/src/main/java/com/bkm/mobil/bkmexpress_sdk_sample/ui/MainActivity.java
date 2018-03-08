package com.bkm.mobil.bkmexpress_sdk_sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bkm.bexandroidsdk.core.BEXStarter;
import com.bkm.bexandroidsdk.core.BEXSubmitConsumerListener;
import com.bkm.bexandroidsdk.en.Environment;
import com.bkm.mobil.bkmexpress_sdk_sample.R;
import com.bkm.mobil.bkmexpress_sdk_sample.network.RestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bsoykal on 14/10/2016.
 */
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

        RestManager.getInstance().requestTestTokenForInitConsumer().enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Toast.makeText(MainActivity.this, "Token received :: " + response.body(), Toast.LENGTH_SHORT).show();

                if (isNotEmpty(response.body())) {

                    //START SUBMIT CONSUMER FOR QUICKPAY //

                    BEXStarter.startSDKForSubmitConsumer(MainActivity.this, Environment.TEST, response.body(), new BEXSubmitConsumerListener() {

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
