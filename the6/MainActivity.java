package com.example.the6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.the6.Database.AppDataBase;
import com.example.the6.Database.SixDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    private Button Loginbtn;
    private Button Signupbtn;
    private EditText UsernameField;
    private EditText PasswordField;

    private SixDAO mDao;

    private String mUsername;
    private String mPassword;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataBase();
        createInventory();
        WireupDisplay();

        System.out.println(mDao.getALLProducts());
        System.out.println(mDao.getAllUsers());
    }

    private void WireupDisplay(){
        Loginbtn = findViewById(R.id.Loginbtn);
        Signupbtn = findViewById(R.id.SignUpbtn);
        UsernameField = findViewById(R.id.editTextUserName);
        PasswordField = findViewById(R.id.editTextTextPassword);


        Loginbtn.setOnClickListener(v -> {
            getValuesFromDisplay();

            if(CheckForUserInDataBase()){
                if(!CheckPassword()){
                    Toast.makeText(MainActivity.this,
                    "Invalid Password", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = LandingPage.intentFactory(getApplicationContext(), mUser.getUserId());
                    startActivity(intent);
                }
            }

        });

        Signupbtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        });

    }

    private void getValuesFromDisplay(){
        mUsername = UsernameField.getText().toString().toLowerCase();
        mPassword = PasswordField.getText().toString().toLowerCase();

    }

    private void getDataBase(){
        mDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getSixDAO();
    }

    private boolean CheckForUserInDataBase(){
        mUser = mDao.getUserbyUsername(mUsername);

        if(mUser != null){
            return true;
        }

        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();

    return false;
    }

    private boolean CheckPassword(){
        getValuesFromDisplay();
        return mUser.getPassword().equals(mPassword);
    }

    private void createInventory(){

        List<User> userList = mDao.getAllUsers();
        List<Products> productsList = mDao.getALLProducts();

        if(userList.size() < 1 && productsList.size() < 1) {
            Products shoes = new Products("Shoes", 10, 300);
            Products hoodie = new Products("Hoodies", 10, 150);
            Products joggers = new Products("Joggers", 10, 90);
            Products hats = new Products("Hats", 10, 30);
            Products chains = new Products("Chain", 10, 10000);
            Products flannels= new Products("Flannel", 10, 100);

            User admin = new User("admin2", "admin2");
            User testuser1 = new User("testuser1", "testuser1");

            mDao.insert(admin, testuser1);
            mDao.insert(shoes, hoodie, joggers, hats, chains, flannels);
        }



    }



}