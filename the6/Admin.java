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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.the6.Database.AppDataBase;
import com.example.the6.Database.SixDAO;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity implements Dialog.dialogListner {
    public static final String USER_ID_KEY= "com.example.the6.UserKEY";

    private RecyclerView mRecyclerview;
    private AdminAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button addProductbtn;
    private Button deleteProductbtn;
    private Button deleteUserbtn;


    private User mUser;
    private int mUserId;

    private User mSelectedUser;
    private Products mSelectedProduct;


    private String searchItem;

    private List<User> userList;
    private List<Products> productsList;

    private SixDAO mDao;

           @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getDataBase();

        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        mUser = mDao.getUserbyUserId(mUserId);
        CreateSearchList();
        WireUpDisplay();
        SetupAddProductButton();
        SetupDeleteProductbtn();
        SetupDeleteUserbtn();

    }


    private void WireUpDisplay(){

        addProductbtn = findViewById(R.id.addproductbtn);
        deleteProductbtn = findViewById(R.id.deleteproductbtn);
        deleteUserbtn = findViewById(R.id.removeUserbtn);
        mRecyclerview.setVisibility(View.GONE);



    }

    private void getDataBase(){
        mDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getSixDAO();
    }

    public static Intent intentFactory(Context context, int mUserId){
        Intent intent = new Intent(context, Admin.class);
        intent.putExtra(USER_ID_KEY, mUserId);

        return intent;
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

    private void CreateSearchList(){
       userList = mDao.getAllUsers();
       productsList = mDao.getALLProducts();
       List<String> searchList = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            searchList.add(userList.get(i).getUsername());

        }

        for (int i = userList.size(); i < userList.size() + productsList.size(); i++) {
            for (int j = 0; j < productsList.size(); j++) {
                if(!searchList.contains(productsList.get(j).getProductName())) {
                    searchList.add(productsList.get(j).getProductName());

                }
            }

        }

        mRecyclerview = findViewById(R.id.adminRecyclerView);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdminAdapter(searchList);

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(position -> {
            searchItem = searchList.get(position);
            mRecyclerview.setVisibility(View.GONE);

            try{
                mSelectedUser = mDao.getUserbyUsername(searchItem);
            }catch(NullPointerException e){
                mSelectedProduct = mDao.getProductByName(searchItem);
            }

            try{
                mSelectedProduct = mDao.getProductByName(searchItem);

            }catch(NullPointerException e){
                mSelectedUser = mDao.getUserbyUsername(searchItem);
            }



        });


    }

    private void SetupAddProductButton(){
        addProductbtn = findViewById(R.id.addproductbtn);

        addProductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog();
                dialog.show(getSupportFragmentManager(), "");

            }
        });



    }

    @Override
    public void applyText(String name, String price, String Quantity) {
        int priceAsint = Integer.parseInt(price);
        int quantityAsint = Integer.parseInt(Quantity);
        Products products = new Products(name, quantityAsint, priceAsint);

        mDao.insert(products);

    }

    private void SetupDeleteProductbtn(){
        deleteProductbtn.setOnClickListener(v -> {

            if(mSelectedProduct == null){
                Toast.makeText(getApplicationContext(), "No product selected", Toast.LENGTH_SHORT).show();

            }else if(mSelectedProduct != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
                builder.setMessage("Are you sure you want to delete:\n" + mSelectedProduct)
                        .setPositiveButton("Yes", ((dialog, which) ->
                                mDao.delete(mSelectedProduct))

                        ).setNegativeButton("No", ((dialog, which) -> {
                }));
                AlertDialog dialog = builder.create();
                dialog.show();

            }



        });
    }

    private void SetupDeleteUserbtn(){
               deleteUserbtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(mSelectedUser == null){
                           Toast.makeText(getApplicationContext(), "No user has been selected", Toast.LENGTH_SHORT).show();
                       }else if(mSelectedUser != null){
                           AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
                           builder.setMessage("Are you sure you want to delete:\n" + mSelectedUser)
                                   .setPositiveButton("Yes", ((dialog, which) ->
                                           mDao.delete(mSelectedUser))

                                   ).setNegativeButton("No", ((dialog, which) -> {
                           }));
                           AlertDialog dialog = builder.create();
                           dialog.show();
                       }


                   }
               });


    }



}