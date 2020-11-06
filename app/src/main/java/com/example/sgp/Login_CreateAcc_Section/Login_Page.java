package com.example.sgp.Login_CreateAcc_Section;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Dashboard;
import com.example.sgp.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Page extends AppCompatActivity {
    static String phoneNumber;
    private Spinner spinner;
    private EditText editTextMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        Button button_verify = findViewById(R.id.button);
        editTextMobile = findViewById(R.id.edt_mobile);


        button_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String Number = editTextMobile.getText().toString().trim();


                if (Number.isEmpty() || Number.length() < 10) {
                    editTextMobile.setError("Valid Mobile Number is required");
                    editTextMobile.requestFocus();
                    return;
                }

                phoneNumber = "+" + code + Number;
                Toast.makeText(Login_Page.this, phoneNumber, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_Page.this, OTP_Verification.class);
                intent.putExtra("PhoneNumber", phoneNumber);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            Intent intent = new Intent(Login_Page.this, Dashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }
}

