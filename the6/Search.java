package com.example.the6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.the6.Database.AppDataBase;
import com.example.the6.Database.SixDAO;

import java.util.List;

public class Search extends AppCompatActivity{
    public static final String USER_ID_KEY = "com.example.the6.UserIdKey";

    private Button buybtn;
    private RecyclerView mRecyclerview;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView mNumberfield;
    private TextView mNamefield;
    private TextView mPricefield;
    private TextView mQuantity;

    private User mUser;
    private int mUserId;

    private SixDAO mDao;

    private Products mProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getDataBase();

        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        mUser = mDao.getUserbyUserId(mUserId);

        WireupDisplay();



    }

    private void WireupDisplay(){

        mNumberfield = findViewById(R.id.textViewProductnumber);
        mNamefield = findViewById(R.id.textViewProductName);
        mPricefield = findViewById(R.id.textViewProductPrice);
        mQuantity = findViewById(R.id.textViewProductQuantity);

        mNamefield.setVisibility(View.GONE);
        mNumberfield.setVisibility(View.GONE);
        mPricefield.setVisibility(View.GONE);
        mQuantity.setVisibility(View.GONE);

        buybtn = findViewById(R.id.selectbtn);

        CreateSearchList();
        mRecyclerview.setVisibility(View.GONE);

        buybtn.setOnClickListener(v -> {

            if(mProduct == null){
                Toast.makeText(this, "Sorry no product selected", Toast.LENGTH_SHORT).show();
            }


           if(mProduct != null) {
               mRecyclerview.setVisibility(View.GONE);
               Products temp;
               temp = mDao.getProductByName(mProduct.getProductName());

               AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
               builder.setMessage("Purchase:\n" + mProduct).setPositiveButton("yes", (dialog, which) -> {
                   if (mProduct != null && mProduct.getAmount() != 0) {
                       mDao.delete(mProduct);
                       int tempamout = temp.getAmount() - 1;
                       temp.setmAmount(tempamout);
                       mDao.insert(temp); // in hindsight the backgrounds were not a good idea
                       OrderLog order = new OrderLog(mProduct.getProductName(), mUser.getUserId());
                       mDao.insert(order);

                   }

               }).setNegativeButton("No", (dialog, which) -> {

               });
               AlertDialog alertDialog = builder.create();
               alertDialog.show();
           }
        });

    }

    private void getDataBase(){
        mDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getSixDAO();
    }

    private void CreateSearchList(){
        List<Products> productList = mDao.getALLProducts();

        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(productList);

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(position -> {
            mProduct = productList.get(position);
            mRecyclerview.setVisibility(View.GONE);


            Integer number = mProduct.getProductNumber();
            Integer price = mProduct.getPrice();
            Integer quantity = mProduct.getAmount();

            mNamefield.setText(mProduct.getProductName());
            mNumberfield.setText("Product Number: " + number.toString());// this was annoying
            mPricefield.setText("Price: $" + price.toString());
            mQuantity.setText("Quantity:" + quantity.toString());

            mNamefield.setVisibility(View.VISIBLE);
            mNumberfield.setVisibility(View.VISIBLE);
            mPricefield.setVisibility(View.VISIBLE);
            mQuantity.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchitem.getActionView();

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mAdapter.getFilter().filter(newText);
            return false;
        }
    });
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mRecyclerview.setVisibility(View.VISIBLE);

        return super.onOptionsItemSelected(item);
    }

    public static Intent intentFactory(Context context, int mUserId){
        Intent intent = new Intent(context, Search.class);
        intent.putExtra(USER_ID_KEY, mUserId);

        return intent;
    }


}