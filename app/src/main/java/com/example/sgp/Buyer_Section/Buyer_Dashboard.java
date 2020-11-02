package com.example.sgp.Buyer_Section;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.Adapters.Database_Class;
import com.example.sgp.Adapters.undelivered_BuyerPending_Adapter;
import com.example.sgp.Dashboard;
import com.example.sgp.MainActivity;
import com.example.sgp.OptionMenu.AccountActivity;
import com.example.sgp.R;
import com.example.sgp.Seller_Section.Seller_Dashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buyer_Dashboard extends AppCompatActivity {
    ArrayList<Database_Class> passing_Data_PendingCrop = new ArrayList<Database_Class>(0);
    private Button button_ProceedToBuy;
    private TextView text_PendingOrders;

    static void LoadFirebaseData_pendingCrop() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dashboard);
        getSupportActionBar().setTitle("Buyer Section");
                button_ProceedToBuy = findViewById(R.id.btn_ProceedToBuy);
        text_PendingOrders = findViewById(R.id.txt_PendingOrderNumber);



        final RecyclerView recyclerView = findViewById(R.id.recyclerview_pendingCrops);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String MobileNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Buyer/Pending");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recyclerView.setAdapter(new undelivered_BuyerPending_Adapter(new ArrayList<Database_Class>(0), 'B',Buyer_Dashboard.this));

                for (DataSnapshot dsnap : snapshot.getChildren()) {
                    Database_Class S = dsnap.getValue(Database_Class.class);
                    passing_Data_PendingCrop.add(S); // Log.d("Tag",S.mNameValue);
                }
                text_PendingOrders.setText(passing_Data_PendingCrop.size()+"");
                recyclerView.setAdapter(new undelivered_BuyerPending_Adapter(passing_Data_PendingCrop, 'B',Buyer_Dashboard.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        button_ProceedToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Buyer_Dashboard.this, BuyerSearchActivity.class));
            }
        });

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
                startActivity(new Intent(this, Buyer_Dashboard.class));

                break;
            case R.id.home_menu_item:
                Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Dashboard.class));

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.iconmenu, menu);
        menu.removeItem(R.id.buyer_menu_item);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Dashboard.class));
    }

}