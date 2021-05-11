package com.example.sgp.OptionMenu;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Dashboard;
import com.example.sgp.MainActivity;
import com.example.sgp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AccountActivity extends AppCompatActivity {
    String MobileNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

    private TextView txtNAme, txtPreArea, txtResArea, txtDob, txtUserName, txtMobile, txtAadhar;
    private ImageButton button_Edit, button_del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        txtMobile = findViewById(R.id.MobileNo);
        txtDob = findViewById(R.id.txt_EditDOB);
        txtNAme = findViewById(R.id.txt_EditName);
        txtAadhar = findViewById(R.id.txt_AadharC);
        txtPreArea = findViewById(R.id.txt_EditPreferredArea);
        txtResArea = findViewById(R.id.txt_editResidingArea);
        txtUserName = findViewById(R.id.txt_user);
        button_del=findViewById(R.id.btn_delete_acc);
        button_Edit = findViewById(R.id.btn_Edit_Details);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db.collection("DATA").document(MobileNo);

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            txtDob.setText(documentSnapshot.getString("dob"));
                            txtNAme.setText(documentSnapshot.getString("name"));
                            txtAadhar.setText(documentSnapshot.getString("AadharNumber"));
                            txtPreArea.setText(documentSnapshot.getString("Residing Area"));
                            txtResArea.setText(documentSnapshot.getString("Residing Area"));
                            String userNAme = "Hello , " + documentSnapshot.getString("name");
                            txtUserName.setText(userNAme);
                            String phno = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().substring(3);
                            txtMobile.setText("+91 " + phno);

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

        button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, EditActivity.class));
            }
        });
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AccountActivity.this);
                dialog.setContentView(R.layout.dialog_screen);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                TextView title, sub;
                title = dialog.findViewById(R.id.cancel_title);
                sub = dialog.findViewById(R.id.cancel_sub);
                title.setText("Account Deletion Confirmation");
                sub.setText("Sure You want to Delete you Account\nwith Registered Mobile No:"+MobileNo);
                final Button confirm, back;
                confirm = dialog.findViewById(R.id.btn_confirm_cancel);
                back = dialog.findViewById(R.id.btn_back);
                confirm.setText("Delete");

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore.getInstance().document("DATA/" + MobileNo).delete();
                        FirebaseFirestore.getInstance().document("Feedback/" + MobileNo).delete();
                        FirebaseDatabase.getInstance().getReference("Data/" + MobileNo).removeValue();
                        FirebaseAuth.getInstance().getCurrentUser().delete();
                        FirebaseAuth.getInstance().signOut();
                        finishAffinity();
                        Toast.makeText(AccountActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Dashboard.class));
    }
}