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
import com.example.sgp.Adapters.sold_cancelled_Adapter;
import com.example.sgp.Adapters.undelivered_BuyerPending_Adapter;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Seller_Statistics_Activity extends AppCompatActivity {
    private static ArrayList<Database_Class> passing_Data_SellerStat;
    //String  Value=getIntent().getStringExtra("Value");
    RecyclerView recyclerView;
    String value, TITLE, MobileNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_statistics);
        value = getIntent().getStringExtra("Value");
        TITLE = getIntent().getStringExtra("Title");
        passing_Data_SellerStat = new ArrayList<Database_Class>(0);
        recyclerView = findViewById(R.id.seller_Stat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle(TITLE);
        DatabaseReference Dataref = FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Seller/" + TITLE);
        Dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                passing_Data_SellerStat.clear();
                for (DataSnapshot D : dataSnapshot.getChildren()) {
                    Database_Class Stat = D.getValue(Database_Class.class);
                    passing_Data_SellerStat.add(Stat);
                    Log.d("Tag", D.getKey());
                }
                if (!passing_Data_SellerStat.isEmpty()) {
                    switch (value.charAt(0)) {
                        case 'S':
                            recyclerView.setAdapter(new sold_cancelled_Adapter(passing_Data_SellerStat, 'S'));
                            break;
                        case 'U':
                            //getSupportActionBar().setTitle("Undelivered");
                            ArrayList<String> NULL=new ArrayList<>(0);
                            recyclerView.setAdapter(new undelivered_BuyerPending_Adapter(passing_Data_SellerStat, NULL,NULL, 'U', Seller_Statistics_Activity.this));
                            break;
                        case 'C':
                            //getSupportActionBar().setTitle("Cancelled");
                            recyclerView.setAdapter(new sold_cancelled_Adapter(passing_Data_SellerStat, 'C'));
                            break;

                    }
                } else {
                    final Dialog dialog = new Dialog(Seller_Statistics_Activity.this);
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
