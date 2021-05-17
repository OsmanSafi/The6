package com.example.the6.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.the6.OrderLog;
import com.example.the6.Products;
import com.example.the6.User;


@Database(entities = {User.class, Products.class, OrderLog.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{
    public static final String DB_NAME = "SIX_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";
    public static final String ORDERLOG_TABLE= "ORDERLOG_TABLE";

    public abstract SixDAO getSixDAO();

}

