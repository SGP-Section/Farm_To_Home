package com.example.sgp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Rem_Stock_Adapter extends RecyclerView.Adapter<Rem_Stock_Adapter.Rem_Stock_ViewHolder> {

    ArrayList<Database_Class> RemStockData;
    ArrayList<String> Key;
    Context context;
    String MobileNo= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();


    //Constructor
    public Rem_Stock_Adapter(ArrayList<Database_Class> RemStockData, ArrayList<String> key,Context context) {
        this.RemStockData = RemStockData;
        this.Key=key;
        this.context=context;
    }

    @NonNull
    @Override
    public Rem_Stock_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rem_stock_item, parent, false);
        return new Rem_Stock_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Rem_Stock_ViewHolder holder, final int position) {
        String CropName = RemStockData.get(position).mCropNameValue;
        String Price = RemStockData.get(position).mPriceValue;
        int NoOfItems = Integer.parseInt(RemStockData.get(position).mQuantityValue);
        double Quantity_perItem = Double.parseDouble(RemStockData.get(position).mWeightValue);

        holder.CropName_TextView.setText(CropName);
        holder.Price_TextView.setText(Price + "");
        holder.NoOfItems_TextView.setText(NoOfItems + "");
        holder.Quantity_perItem_TextView.setText(Quantity_perItem + "kg");
        holder.TotalQuantity_TextView.setText((NoOfItems * Quantity_perItem) + "kg");

        holder.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.cancel_dialog);
                TextView title=dialog.findViewById(R.id.cancel_title),sub=dialog.findViewById(R.id.cancel_sub);
                title.setText("Cancel Stock");
                sub.setText("Press Confirm to Cancel the Stock");
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                final Button confirm, back;
                confirm = dialog.findViewById(R.id.btn_confirm_cancel);
                back = dialog.findViewById(R.id.btn_back);
                Log.d("Tag", Key.get(position));

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference("Main Stock/Orders/" + Key.get(position)).removeValue();
                        dialog.dismiss();
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Order Deleted", Toast.LENGTH_SHORT).show();

                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return RemStockData.size();
    }

    public class Rem_Stock_ViewHolder extends RecyclerView.ViewHolder {

        TextView CropName_TextView, Price_TextView, NoOfItems_TextView, Quantity_perItem_TextView, TotalQuantity_TextView;
        Button Cancel;
        public Rem_Stock_ViewHolder(@NonNull View itemView) {
            super(itemView);
            CropName_TextView = itemView.findViewById(R.id.txt_crop_name);
            Price_TextView = itemView.findViewById(R.id.txt_price);
            NoOfItems_TextView = itemView.findViewById(R.id.txt_no_of_item_remStock);
            Quantity_perItem_TextView = itemView.findViewById(R.id.txt_amount_1Q);
            TotalQuantity_TextView = itemView.findViewById(R.id.txt_total_amount);
            Cancel=itemView.findViewById(R.id.cancel_rem_stock);
        }
    }
}

