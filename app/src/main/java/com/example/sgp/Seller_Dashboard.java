package com.example.sgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Seller_Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);
        //getSupportActionBar().setTitle("Seller DashBoard");

        Button sold_btn, undelivered_btn, cancel_btn, To_Sell, Remaining_stocks;
        Remaining_stocks = findViewById(R.id.remaining_stock_btn);
        sold_btn = findViewById(R.id.sold_button);
        undelivered_btn=findViewById(R.id.undelivered_btn);
        cancel_btn=findViewById(R.id.cancelled_button);
        To_Sell = findViewById(R.id.btn_toSell);

        To_Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Seller_Dashboard.this, Sell_Activity.class));
            }
        });
        sold_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Seller_Dashboard.this, Seller_Statistics_Activity.class);
                intent.putExtra("Value","S");
                intent.putExtra("Title","Sold");
                startActivity(intent);


            }
        });
        undelivered_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Seller_Dashboard.this, Seller_Statistics_Activity.class);
                intent.putExtra("Value","U");
                intent.putExtra("Title","Undelivered");

                startActivity(intent);

            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Seller_Dashboard.this, Seller_Statistics_Activity.class);
                intent.putExtra("Value","C");
                intent.putExtra("Title","Cancelled");

                startActivity(intent);

            }
        });

//        To_Sell=findViewById();


        Remaining_stocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Seller_Dashboard.this, Manage_Stock_Activity.class);
                startActivity(intent);
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
                Intent intent = new Intent(Seller_Dashboard.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }



}