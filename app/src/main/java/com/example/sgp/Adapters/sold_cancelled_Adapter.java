package com.example.sgp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.R;

import java.util.ArrayList;

public class sold_cancelled_Adapter extends RecyclerView.Adapter<sold_cancelled_Adapter.sold_ViewHolder> {
    ArrayList<Database_Class> Data=new ArrayList<Database_Class>(0);
    char section;
    public sold_cancelled_Adapter(ArrayList<Database_Class> data, char section){
        this.Data=data;
        this.section=section;
    }

    @Override
    public sold_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.sold_cancelled_item, parent, false);
            return new sold_cancelled_Adapter.sold_ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull sold_ViewHolder holder, int position) {

        String mNameValue =Data.get(position).mNameValue;
        String mPhnoValue=Data.get(position).mPhnoValue;
        String mCropNameValue=Data.get(position).mCropNameValue;
        String mPriceValue=Data.get(position).mPriceValue;
//        String mQuantityValue=Data.get(position).mQuantityValue;
//        String mWeightValue=Data.get(position).mWeightValue;
        int mQuantityValue=Integer.parseInt(Data.get(position).mQuantityValue);
        int mWeightValue=Integer.parseInt(Data.get(position).mWeightValue);
        String Total_Quantity=""+(mQuantityValue*mWeightValue);
        String mAreaValue=Data.get(position).mAreaValue;
        String mDateValue=Data.get(position).mDateValue;
        //-------------------------------------
        holder.bName.setText(mNameValue+"");
        holder.bPhno.setText(mPhnoValue+"");
        holder.cropName.setText(mCropNameValue+"");
        holder.Price.setText(mPriceValue+"");
        holder.Quantity.setText(mQuantityValue+"");
        holder.Weight_perItem.setText(mWeightValue+"");
        holder.Total_Quantity.setText(Total_Quantity+"");
        holder.Area.setText(mAreaValue+"");
        holder.Date.setText(mDateValue);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class sold_ViewHolder extends RecyclerView.ViewHolder{
        TextView bName,bPhno,cropName,Price,Quantity,Weight_perItem,Total_Quantity, Area, Date,set_stat;
        public sold_ViewHolder(@NonNull View itemView) {
            super(itemView);
            bName=itemView.findViewById(R.id.value_buyer_name);
            bPhno=itemView.findViewById(R.id.value_buyer_phno);
            cropName=itemView.findViewById(R.id.value_crop_name);
            Price=itemView.findViewById(R.id.value_price);
            Quantity=itemView.findViewById(R.id.value_no_of_item);
            Weight_perItem=itemView.findViewById(R.id.value_amount_1Q);
            Total_Quantity=itemView.findViewById(R.id.value_total_amount);
            Area =itemView.findViewById(R.id.value_area);
            Date =itemView.findViewById(R.id.value_date);
            set_stat=itemView.findViewById(R.id.txt_date_);
            if (section == 'S') {
                set_stat.setText("Date Sold:");
            } else if (section == 'C') {
                set_stat.setText("Date Cancelled:");
            }

        }
    }
}
