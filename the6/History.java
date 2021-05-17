package com.example.the6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.the6.Database.AppDataBase;
import com.example.the6.Database.SixDAO;

import java.util.List;

public class History extends AppCompatActivity {
    public static final String USER_ID_KEY = "com.example.the6.UserIdKey";


    private TextView mOrderDisplay;
    private List<OrderLog> orderLogList;
    private SixDAO mDao;
    private User mUser;
    private int mUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getDataBase();

        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        mUser = mDao.getUserbyUserId(mUserId);

        mOrderDisplay = findViewById(R.id.Orderlog);

        orderLogList = mDao.getOrdersByUserNumber(mUserId);

        if(orderLogList.size() < 1){
            mOrderDisplay.setText("No Orders have been made by" + mUser.getUsername());
        }

        StringBuilder sb = new StringBuilder();
        for(OrderLog log : orderLogList){
            sb.append(log);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mOrderDisplay.setText(sb.toString());
    }

    private void getDataBase(){
        mDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getSixDAO();
    }

    public static Intent intentFactory(Context context, int mUserId){
        Intent intent = new Intent(context, History.class);
        intent.putExtra(USER_ID_KEY, mUserId);

        return intent;
    }




}