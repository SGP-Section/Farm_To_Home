package com.example.sgp.Adapters;


public class Database_Class {
    public String mNameValue, mPhnoValue, mCropNameValue,mPriceValue, mQuantityValue, mWeightValue, mAreaValue, mDateValue;
    public Database_Class(){}

    public Database_Class(String NameValue, String BuyerPhnoValue, String CropNameValue, String PriceValue, String QuantityValue, String WeightValue, String AreaValue, String DateValue) {
        this.mNameValue = NameValue;
        this.mPhnoValue = BuyerPhnoValue;
        this.mCropNameValue = CropNameValue;
        this.mPriceValue=PriceValue;
        this.mQuantityValue = QuantityValue;
        this.mWeightValue = WeightValue;
        this.mAreaValue = AreaValue;
        this.mDateValue = DateValue;

    }



}
