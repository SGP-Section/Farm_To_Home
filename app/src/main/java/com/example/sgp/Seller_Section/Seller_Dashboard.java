package com.example.sgp.Seller_Section;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Adapters.Database_Class;
import com.example.sgp.Dashboard;
import com.example.sgp.MainActivity;
import com.example.sgp.R;
import com.example.sgp.Buyer_Section.buyer_dashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Seller_Dashboard extends AppCompatActivity {
    ProgressBar sold_PgBar, undel_PgBar;
    String MobileNo = null;
    TextView Sold_kg,Sold_per,Undel_kg,Undel_per,Cancel_kg, Cancel_NoOfOrder;
    Button Sold_btn, undelivered_btn, cancel_btn, To_Sell, Remaining_stocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);
        getSupportActionBar().setTitle("Seller");
        Initializing();
        SetProgressBar_Values();

        To_Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Seller_Dashboard.this, Sell_Activity.class));
            }
        });
        Sold_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Seller_Dashboard.this, Seller_Statistics_Activity.class);
                intent.putExtra("Value", "S");
                intent.putExtra("Title", "Sold");
                startActivity(intent);


            }
        });
        undelivered_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Seller_Dashboard.this, Seller_Statistics_Activity.class);
                intent.putExtra("Value", "U");
                intent.putExtra("Title", "Undelivered");

                startActivity(intent);

            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Seller_Dashboard.this, Seller_Statistics_Activity.class);
                intent.putExtra("Value", "C");
                intent.putExtra("Title", "Cancelled");
                startActivity(intent);
            }
        });



        Remaining_stocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Seller_Dashboard.this, Manage_Stock_Activity.class);
                startActivity(intent);
            }
        });
    }
    void Initializing(){
        MobileNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Remaining_stocks = findViewById(R.id.remaining_stock_btn);
        Sold_btn = findViewById(R.id.sold_button);
        undelivered_btn = findViewById(R.id.undelivered_btn);
        cancel_btn = findViewById(R.id.cancelled_button);
        To_Sell = findViewById(R.id.btn_toSell);
        sold_PgBar = (ProgressBar) findViewById(R.id.sold_progBar);
        undel_PgBar = (ProgressBar) findViewById(R.id.undel_progBar);
        Sold_kg = findViewById(R.id.sold_kg);
        Sold_per= findViewById(R.id.sold_percentage);//(00.00%)
        Undel_kg= findViewById(R.id.not_sold_kg);
        Undel_per= findViewById(R.id.not_sold_percentage);//(00.00%)
        Cancel_kg= findViewById(R.id.canelled_kg);
        Cancel_NoOfOrder = findViewById(R.id.canelled_number);
    }

    void SetProgressBar_Values() {
        FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Seller/nextOrderCounter")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int M = Integer.parseInt(snapshot.getValue().toString());
                        sold_PgBar.setMax(M);
                        undel_PgBar.setMax(M);
                        Log.d("Tag", M + ":" + snapshot.getKey());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Seller/Sold")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int counter = 0;
                        float KG=0f,Total=(float) sold_PgBar.getMax();
                        for (DataSnapshot D : dataSnapshot.getChildren()) {
                            Database_Class Obj=D.getValue(Database_Class.class);
                            KG+=Float.parseFloat(Obj.mWeightValue);
                            counter++;
                            Log.d("Tag", "S:" + counter);
                        }
                        Log.d("Tag",KG+"");
                        sold_PgBar.setProgress(counter);
                        float per =(counter*100.00f)/Total;
                        Sold_per.setText("("+String.format("%6.2f",per)+"%)");
                        Sold_kg.setText(String.format("%6.2f",KG));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Seller/Undelivered")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int counter = 0;
                        float KG=0f,Total=(float) sold_PgBar.getMax();

                        for (DataSnapshot D : dataSnapshot.getChildren()) {
                            Database_Class Obj=D.getValue(Database_Class.class);
                            KG+=Float.parseFloat(Obj.mWeightValue);
                            counter++;
                            Log.d("Tag", "U:" + counter);
                        }
                        undel_PgBar.setProgress(counter);
                        float per =(counter*100.00f)/Total;
                        Undel_per.setText("("+String.format("%3.2f",per)+"%)");
                        Undel_kg.setText(String.format("%6.2f",KG));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Seller/Cancelled")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int counter = 0;
                        float KG=0f;

                        for (DataSnapshot D : dataSnapshot.getChildren()) {
                            Database_Class Obj=D.getValue(Database_Class.class);
                            KG+=Float.parseFloat(Obj.mWeightValue);
                            counter++;
                            Log.d("Tag", "C:" + counter);
                        }
                        undel_PgBar.setProgress(counter);
                        Cancel_NoOfOrder.setText(counter+"");
                        Cancel_kg.setText(String.format("%6.2f",KG));

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
                Toast.makeText(this, "Seller Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Seller_Dashboard.class));
                break;
            case R.id.buyer_menu_item:
                Toast.makeText(this, "Buyer Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, buyer_dashboard.class));
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
                Intent intent = new Intent(Seller_Dashboard.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}