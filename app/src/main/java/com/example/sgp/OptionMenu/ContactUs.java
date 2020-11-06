package com.example.sgp.OptionMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Buyer_Section.CallActivity;
import com.example.sgp.R;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ImageButton dhruv,charmi;
        charmi=findViewById(R.id.call_Charmi);
        dhruv=findViewById(R.id.call_Dhruv);
        charmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUs.this, CallActivity.class);
                intent.putExtra("PhNoValue","+919327286582");
                ContactUs.this.startActivity(intent);
            }
        });
        dhruv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUs.this, CallActivity.class);
                intent.putExtra("PhNoValue", "+919726434049");
                ContactUs.this.startActivity(intent);
            }
        });
    }
}