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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bsoykal on 14/10/2016.
 */

public class Payment extends AppCompatActivity {

    private AppCompatButton appBtnStartPayment;
    private AppCompatEditText appEdtAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        appEdtAmount = (AppCompatEditText) findViewById(R.id.appedt_amount);

        appBtnStartPayment = (AppCompatButton) findViewById(R.id.startpayment_btn);
        appBtnStartPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = appEdtAmount.getText().toString();
                if (amount != null && amount.length() != 0) {

                    RestManager.getInstance().getPurchaseTestToken(appEdtAmount.getText().toString()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            BEXStarter.startSDKForPayment(Payment.this, Environment.TEST, response.body(), new BEXPaymentListener() {
                                @Override
                                public void onSuccess(PosResult posResult) {
                                    Toast.makeText(Payment.this, "Ödeme Başarılı !!\n" +
                                            "PosMsg :: "+posResult.getPosMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onCancelled() {
                                    Toast.makeText(Payment.this, "Kullanıcı ödemeyi iptal etti !!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(int errorId, String errorMsg) {
                                    Toast.makeText(Payment.this, "Hata :: " + errorMsg + " !!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(Payment.this, "Hata :: Token Alınamadı!!", Toast.LENGTH_SHORT).show();
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
