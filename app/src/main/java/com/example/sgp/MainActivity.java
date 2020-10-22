package com.example.sgp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.net.NetworkInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar spin = (ProgressBar) findViewById(R.id.progressBar_intro);

        //Initialize connectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get active network Information
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            //when internet is inactive

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.network_alert_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button btnTryAgainNetwork = dialog.findViewById(R.id.btn_tryAgain_network);
            btnTryAgainNetwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });
            dialog.show();
        } else {

            //when internet is Active
            spin.setVisibility(View.VISIBLE);
            new CountDownTimer(2000, 500) {
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(MainActivity.this, createAccount.class));
                    finish();
                }
            }.start();

        }

    }
}
