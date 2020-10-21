package com.example.sgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sgp.Adapters.BuyerSearch_adapter;
import com.example.sgp.Adapters.Database_Class;
import com.example.sgp.Adapters.undelivered_BuyerPending_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class buyer_dashboard extends AppCompatActivity {
    private Button button_ProceedToBuy;
    private TextView text_PendingOrders;
    ArrayList<Database_Class> passing_Data_PendingCrop=new ArrayList<Database_Class>(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dashboard);
        getSupportActionBar().setTitle("Buyer DashBoard");

        button_ProceedToBuy = findViewById(R.id.btn_ProceedToBuy);
        text_PendingOrders = findViewById(R.id.txt_PendingOrderNumber);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview_pendingCrops);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
     /*for (int i = 0; i < 15; i++) {
            passing_Data_PendingCrop.add(new Database_Class("Seller "+i,"987654321","Lemon","30.00","3","5","Maninagar","28/09/2020"));

        }*/
        String MobileNo=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("Data/"+MobileNo+"/Buyer/Pending");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dsnap:snapshot.getChildren()){
                    Database_Class S=dsnap.getValue(Database_Class.class);
                    passing_Data_PendingCrop.add(S);
                   // Log.d("Tag",S.mNameValue);
                }
                recyclerView.setAdapter(new undelivered_BuyerPending_Adapter(passing_Data_PendingCrop,'B'));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        button_ProceedToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCardList();

                startActivity(new Intent(buyer_dashboard.this, BuyerSearchActivity.class));
            }
        });

    }
   static void LoadFirebaseData_pendingCrop(){

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.seller_menu_item:
                Toast.makeText(this, "Seller Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Seller_Dashboard.class));
                break;
            case R.id.buyer_menu_item:
                Toast.makeText(this, "Buyer Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,buyer_dashboard.class));
                break;

            case R.id.home_menu_item:
                Toast.makeText(this, "home Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Dashboard.class));
                break;
            case R.id.account_menu_item:
                Toast.makeText(this, "account Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout_menu_item:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(buyer_dashboard.this, MainActivity.class);
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
        menu.removeItem(R.id.buyer_menu_item);
        return true;
    }
    private void fillCardList() {
        FirebaseDatabase.getInstance().getReference("Main Stock/Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsnap : snapshot.getChildren()) {
                            Database_Class S = dsnap.getValue(Database_Class.class);

                            Log.d("Tag", dsnap.getKey() + "key: Search");


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}