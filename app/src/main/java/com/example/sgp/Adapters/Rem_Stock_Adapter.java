package com.example.sgp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.R;

import java.util.ArrayList;

public class Rem_Stock_Adapter extends RecyclerView.Adapter<Rem_Stock_Adapter.Rem_Stock_ViewHolder> {

    ArrayList<Database_Class> RemStockData;



    //Constructor
    public Rem_Stock_Adapter(ArrayList<Database_Class> RemStockData) {
        this.RemStockData = RemStockData;
    }

    @NonNull
    @Override
    public Rem_Stock_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rem_stock_item, parent, false);
        return new Rem_Stock_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Rem_Stock_ViewHolder holder, int position) {
        String CropName = RemStockData.get(position).mCropNameValue;
        String Price = RemStockData.get(position).mPriceValue;
        double NoOfItems = Double.parseDouble(RemStockData.get(position).mQuantityValue);
        double Quantity_perItem = Double.parseDouble(RemStockData.get(position).mWeightValue);

        holder.CropName_TextView.setText(CropName);
        holder.Price_TextView.setText(Price + "");
        holder.NoOfItems_TextView.setText(NoOfItems + "");
        holder.Quantity_perItem_TextView.setText(Quantity_perItem + "kg");
        holder.TotalQuantity_TextView.setText((NoOfItems * Quantity_perItem) + "kg");
    }

    @Override
    public int getItemCount() {
        return RemStockData.size();
    }

    public class Rem_Stock_ViewHolder extends RecyclerView.ViewHolder {

        TextView CropName_TextView, Price_TextView, NoOfItems_TextView, Quantity_perItem_TextView, TotalQuantity_TextView;

        public Rem_Stock_ViewHolder(@NonNull View itemView) {
            super(itemView);
            CropName_TextView = itemView.findViewById(R.id.txt_crop_name);
            Price_TextView = itemView.findViewById(R.id.txt_price);
            NoOfItems_TextView = itemView.findViewById(R.id.txt_no_of_item_remStock);
            Quantity_perItem_TextView = itemView.findViewById(R.id.txt_amount_1Q);
            TotalQuantity_TextView = itemView.findViewById(R.id.txt_total_amount);
        }
    }
}

