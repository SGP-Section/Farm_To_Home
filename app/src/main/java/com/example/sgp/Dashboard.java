package com.example.sgp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Buyer_Section.Buyer_Dashboard;
import com.example.sgp.Login_CreateAcc_Section.Login_Page;
import com.example.sgp.OptionMenu.AccountActivity;
import com.example.sgp.OptionMenu.ContactUs;
import com.example.sgp.OptionMenu.Feedback;
import com.example.sgp.Seller_Section.Seller_Dashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.sgp.R.id;
import static com.example.sgp.R.layout;

public class Dashboard extends AppCompatActivity {

    private Button button_buyer,button_seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard");
        if(FirebaseAuth.getInstance().getUid()==null){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Dashboard.this, Login_Page.class));
            finish();
        }
        button_buyer = findViewById(R.id.btn_buyer);
        button_seller = findViewById(R.id.btn_seller);
        LoadData();




        button_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Dashboard.this, Buyer_Dashboard.class) );
            }
        });

        button_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, Seller_Dashboard.class));
            }
        });
    }

    private void LoadData(){
        String MobileNo=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("Data/"+MobileNo);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsnap:snapshot.getChildren())
                   Log.d("Tag",dsnap.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dref= FirebaseDatabase.getInstance().getReference("Main Stock");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsnap:snapshot.getChildren())
                    Log.d("Tag",dsnap.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
            case id.logout_menu_item:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.iconmenu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
