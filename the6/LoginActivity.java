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

import java.util.ArrayList;
import java.util.List;


// this should have been named sign up activity but i was too lazy to change it
// correction i changed it and it broke everything :(
public class LoginActivity extends AppCompatActivity {


    private Button SignupBtn;
    private EditText Usernamefield;
    private EditText PasswordField;

    private String mUsername;
    private String mPassword;

    private SixDAO mDao;

    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);

        getDataBase();
        WireupDisplay();

        SignupBtn.setOnClickListener(v -> {

            getValuesFromDisplay();// edittext.gettext() is very important-___-
            List<User> userList = mDao.getAllUsers();
            ArrayList<String> namelist= new ArrayList<>();

            for (int i = 0; i < userList.size(); i++) {
                namelist.add(userList.get(i).getUsername());
            }

            if(!namelist.contains(mUsername)) {
                mDao.insert(mUser);

                Intent intent2 = LandingPage.intentFactory(getApplicationContext(), mUser.getUserId());
                startActivity(intent2);

            }else{
                Toast.makeText(this, "Sorry that username is taken", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);}

        });

    }

    private void WireupDisplay(){
        SignupBtn = findViewById(R.id.Signupbtn2);
        Usernamefield = findViewById(R.id.editTextUserName2);
        PasswordField = findViewById(R.id.editTextTextPassword2);

    }

    private void getDataBase(){
        mDao = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getSixDAO();
    }

    private void getValuesFromDisplay(){
        mUsername = Usernamefield.getText().toString().toLowerCase();
        mPassword = PasswordField.getText().toString().toLowerCase();
        mUser = new User(mUsername, mPassword);


    }


}