package com.example.the6.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.the6.OrderLog;
import com.example.the6.Products;
import com.example.the6.User;

import java.util.List;

@Dao
public interface SixDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query(" SELECT * FROM " + AppDataBase.USER_TABLE + " ORDER BY mUsername ASC" )
    List<User> getAllUsers();

    @Query(" SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUsername = :username")
    User getUserbyUsername(String username);

    @Query( " SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserId = :userid")
    User getUserbyUserId(int userid);


    ///////////////////////////////////////////////////////////////////////////////////

    @Insert
    void insert(Products... products);

    @Update
    void update(Products... products);

    @Delete
    void delete(Products product);

    @Query(" SELECT * FROM " + AppDataBase.PRODUCT_TABLE + " ORDER BY mProductName ASC")
    List<Products> getALLProducts();

    @Query(" SELECT * FROM " + AppDataBase.PRODUCT_TABLE + " WHERE mProductName = :name")
    Products getProductByName(String name);


    //////////////////////////////////////////////////////////////////////////////////////

    @Insert
    void insert(OrderLog... orderLogs);

    @Update
    void update(OrderLog... orderLogs);

    @Delete
    void delete(OrderLog orderLog);

    @Query(" SELECT * FROM " + AppDataBase.ORDERLOG_TABLE + " ORDER BY mOrdernumber ASC")
    List<OrderLog> getAllOrderLogs();

    @Query(" SELECT * FROM " + AppDataBase.ORDERLOG_TABLE + " WHERE mOrdernumber = :ordernumber")
    OrderLog getOrderbyNumber(int ordernumber);

    @Query(" SELECT * FROM " + AppDataBase.ORDERLOG_TABLE + " WHERE mUserNumber = :usernumber")
    List<OrderLog> getOrdersByUserNumber(int usernumber);





}
