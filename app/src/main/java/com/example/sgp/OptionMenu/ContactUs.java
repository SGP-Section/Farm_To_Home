package com.example.sgp.OptionMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Buyer_Section.CallActivity;
import com.example.sgp.Dashboard;
import com.example.sgp.R;

public class ContactUs extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, Dashboard.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        findViewById(R.id.call_Charmi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUs.this, CallActivity.class);
                intent.putExtra("PhNoValue", "+919327286582");
                ContactUs.this.startActivity(intent);
            }
        });
        findViewById(R.id.call_Dhruv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUs.this, CallActivity.class);
                intent.putExtra("PhNoValue", "+919726434049");
                ContactUs.this.startActivity(intent);
            }
        });
        findViewById(R.id.email_Dhruv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail("dkpatel2491@gmail.com");

            }
        });
        findViewById(R.id.email_charmi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail("charmi261101@gmail.com");
            }
        });
    }

    protected void sendEmail(String Mail) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:" + Mail + "?subject=" + "&body="));
        startActivity(intent);

    }
}