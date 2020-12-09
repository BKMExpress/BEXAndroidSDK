package com.bkm.mobil.bkmexpress_sdk_sample.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import com.bkm.bexandroidsdk.core.BEXStarter;
import com.bkm.bexandroidsdk.core.BEXSubmitConsumerListener;
import com.bkm.bexandroidsdk.en.Environment;
import com.bkm.mobil.bkmexpress_sdk_sample.R;
import com.bkm.mobil.bkmexpress_sdk_sample.network.RestManager;
import com.bkm.mobil.bkmexpress_sdk_sample.network.model.PairingRequest;
import com.bkm.mobil.bkmexpress_sdk_sample.network.model.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bsoykal on 14/10/2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton pairingButton = findViewById(R.id.pairing_button);
        AppCompatButton paymentButton = findViewById(R.id.payment_button);

        pairingButton.setOnClickListener(this);
        paymentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pairing_button) {
            startPairingAction();
        } else if (v.getId() == R.id.payment_button) {
            startPaymentAction();
        }
    }

    private void startPairingAction() {
        RestManager.getInstance().requestPairing(new PairingRequest()).enqueue(new Callback<TokenResponse>() {

            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                TokenResponse tokenResponse = response.body();
                if (tokenResponse == null) {
                    return;
                }
                Toast.makeText(MainActivity.this, "Token received :: " + tokenResponse.token, Toast.LENGTH_SHORT).show();

                BEXStarter.startSDKForSubmitConsumer(MainActivity.this, Environment.PREPROD, tokenResponse.token, new BEXSubmitConsumerListener() {

                    @Override
                    public void onSuccess(String s, String s1) {
                        Toast.makeText(MainActivity.this, "Pairing Completed!!! \nFirst6 :: " + s + "\nLast2 :: " + s1, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled() {
                        Toast.makeText(MainActivity.this, "Pairing Cancelled By User!!!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int errorId, String errorMsg) {
                        Toast.makeText(MainActivity.this, "Pairing failed!!! Cause :: " + errorMsg, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Token failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPaymentAction() {
        Intent paymentIntent = new Intent(MainActivity.this, Payment.class);
        startActivity(paymentIntent);
    }
}