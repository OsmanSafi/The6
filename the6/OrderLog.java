package com.example.the6;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.the6.Database.AppDataBase;

@Entity(tableName = AppDataBase.ORDERLOG_TABLE)
public class OrderLog {

    @PrimaryKey(autoGenerate = true)
    private int mOrdernumber;

    private String mProductname;
    private int mUserNumber;

    public OrderLog(String mProductname, int mUserNumber) {
        this.mProductname = mProductname;
        this.mUserNumber = mUserNumber;
    }

    public int getOrdernumber() {
        return mOrdernumber;
    }
    public void setOrdernumber(int mOrdernumber) {
        this.mOrdernumber = mOrdernumber;
    }

    public String getProductname() {
        return mProductname;
    }
    public void setProductname(String mProductname) {
        this.mProductname = mProductname;
    }

    public int getUserNumber() {
        return mUserNumber;
    }
    public void setUserNumber(int mUserNumber) {
        this.mUserNumber = mUserNumber;
    }

    @Override
    public String toString() {
        return "Order Number: " + mOrdernumber + ", " + mProductname;
    }
}
