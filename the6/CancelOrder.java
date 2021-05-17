package com.example.the6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.the6.Database.AppDataBase;
import com.example.the6.Database.SixDAO;

import java.util.List;

public class CancelOrder extends AppCompatActivity {
    public static final String USER_ID_KEY = "com.example.the6.USER_ID_KEY";


    private RecyclerView mRecyclerview;
    private CancelAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SixDAO mDao;

    private int mUserId;
    private User mUser;

    private OrderLog mOrderlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        getDataBase();
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        mUser = mDao.getUserbyUserId(mUserId);

        CreateOrderList();

    }

    public static Intent intentFactory(Context context, int mUserId){
        Intent intent = new Intent(context, CancelOrder.class);
        intent.putExtra(USER_ID_KEY, mUserId);

        return intent;
    }

    private void getDataBase(){
        mDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getSixDAO();
    }

    private void CreateOrderList(){
        List<OrderLog> orderLogList = mDao.getOrdersByUserNumber(mUser.getUserId());

        mRecyclerview = findViewById(R.id.recyclerviewCancelOrder);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CancelAdapter(orderLogList);

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> {
            mOrderlog = orderLogList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(CancelOrder.this);
            builder.setMessage("Are you sure you want to Cancel this Order\n" + mOrderlog)
            .setPositiveButton("Yes", (dialog, which)
                    -> mDao.delete(mOrderlog))
                    .setNegativeButton("No", (dialog, which)
                            -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });



    }



}