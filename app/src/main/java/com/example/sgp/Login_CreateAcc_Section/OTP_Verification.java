package com.example.sgp.Login_CreateAcc_Section;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Dashboard;
import com.example.sgp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class OTP_Verification extends AppCompatActivity {

    private String verifyCode,phoneNumber;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneNumber = getIntent().getStringExtra("PhoneNumber");
        btnSignIn = (Button) findViewById(R.id.buttonSignIn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.edt_otp);


        sendVerificationCode(phoneNumber);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = editText.getText().toString().trim();

                if(code.isEmpty() || code.length()<6){
                    editText.setError(code);
                    editText.requestFocus();
                    return;
                }

                verifyManuallyVerifyCode(code);
            }
        });
    }

    private void verifyManuallyVerifyCode(String code){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode, code);

        signInWithCredential(credential);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verifyCode = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode().toString();

            if(!code.isEmpty()){
                editText.setText(code);
                verifyManuallyVerifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTP_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void sendVerificationCode(String number){

        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,2, TimeUnit.MINUTES, TaskExecutors.MAIN_THREAD, mCallback);

    }

    private void signInWithCredential(PhoneAuthCredential credential) {


        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseFirestore fb=FirebaseFirestore.getInstance();
                            fb.collection("DATA").document(phoneNumber)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d("=======>", "Document exists!&& asking decision");


                                            Dialog dialog = new Dialog(OTP_Verification.this);
                                            dialog.setContentView(R.layout.login_dialog);
                                            dialog.setCanceledOnTouchOutside(false);
                                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                                            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                            Button Login_dialog= dialog.findViewById(R.id.btn_login_acc);
                                            Button CreateAcc_dialog=dialog.findViewById(R.id.btn_create_acc);
                                            Login_dialog.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(OTP_Verification.this, Dashboard.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                intent.putExtra("PhoneNumber",phoneNumber);
                                                    startActivity(intent);
                                                }
                                            });
                                            CreateAcc_dialog.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(OTP_Verification.this, ApplicantDetails.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    intent.putExtra("PhoneNumber",phoneNumber);
                                                    startActivity(intent);
                                                }
                                            });
                                            dialog.show();


                                        } else {
                                            Intent intent = new Intent(OTP_Verification.this, ApplicantDetails.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtra("PhoneNumber",phoneNumber);
                                            startActivity(intent);
                                            Log.d("=======>", "Document does not exist!&& Login");
                                        }
                                    } else {
                                        Log.d("=======>", "Failed with: ", task.getException());
                                    }
                                }
                            });



                        }else{
                            Toast.makeText(OTP_Verification.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}
