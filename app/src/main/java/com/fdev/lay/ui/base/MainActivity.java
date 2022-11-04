package com.fdev.lay.ui.base;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import com.fdev.lay.R;
import com.fdev.lay.common.Constants;
import com.fdev.lay.common.Instants;
import com.fdev.lay.common.utils.Utils;
import com.fdev.lay.data.local.database.Account;
import com.fdev.lay.data.local.database.DatabaseHandler;
import com.fdev.lay.ui.login.LoginActivity;
import com.fdev.lay.ui.MainScreen.HomePage;
import com.fdev.lay.ui.FirstTime.FirstTime2;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private MainMainViewModel viewModel;
    private boolean firstTime;
    private DatabaseHandler dbHandler = new DatabaseHandler(this);
    private static String apiToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainMainViewModel.class);
        setup();
    }

    private void setup() {
        checkInternet();
        userStatus();
        canSetUserName();
        setAPIKey();
        strictMode();
        welcome();
        redirect();
    }

    private void setAPIKey() {
        int random = Math.abs(ThreadLocalRandom.current().nextInt() % 10);
        apiToken = Constants.INSTANCE.getKey().get(random);
    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    void userStatus() {
        //If there is no data created in account table it's user's first time and it creates the account table in sqlite
        if (dbHandler.getaccountsCount() == 0) {
            dbHandler.addaccount(new Account(1, 1, 0, 0, 1));
            firstTime = true;

        } else if (dbHandler.getaccountsCount() >= 1) { // to delete extra accounts in case if there's more than one
            for (int i = 0; i > dbHandler.getaccountsCount(); i++)
                try {
                    dbHandler.deleteaccount(new Account(i, 0, 0, 0, 1));
                } catch (Exception e) {
                    dbHandler.deleteaccount(new Account(i, 1, 0, 0, 1));
                }

        } else if (dbHandler.getIsFirtsTime()) firstTime = true;
        else firstTime = false;
    }

    private void welcome() {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        TextView welcomeText = findViewById(R.id.creativeText);
        String WelcomeText;

        if (hour < 12 && hour > 5) WelcomeText = "Good Morning";
        else if (hour > 12 && hour < 18) WelcomeText = "Good Afternoon";
        else WelcomeText = "Good Evening";

        welcomeText.setText(WelcomeText);
    }

    private void redirect() {
        int delay;
        if (dbHandler.getIsLiteMode()) delay = 0;
        else delay = 1200;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Intent intent;
                               if (firstTime) intent = new Intent(MainActivity.this, FirstTime2.class);
                               else if (dbHandler.getIsLoggedIn()) intent = new Intent(MainActivity.this, HomePage.class);
                               else intent = new Intent(MainActivity.this, LoginActivity.class);
                               startActivity(intent);
                               finish();
                           }
                       }
                , delay
        );
    }

    private void checkInternet(){
        Instants.INSTANCE.setInternetAvailable(Utils.isNetworkAvailable(this));
    }

    private void canSetUserName(){
        if (Instants.INSTANCE.isInternetAvailable() && !firstTime && dbHandler.getIsLoggedIn())
        Instants.INSTANCE.setCanShowUserName(true);
    }

    public static String getToken() {
        return apiToken;
    }
}