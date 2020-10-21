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
import android.widget.Toast;

import com.example.sgp.Adapters.Database_Class;
import com.example.sgp.Adapters.sold_cancelled_Adapter;
import com.example.sgp.Adapters.undelivered_BuyerPending_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Seller_Statistics_Activity extends AppCompatActivity {
    //String  Value=getIntent().getStringExtra("Value");
    RecyclerView recyclerView;
   private static ArrayList<Database_Class> passing_Data_SellerStat;
    String value;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_statistics);
         value=getIntent().getStringExtra("Value");
         title=getIntent().getStringExtra("Title");
        passing_Data_SellerStat=new ArrayList<Database_Class>(0);
        recyclerView= findViewById(R.id.seller_Stat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle(title);
        String MobileNo=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference Dataref= FirebaseDatabase.getInstance().getReference("Data/"+MobileNo+"/Seller/"+title);
        Dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot D:dataSnapshot.getChildren()) {
                    Database_Class Stat=D.getValue(Database_Class.class);
                    passing_Data_SellerStat.add(Stat);
                    Log.d("Tag",D.getKey());
                    switch (value.charAt(0)){
                        case 'S':
                            recyclerView.setAdapter(new sold_cancelled_Adapter(passing_Data_SellerStat,'S'));
                            break;
                        case 'U':
                            //getSupportActionBar().setTitle("Undelivered");
                            recyclerView.setAdapter(new undelivered_BuyerPending_Adapter(passing_Data_SellerStat,'U'));
                            break;
                        case 'C':
                            //getSupportActionBar().setTitle("Cancelled");
                            recyclerView.setAdapter(new sold_cancelled_Adapter(passing_Data_SellerStat,'C'));
                            break;

                    }
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
                Toast.makeText(this, "Seller Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Seller_Dashboard.class));
                break;
            case R.id.buyer_menu_item:
                Toast.makeText(this, "Buyer Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,buyer_dashboard.class));
                break;

            case R.id.home_menu_item:
                Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Dashboard.class));
                break;
            case R.id.account_menu_item:
                Toast.makeText(this, "account Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout_menu_item:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Seller_Statistics_Activity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
