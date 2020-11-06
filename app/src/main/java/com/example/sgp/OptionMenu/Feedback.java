package com.example.sgp.OptionMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Dashboard;
import com.example.sgp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Feedback extends AppCompatActivity {
    EditText editText_sug;
    RatingBar ratingBar;
    String MobileNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Button submit = findViewById(R.id.btn_feedback_submit);
        editText_sug = findViewById(R.id.edt_feedback_suggestion);
        ratingBar = findViewById(R.id.ratingBar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DateValue = new SimpleDateFormat("dd-MM-yyyy").format(new Date());//getIntent().getStringExtra("DateValue");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Rating", ratingBar.getRating());
                hashMap.put("Time",DateValue);
                hashMap.put("Suggestion", editText_sug.getText().toString().trim());
                FirebaseFirestore.getInstance().collection("Feedback").document(MobileNo).set(hashMap);
                finish();
                startActivity(new Intent(Feedback.this, Dashboard.class));

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, Dashboard.class));

    }
}