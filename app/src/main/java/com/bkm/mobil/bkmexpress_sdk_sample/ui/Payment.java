package com.bkm.mobil.bkmexpress_sdk_sample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bkm.bexandroidsdk.core.BEXPaymentListener;
import com.bkm.bexandroidsdk.core.BEXStarter;
import com.bkm.bexandroidsdk.en.Environment;
import com.bkm.bexandroidsdk.n.bexdomain.PosResult;
import com.bkm.mobil.bkmexpress_sdk_sample.R;
import com.bkm.mobil.bkmexpress_sdk_sample.network.RestManager;
import com.bkm.mobil.bkmexpress_sdk_sample.network.model.Bank;
import com.bkm.mobil.bkmexpress_sdk_sample.network.model.PaymentRequest;
import com.bkm.mobil.bkmexpress_sdk_sample.network.model.TokenResponse;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bsoykal on 14/10/2016.
 */

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final AppCompatEditText amountText = findViewById(R.id.amountText);

        AppCompatButton startPaymentButton = findViewById(R.id.startPaymentButton);
        startPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amountText.getText().toString();
                if (amount.length() != 0) {
                    ArrayList<Bank> banks = new ArrayList<>();
                    ArrayList<Integer> installments = new ArrayList<>(Collections.singletonList(1));
                    banks.add(new Bank("0010", "Bank", "", installments));

                    PaymentRequest paymentRequest = new PaymentRequest(banks, "0,00", amount);

                    RestManager.getInstance().requestPayment(paymentRequest).enqueue(new Callback<TokenResponse>() {
                        @Override
                        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                            TokenResponse tokenResponse = response.body();
                            if (tokenResponse == null) {
                                return;
                            }
                            BEXStarter.startSDKForPayment(Payment.this, Environment.PREPROD, tokenResponse.token, new BEXPaymentListener() {
                                @Override
                                public void onSuccess(PosResult posResult) {
                                    Toast.makeText(Payment.this, "Payment Completed!!!\n" +
                                            "PosMsg :: " + posResult.getPosMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onCancelled() {
                                    Toast.makeText(Payment.this, "Payment Cancelled By User!!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(int errorId, String errorMsg) {
                                    Toast.makeText(Payment.this, "Payment failed!!! Cause :: " + errorMsg + " !!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<TokenResponse> call, Throwable t) {
                            Toast.makeText(Payment.this, "Token failed", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

}
