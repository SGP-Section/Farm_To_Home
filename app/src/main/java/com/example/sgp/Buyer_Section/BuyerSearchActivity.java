package com.example.sgp.Buyer_Section;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.Adapters.BuyerSearch_adapter;
import com.example.sgp.Adapters.Database_Class;
import com.example.sgp.Dashboard;
import com.example.sgp.MainActivity;
import com.example.sgp.OptionMenu.AccountActivity;
import com.example.sgp.R;
import com.example.sgp.Seller_Section.Seller_Dashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BuyerSearchActivity extends AppCompatActivity {
      static ArrayList<Database_Class> mainCardList_Value;
      static ArrayList<String> mainCardList_Key;
    private BuyerSearch_adapter data_adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    EditText Search_edtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_search);
        getSupportActionBar().setTitle("Buyer Section");

        Search_edtxt=findViewById(R.id.buyer_search_editTXT);
        mainCardList_Value = new ArrayList<>(0);
        mainCardList_Key = new ArrayList<>(0);

        FirebaseDatabase.getInstance().getReference("Main Stock/Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsnap : snapshot.getChildren()) {
                            Database_Class S = dsnap.getValue(Database_Class.class);
                            mainCardList_Value.add(S);
                            mainCardList_Key.add(dsnap.getKey());
                            Log.d("Tag", dsnap.getKey() + "key: Search");


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        recyclerView = findViewById(R.id.recyclerview_buyerSearch);
        recyclerView.setAdapter(new BuyerSearch_adapter(BuyerSearchActivity.this, mainCardList_Value, mainCardList_Key));
        recyclerView.setLayoutManager(new LinearLayoutManager(BuyerSearchActivity.this));
        Search_edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                customFilter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview_buyerSearch);
        recyclerView.setHasFixedSize(true);
        data_adapter = new BuyerSearch_adapter(BuyerSearchActivity.this, mainCardList_Value, mainCardList_Key);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(data_adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void fillCardList() {
        mainCardList_Value = new ArrayList<>(0);
        mainCardList_Key = new ArrayList<>(0);

        FirebaseDatabase.getInstance().getReference("Main Stock/Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsnap : snapshot.getChildren()) {
                            Database_Class S = dsnap.getValue(Database_Class.class);
                            mainCardList_Value.add(S);
                            mainCardList_Key.add(dsnap.getKey());
                            Log.d("Tag", dsnap.getKey() + "key: Search");


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        recyclerView = findViewById(R.id.recyclerview_buyerSearch);
        recyclerView.setAdapter(new BuyerSearch_adapter(BuyerSearchActivity.this, mainCardList_Value, mainCardList_Key));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setHasFixedSize(true);

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

        menu.setQwertyMode(true);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.iconmenu, menu);
        return true;

       /* MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.onActionViewExpanded();
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setQueryHint("Search.....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customFilter(newText);
                return false;
            }
        });

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.onActionViewExpanded();
        searchView.setQueryHint("Search.....");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                customFilter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customFilter(s);
                //data_adapter.getFilter().filter(s);
                return true;
            }

        });
*/
    }


    void customFilter(String s) {
        ArrayList<Database_Class> temp_Value = new ArrayList<>();
        ArrayList<String> temp_Keys = new ArrayList<>();
        for (Database_Class data : mainCardList_Value) {
            if (data.mCropNameValue.toLowerCase().contains(s.toLowerCase())) {
                temp_Value.add(data);
                temp_Keys.add(mainCardList_Key.get(mainCardList_Value.indexOf(data)));
                Log.d("Tag", temp_Keys + " Temp : " + temp_Value);
            }
        }
        recyclerView = findViewById(R.id.recyclerview_buyerSearch);
        recyclerView.setHasFixedSize(true);
        data_adapter = new BuyerSearch_adapter(BuyerSearchActivity.this, temp_Value, mainCardList_Key);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(data_adapter);
        recyclerView.setLayoutManager(layoutManager);

    }
}




