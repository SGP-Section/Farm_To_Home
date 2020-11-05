package com.example.sgp.Seller_Section;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.sgp.Adapters.Rem_Stock_Adapter;
import com.example.sgp.Buyer_Section.Buyer_Dashboard;
import com.example.sgp.Dashboard;
import com.example.sgp.MainActivity;
import com.example.sgp.OptionMenu.AccountActivity;
import com.example.sgp.OptionMenu.ContactUs;
import com.example.sgp.OptionMenu.Feedback;
import com.example.sgp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Manage_Stock_Activity extends AppCompatActivity {
    final String MobileNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
    ArrayList<Database_Class> RemStockData = new ArrayList<>(0);
    ArrayList<String> Key = new ArrayList<>(0);
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_stock);
        recyclerView = findViewById(R.id.remaining_stock_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RemStockData = new ArrayList<>(0);
        getSupportActionBar().setTitle("Manage Stocks");

        FirebaseDatabase.getInstance().getReference("Main Stock/Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        RemStockData.clear();
                        Key.clear();
                        for (DataSnapshot dsnap : snapshot.getChildren()) {
                            Database_Class D = dsnap.getValue(Database_Class.class);
                            if (MobileNo.compareTo(D.mPhnoValue) == 0) {
                                Key.add(dsnap.getKey());
                                RemStockData.add(D);
                                Log.d("Tag", D.mPhnoValue + dsnap.getValue());

                            }
                        }
                        if(!RemStockData.isEmpty())
                        recyclerView.setAdapter(new Rem_Stock_Adapter(RemStockData, Key, Manage_Stock_Activity.this));
                        else {
                            final Dialog dialog = new Dialog(Manage_Stock_Activity.this);
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
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.iconmenu, menu);
        menu.removeItem(R.id.seller_menu_item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.seller_menu_item:
                startActivity(new Intent(this, Seller_Dashboard.class));
                break;
            case R.id.buyer_menu_item:
                startActivity(new Intent(this, Buyer_Dashboard.class));
                break;
            case R.id.home_menu_item:
                startActivity(new Intent(this, Dashboard.class));
                break;
            case R.id.ContactUs_menu_item:
                startActivity(new Intent(this, ContactUs.class));
                break;
            case R.id.feedback_menu_item:
                startActivity(new Intent(this, Feedback.class));
                break;
            case R.id.account_menu_item:
                Intent intent1 = new Intent(this, AccountActivity.class);
                startActivity(intent1);
                break;
            case R.id.logout_menu_item:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}