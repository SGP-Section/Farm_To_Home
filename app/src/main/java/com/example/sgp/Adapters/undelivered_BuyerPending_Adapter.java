package com.example.sgp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.Buyer_Section.Buyer_Dashboard;
import com.example.sgp.R;

import java.util.ArrayList;

public class undelivered_BuyerPending_Adapter extends RecyclerView.Adapter<undelivered_BuyerPending_Adapter.undelivered_ViewHolder> {
    ArrayList<Database_Class> Data = new ArrayList<Database_Class>(0);
    char decision;
    Context context;

    public undelivered_BuyerPending_Adapter(ArrayList<Database_Class> data, char decision, Context context) {
        this.decision = decision;
        this.Data = data;
        this.context=context;
    }

    @Override
    public undelivered_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.undeliverd_buyerpending_item, parent, false);
        return new undelivered_BuyerPending_Adapter.undelivered_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull undelivered_ViewHolder holder, int position) {
        if (decision == 'B') {
            holder.name_txt.setText("Seller Name:");
            holder.phno_txt.setText("Seller Phone No:");
        }
        else {
            holder.CANCEL.setVisibility(View.INVISIBLE);
        }
        String mNameValue =Data.get(position).mNameValue;
        String mPhnoValue=Data.get(position).mPhnoValue;
        String mCropNameValue=Data.get(position).mCropNameValue;
        String mPriceValue=Data.get(position).mPriceValue;
        int mQuantityValue = Integer.parseInt(Data.get(position).mQuantityValue);
        double mWeightValue = Double.parseDouble(Data.get(position).mWeightValue);
        String Total_Quantity = "" + (mQuantityValue * mWeightValue);
        String mAreaValue=Data.get(position).mAreaValue;
        //-------------------------------------
        holder.bName.setText(mNameValue);
        holder.bPhno.setText(mPhnoValue);
        holder.cropName.setText(mCropNameValue);
        holder.Price.setText(mPriceValue);
        holder.Quantity.setText(mQuantityValue+"");
        holder.Weight_perItem.setText(mWeightValue+"");
        holder.Total_Quantity.setText(Total_Quantity+"");
        holder.Area.setText(mAreaValue);
        holder.CANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.buyer_cancel_dialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                Button confirm,back;
                confirm=dialog.findViewById(R.id.btn_confirm_cancel);
                back=dialog.findViewById(R.id.btn_back);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"Delete",Toast.LENGTH_SHORT).show();
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"Back",Toast.LENGTH_SHORT).show();

                    }
                });



                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class undelivered_ViewHolder extends RecyclerView.ViewHolder {
        TextView bName, bPhno, cropName, Price, Quantity, Weight_perItem, Total_Quantity, Area, name_txt, phno_txt;
        Button CANCEL;
        public undelivered_ViewHolder(@NonNull View itemView) {
            super(itemView);
            bName = itemView.findViewById(R.id.value_buyer_name_undel);
            bPhno = itemView.findViewById(R.id.value_buyer_phno_undel);
            cropName = itemView.findViewById(R.id.value_crop_name_undel);
            Price = itemView.findViewById(R.id.value_price_undel);
            Quantity = itemView.findViewById(R.id.value_no_of_item_undel);
            Weight_perItem = itemView.findViewById(R.id.value_amount_1Q_undel);
            Total_Quantity = itemView.findViewById(R.id.value_total_amount_undel);
            Area = itemView.findViewById(R.id.value_area_undel);
            name_txt = itemView.findViewById(R.id.txt_buyer_name);
            phno_txt = itemView.findViewById(R.id.txt_buyer_phno);

            CANCEL=itemView.findViewById(R.id.btn_cancel_undeli_pending);



        }
    }
}