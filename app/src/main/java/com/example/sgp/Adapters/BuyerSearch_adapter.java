package com.example.sgp.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.Buyer_Section.BuyActivity;
import com.example.sgp.Buyer_Section.BuyerSearchActivity;
import com.example.sgp.Buyer_Section.CallActivity;
import com.example.sgp.R;

import java.util.ArrayList;

public class BuyerSearch_adapter extends RecyclerView.Adapter<BuyerSearch_adapter.Buyer_ViewHolder>{

    ArrayList<Database_Class> BuyerCardList;
    //    ArrayList<Database_Class> BuyerCardListFull;
    ArrayList<String> Keys;
    Context context;
    Activity activity;

    public BuyerSearch_adapter(Context context, ArrayList<Database_Class> mCardList, ArrayList<String> Keys) {
        this.context = context;
        this.activity = (Activity)context;
        BuyerCardList = mCardList;
        this.Keys = Keys;
//        BuyerCardListFull = new ArrayList<>(mCardList);
    }

    @NonNull
    @Override
    public BuyerSearch_adapter.Buyer_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_card_view, parent, false);
        return new Buyer_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerSearch_adapter.Buyer_ViewHolder holder, final int position) {
        Database_Class CurrentItem = BuyerCardList.get(position);
        final String NameValue = CurrentItem.mNameValue;
        final String PhnoValue = CurrentItem.mPhnoValue;
        final String CropNameValue = CurrentItem.mCropNameValue;
        final String PriceValue = CurrentItem.mPriceValue;
        final String QuantityValue = CurrentItem.mQuantityValue;
        final String WeightValue = CurrentItem.mWeightValue;
        final String AreaValue = CurrentItem.mAreaValue;
        final String DateValue = CurrentItem.mDateValue;


        holder.SellerNameValue.setText(NameValue);
        holder.SellerPhno.setText(PhnoValue);
        holder.CropNameValue.setText(CropNameValue);
        holder.QuantityValue.setText(QuantityValue);
        holder.WeightValue.setText(WeightValue);
        holder.AreaValue.setText(AreaValue);
        holder.to_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Tobuy" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BuyActivity.class);
                intent.putExtra("Key", Keys.get(position));
                intent.putExtra("NameValue", NameValue);
                intent.putExtra("PhnoValue", PhnoValue);
                intent.putExtra("CropNameValue", CropNameValue);
                intent.putExtra("PriceValue", PriceValue);
                intent.putExtra("QuantityValue", QuantityValue);
                intent.putExtra("WeightValue", WeightValue);
                intent.putExtra("AreaValue", AreaValue);
                intent.putExtra("DateValue", DateValue);
                context.startActivity(intent);
            }
        });

        holder.call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CallActivity.class);
                intent.putExtra("PhNoValue",PhnoValue);
                context.startActivity(intent);
            }
        });

    }

    public static class Buyer_ViewHolder extends RecyclerView.ViewHolder {

        TextView SellerNameValue, SellerPhno, CropNameValue, QuantityValue, WeightValue, AreaValue;
        ImageButton to_buy,call_btn;

        public Buyer_ViewHolder(@NonNull final View itemView) {
            super(itemView);

            SellerNameValue = itemView.findViewById(R.id.txt_sellerName_value);
            SellerPhno = itemView.findViewById(R.id.txt_sellerPhno_value);
            CropNameValue = itemView.findViewById(R.id.txt_crop_value);
            QuantityValue = itemView.findViewById(R.id.txt_quantity_value);
            WeightValue = itemView.findViewById(R.id.txt_weight_value);
            AreaValue = itemView.findViewById(R.id.txt_area_value);
            to_buy = itemView.findViewById(R.id.To_Buy_btn);
            call_btn = itemView.findViewById(R.id.callButton);

        }
    }

    @Override
    public int getItemCount() {
        return BuyerCardList.size();
    }

    /*@Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                ArrayList<Database_Class> tempList = new ArrayList<>();
                if(charSequence == null || charSequence.length()==0){
                    tempList= BuyerCardListFull;
                    //tempList.addAll(BuyerCardListFull);
                }else if(charSequence.length()>0){
                    String filterPattern = charSequence.toString().toLowerCase();

                    for(Database_Class item: BuyerCardListFull){
                        if(item.mCropNameValue.toLowerCase().contains(filterPattern)){
                            tempList.add(item);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();

                filterResults.values=tempList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                BuyerCardList.clear();
                BuyerCardList.addAll( (List) filterResults.values);
                BuyerSearch_adapter.this.notifyDataSetChanged();

            }
        };
    }*/


}
