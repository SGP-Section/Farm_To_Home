package com.example.sgp.OptionMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Dashboard;
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
    private EditText edtName, edtDob, edtPreArea, edtResArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        edtDob = findViewById(R.id.edt_EditDOB);
        edtName = findViewById(R.id.edt_EditName);
        edtPreArea = findViewById(R.id.edt_EditPreferredArea);
        edtResArea = findViewById(R.id.edt_editResidingArea);
        Button btnUpdate = findViewById(R.id.btn_Edit_Details);


        FirebaseFirestore.getInstance()
                .collection("DATA").document(phone).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            edtDob.setText(documentSnapshot.getString("dob"));
                            edtName.setText(documentSnapshot.getString("name"));
                            edtPreArea.setText(documentSnapshot.getString("Residing Area"));
                            edtResArea.setText(documentSnapshot.getString("Residing Area"));

                        } else {
                            Toast.makeText(EditActivity.this, "Document does not exist!!", Toast.LENGTH_SHORT).show();
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

                HashMap<String, Object> data = new HashMap<>();
                data.put("name", edtName.getText().toString());
                data.put("dob", edtDob.getText().toString());
                data.put("Residing Area", edtResArea.getText().toString());
                data.put("Preferred Area", edtPreArea.getText().toString());


                boolean C1 = SaveToFirebase(data, phone);
                if (C1) {
                    finish();
                    startActivity(new Intent(EditActivity.this, Dashboard.class));
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
                        Toast.makeText(EditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        ret = false;

                    }
                })
                .addOnFailureListener(EditActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditActivity.this, "Account Creation Fail:" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        return ret;
    }
}