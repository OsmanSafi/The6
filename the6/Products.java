package com.example.the6;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.the6.Database.AppDataBase;

@Entity(tableName = AppDataBase.PRODUCT_TABLE)
public class Products {

    @PrimaryKey(autoGenerate = true)
    private int mProductNumber;

    private String mProductName;
    private int mAmount;
    private int mPrice;


    public Products(String mProductName, int mAmount, int mPrice) {
        this.mProductName = mProductName;
        this.mAmount = mAmount;
        this.mPrice = mPrice;


    }


    public int getProductNumber() {
        return mProductNumber;
    }
    public void setProductNumber(int ProductNumber) {
        this.mProductNumber = ProductNumber;
    }

    public String getProductName() {
        return mProductName;
    }
    public void setProductName(String ProductName) {
        this.mProductName = ProductName;
    }

    public int getAmount() {
        return mAmount;
    }
    public void setmAmount(int Amount) {
        this.mAmount = Amount;
    }

    @Override
    public String toString() {
        return "[Product Number: " + mProductNumber + ", Name: " + mProductName + ", Price: " + mPrice + "]";
    }


    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }


}
