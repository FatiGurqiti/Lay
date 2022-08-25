package com.fdev.lay.ui.base;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.fdev.lay.R;
import com.fdev.lay.data.local.database.Account;
import com.fdev.lay.data.local.database.DatabaseHandler;
import com.fdev.lay.presentation.ui.login.LoginActivity;
import com.fdev.lay.presentation.ui.main.HomePage;
import com.fdev.lay.ui.FirstTime.FirstTime2;

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

        String[] key = new String[]{
                "faf5f15f11mshde808885082bbb3p1b4a6ejsn3384755128b8",
                "1c94f2813amshf75073ced6bbcdep11f75ajsnf0310f84ce43",
                "b30730a0eemshc261b3851f67015p123219jsnca66e69443cf",
                "32d3f8856emshec3a7f9d298ad39p1594a7jsnd063ba87a615",
                "3a3054add1mshaa261180f949236p1059a1jsnc451fef505ce",
                "0490cbd4famsha5f6c9eb3c5b0bep1d02edjsn9f1dee49dc09",
                "0387b85c24msh9e6dd27318a9b41p11f93ejsn45c86a31edc5",
                "0cbd83e564msh837c6ee47e07343p19551cjsn7cafb1080993",
                "bcac102b38msh3b43e6b9039840fp1a8bdcjsn03aa36bb3eb1"
        };

        int random = Math.abs(ThreadLocalRandom.current().nextInt() % 10);

        APItoken = key[random];

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
                                   intent = new Intent(MainActivity.this, HomePage.class);
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