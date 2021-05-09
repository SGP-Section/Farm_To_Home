package com.example.sgp.Buyer_Section;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.Adapters.Database_Class;
import com.example.sgp.Adapters.undelivered_BuyerPending_Adapter;
import com.example.sgp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Order_History extends AppCompatActivity {
    ArrayList<Database_Class> passing_Data_PendingCrop = new ArrayList<>(0);
    ArrayList<String> Key = new ArrayList<>(0);
    ArrayList<String> ID = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        getSupportActionBar().setTitle("Order History");


        final RecyclerView recyclerView = findViewById(R.id.recyclerview_orderHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String MobileNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Buyer/Delivered");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Key.clear();
                ID.clear();

                passing_Data_PendingCrop.removeAll(passing_Data_PendingCrop);
                for (DataSnapshot dsnap : snapshot.getChildren()) {
                    Key.add(dsnap.getKey());
                    if (dsnap.child("ID").exists()) {
                        String s = dsnap.child("ID").getValue().toString();
                        ID.add(s);
                        Log.d("Tag", "Key:" + s);

                    }

                    Database_Class S = dsnap.getValue(Database_Class.class);
                    int price = Integer.parseInt(S.mQuantityValue) * Integer.parseInt(S.mPriceValue);
                    S.mPriceValue = price + "";
                    passing_Data_PendingCrop.add(S); // Log.d("Tag",S.mNameValue);
                }
                if (passing_Data_PendingCrop.isEmpty()) {
                    final Dialog dialog = new Dialog(Order_History.this);
                    dialog.setContentView(R.layout.empty_dialog_screen);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                    TextView title;
                    title = dialog.findViewById(R.id.empty_title);
                    title.setText("NO DATA Found!");
                    final Button confirm, back;
                    confirm = dialog.findViewById(R.id.btn_confirm_cancel);
                    back = dialog.findViewById(R.id.btn_back_empty);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            dialog.dismiss();


                        }
                    });

                    dialog.show();
                } else {
                    recyclerView.setAdapter(new undelivered_BuyerPending_Adapter(passing_Data_PendingCrop, Key, ID, 'O', Order_History.this));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Data UnAvailible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, Buyer_Dashboard.class));
    }
}