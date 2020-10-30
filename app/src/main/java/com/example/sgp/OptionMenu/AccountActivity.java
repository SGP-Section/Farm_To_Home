package com.example.sgp.OptionMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sgp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AccountActivity extends AppCompatActivity {
    String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

    private TextView txtNAme,txtPreArea,txtResArea,txtDob,txtUserName;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        txtDob = findViewById(R.id.txt_EditDOB);
        txtNAme = findViewById(R.id.txt_EditName);
        txtPreArea = findViewById(R.id.txt_EditPreferredArea);
        txtResArea = findViewById(R.id.txt_editResidingArea);
        txtUserName = findViewById(R.id.txt_user);

        button = findViewById(R.id.btn_Edit_Details);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db.collection("DATA").document(phone);

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            txtDob.setText(documentSnapshot.getString("dob"));
                            txtNAme.setText(documentSnapshot.getString("name"));
                            txtPreArea.setText(documentSnapshot.getString("Residing Area"));
                            txtResArea.setText(documentSnapshot.getString("Residing Area"));
                            String userNAme = "Hello , "+ documentSnapshot.getString("name");
                            txtUserName.setText(userNAme);

                        }
                        else{
                            Toast.makeText(AccountActivity.this, "Document Does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccountActivity.this, "ERROR!!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, EditActivity.class));
            }
        });
    }
}