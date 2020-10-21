package com.example.sgp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Adapters.Database_Class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyActivity extends AppCompatActivity {

    double double_price, double_weight, double_total_price;
    int quantity, total_quantity;
    TextView txt_name, txt_crop, txt_weight, txt_qua, txt_price, txt_totalPrice, txt_area;
    ImageButton increaseQua, decreaseQua;
    Button buy_Btn,btnCall;
    String MobileNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
    int OrderNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        final String Key = getIntent().getStringExtra("Key");
        final String NameValue = getIntent().getStringExtra("NameValue");
        final String PhnoValue = getIntent().getStringExtra("PhnoValue");
        final String CropNameValue = getIntent().getStringExtra("CropNameValue");
        final String PriceValue = getIntent().getStringExtra("PriceValue");
        final String MaxQuantityValue = getIntent().getStringExtra("QuantityValue");
        final String WeightValue = getIntent().getStringExtra("WeightValue");
        final String AreaValue = getIntent().getStringExtra("AreaValue");
        final String DateValue = new SimpleDateFormat("dd-MM-yyyy").format(new Date());//getIntent().getStringExtra("DateValue");

        double_price = Double.parseDouble(PriceValue);
        total_quantity = Integer.parseInt(MaxQuantityValue);
        double_weight = Double.parseDouble(WeightValue);
        double_total_price = 0.0;
        quantity = 0;

        SetIDs();
        GetOrderNumber();

        txt_name.setText(NameValue);
        txt_crop.setText(CropNameValue);
        txt_weight.setText(WeightValue);
        txt_price.setText(PriceValue);
        txt_area.setText(AreaValue);
//        quantity = Integer.parseInt(txt_qua.getText().toString());
        double_total_price = quantity * double_price;

        txt_totalPrice.setText(double_total_price + "");
        increaseQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.parseInt(txt_qua.getText().toString());
                if (quantity <= total_quantity)
                    quantity++;
                else
                    Toast.makeText(BuyActivity.this, quantity + " is Max Quantity Availible", Toast.LENGTH_SHORT).show();
                if (total_quantity == 0) {
                    Toast.makeText(BuyActivity.this, "No Stock Is Availible", Toast.LENGTH_SHORT).show();
                }
                double_total_price = quantity * double_price;
                txt_qua.setText(quantity + "");
                txt_totalPrice.setText(double_total_price + "");


            }
        });
        decreaseQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.parseInt(txt_qua.getText().toString());
                if (quantity > 0)
                    quantity--;
                else
                    Toast.makeText(BuyActivity.this, "Quantity must be Greater Than 0", Toast.LENGTH_SHORT).show();


                double_total_price = (quantity * double_price);
                txt_qua.setText(quantity + "");
                txt_totalPrice.setText(double_total_price + "");


            }
        });

        buy_Btn = findViewById(R.id.set_buy_btn);

        buy_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String QuantityValue = txt_qua.getText().toString();
                int Q = Integer.parseInt(QuantityValue);
                if (total_quantity <= 0)
                    Toast.makeText(BuyActivity.this, "Entered Quantity is Not Available", Toast.LENGTH_SHORT).show();
                else {
                    GetOrderNumber();
                    SaveinRealTime(new Database_Class(NameValue, PhnoValue, CropNameValue, PriceValue, QuantityValue, WeightValue, AreaValue, DateValue));
                    total_quantity -= Q;
                    ChangeinMainStocks(Key, total_quantity);
                    RealTimeCleanUp(total_quantity, Key);
                    startActivity(new Intent(BuyActivity.this, BuyerSearchActivity.class));

                }

            }
        });

    }

    void RealTimeCleanUp(int Tquantity, String key) {
        if (Tquantity == 0) {
            FirebaseDatabase.getInstance().getReference("Main Stock/Orders/" + key).removeValue();
            Toast.makeText(BuyActivity.this, "Stock ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BuyActivity.this, BuyerSearchActivity.class));

        }
    }

    void ChangeinMainStocks(String key, int Q) {
        FirebaseDatabase.getInstance().getReference("Main Stock/Orders/" + key + "/mQuantityValue").setValue(Q + "");
    }

    private void SaveinRealTime(Database_Class D) {
        FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Buyer/Pending/" + OrderNo).setValue(D);
        FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Buyer/nextOrderCounterB").setValue(String.valueOf(OrderNo + 1));
    }

    private void GetOrderNumber() {
        FirebaseDatabase.getInstance().getReference("Data/" + MobileNo + "/Buyer/nextOrderCounterB")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        OrderNo = Integer.parseInt(snapshot.getValue().toString());
                        Log.d("Tag", OrderNo + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    void SetIDs() {
        txt_name = findViewById(R.id.set_buy_sellerName);
        txt_crop = findViewById(R.id.set_buy_crop);
        txt_weight = findViewById(R.id.set_buy_weight);
        increaseQua = findViewById(R.id.set_buy_inc);
        txt_qua = findViewById(R.id.set_buy_quantity);
        decreaseQua = findViewById(R.id.set_buy_dec);
        txt_price = findViewById(R.id.set_buy_price);
        txt_totalPrice = findViewById(R.id.set_buy_totalprice);
        txt_area = findViewById(R.id.set_buy_area);
        buy_Btn = findViewById(R.id.set_buy_btn);
    }

}