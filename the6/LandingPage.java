package com.example.the6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.the6.Database.AppDataBase;
import com.example.the6.Database.SixDAO;

public class LandingPage extends AppCompatActivity {

    public static final String USER_ID_KEY = "com.example.the6.UserIdKey";
    public static final String PREFERENCES_KEY = "com.example.the6.PreferencesKey";

    private Button Searchbtn;
    private Button Historybtn;
    private Button Cancelbtn;
    private Button Adminbtn;
    private TextView nametext;


    private SixDAO mDao;

    private User mUser;

    private int mUserId = -1;

    private SharedPreferences mPreferences = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        getDataBase();

        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        mUser = mDao.getUserbyUserId(mUserId);

        WireupDisplay();

        nametext = findViewById(R.id.textViewusernameLanding);
        nametext.setText(mUser.getUsername());

    }


    // logout options button at top right corner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);

        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(mUser != null){
            MenuItem item = menu.findItem(R.id.logoutbtnmenu);
            item.setTitle(mUser.getUsername());
        }

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LandingPage.this, MainActivity.class));

        return super.onOptionsItemSelected(item);
    }

    public void WireupDisplay(){



        Searchbtn = (Button) findViewById(R.id.Searchbtn); // TODO make search page
        Historybtn = (Button) findViewById(R.id.Historybtn); // TODO make history scrolling page
        Cancelbtn = (Button) findViewById(R.id.Cancelbtn); //TODO make cancel order page
        Adminbtn = (Button) findViewById(R.id.Adminbtn); //TODO make admin page

        Searchbtn.setOnClickListener(v -> {
            Intent intent = Search.intentFactory(getApplicationContext(), mUser.getUserId());
            startActivity(intent);
        });

        Historybtn.setOnClickListener(v -> {
            Intent intent = History.intentFactory(getApplicationContext(), mUser.getUserId());
            startActivity(intent);
        });

        Cancelbtn.setOnClickListener(v -> {
            Intent intent = CancelOrder.intentFactory(getApplicationContext(), mUser.getUserId());
            startActivity(intent);
        });

        Adminbtn.setOnClickListener(v -> {
            Intent intent = Admin.intentFactory(getApplicationContext(), mUser.getUserId());
            startActivity(intent);
        });

       if(!mUser.getUsername().equals("admin2")){
            Adminbtn.setVisibility(View.GONE);
        }

    }

    private void getDataBase(){
        mDao = Room.databaseBuilder(this, AppDataBase.class,
                AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getSixDAO();
    }

    public static Intent intentFactory(Context context, int mUserId){
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(USER_ID_KEY, mUserId);

        return intent;
    }

    private void getPrefs(){
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    // getIntent worked so i didnt need this but i left it for future references
    private void CheckForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){
            return;
        }

        if(mPreferences == null){
            getPrefs();
        }

        mUserId = mPreferences.getInt(USER_ID_KEY, -1);
        if(mUserId != -1){
            return;
        }
    }



}