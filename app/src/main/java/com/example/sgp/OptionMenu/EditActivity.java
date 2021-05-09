package com.example.sgp.OptionMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Login_CreateAcc_Section.Verhoeff;
import com.example.sgp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    boolean ret = true;
    DocumentReference dr;
    String phone;
    private EditText edtName, edtAadhar, edtDob, edtPreArea, edtResArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        edtDob = findViewById(R.id.edt_EditDOB);
        edtName = findViewById(R.id.edt_EditName);
        edtAadhar = findViewById(R.id.edt_AadharEdit);
        edtPreArea = findViewById(R.id.edt_EditPreferredArea);
        edtResArea = findViewById(R.id.edt_editResidingArea);

        ImageButton btnUpdate = findViewById(R.id.btn_Edit_Details);
// add aadhar card

        FirebaseFirestore.getInstance()
                .collection("DATA").document(phone).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            edtDob.setText(documentSnapshot.getString("dob"));
                            edtName.setText(documentSnapshot.getString("name"));
                            edtAadhar.setText(documentSnapshot.getString("AadharNumber"));
                            edtPreArea.setText(documentSnapshot.getString("Preferred Area"));
                            edtResArea.setText(documentSnapshot.getString("Residing Area"));

                        } else {
                            Toast.makeText(EditActivity.this, "Details does not exist!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditActivity.this, "ERROR!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aadharcardNumber = edtAadhar.getText().toString().trim();

                HashMap<String, Object> data = new HashMap<>();
                data.put("name", edtName.getText().toString());
                data.put("dob", edtDob.getText().toString());
                data.put("AadharNumber", aadharcardNumber);
                data.put("Residing Area", edtResArea.getText().toString());
                data.put("Preferred Area", edtPreArea.getText().toString());


                if (Verhoeff.validateVerhoeff(aadharcardNumber)) {
                    SaveToFirebase(data, phone);
                    Toast.makeText(EditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(EditActivity.this, AccountActivity.class));
                } else {
                    edtAadhar.setError("Enter Valid AadharCard Number");
                    edtAadhar.requestFocus();

                    aadharcardNumber = "";
                    return;
                }

            }
        });

    }

    protected boolean SaveToFirebase(final Map<String, Object> data, String mobileCreate) {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference dref = fb.collection("DATA").document(mobileCreate);
        dref.set(data)
                .addOnSuccessListener(EditActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ret = false;

                    }
                })
                .addOnFailureListener(EditActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditActivity.this, "Account Updation Fail:\n" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        return ret;
    }
}