package com.fdev.lay;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.fdev.lay.database.Account;
import com.fdev.lay.database.DatabaseHandler;
import com.fdev.lay.presentation.ui.login.LoginActivity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {


    Timer timer;
    TextView welcomeText;
    private boolean firsttime;
    private DatabaseHandler db = new DatabaseHandler(this);
    private static String APItoken;

    @RequiresApi(api = Build.VERSION_CODES.O)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APItoken = "KEY";

        userStatus();
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        welcome();
        int delay;
        if (db.getIsLiteMode()) delay = 0;
        else delay = 1200;
        timer = new Timer();

        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Intent intent;

                               if (firsttime)
                                   intent = new Intent(MainActivity.this, FirstTime2.class);
                               else if (db.getIsLoggedIn())   // User has logged in
                                   intent = new Intent(MainActivity.this, MainLoggedIn.class);
                               else  // User hasn't logged in
                                   intent = new Intent(MainActivity.this, LoginActivity.class);

                               startActivity(intent);
                               finish();

                           }
                       }
                , delay
        );


    }


    private void userStatus() {


        if (db.getaccountsCount() == 0) {
            //It's there is no data created in account table it's first time and it creates the account table in sqlite
            db.addaccount(new Account(1, 1, 0, 0, 1));
            firsttime = true;

        } else if (db.getaccountsCount() >= 1) {
            // to delete extra accounts in case if there's more than one
            for (int i = 0; i > db.getaccountsCount(); i++)
                try {
                    db.deleteaccount(new Account(i, 0, 0, 0, 1));
                } catch (Exception e) {
                    db.deleteaccount(new Account(i, 1, 0, 0, 1));
                }

        } else if (db.getIsFirtsTime()) firsttime = true;
        else firsttime = false;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void welcome() {

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        String time = String.valueOf(hour);
        welcomeText = findViewById(R.id.creativeText);
        String WelcomeText = null;

        if (hour < 12 && hour > 5) {
            WelcomeText = "Good Morning";
        } else if (hour > 12 && hour < 18) {
            WelcomeText = "Good Afternoon";
        } else {
            WelcomeText = "Good Evening";
        }

        welcomeText.setText(WelcomeText);

    }

    public static String getToken() {
        return APItoken;
    }

}