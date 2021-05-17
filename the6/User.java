package com.example.the6;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.the6.Database.AppDataBase;

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUsername;
    private String mPassword;



    public User(String mUsername, String mPassword) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;

    }



    public String getUsername() {
        return mUsername;
    }
    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public int getUserId() {
        return mUserId;
    }
    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }


    @Override
    public String toString() {
        return mUsername;
    }
}
