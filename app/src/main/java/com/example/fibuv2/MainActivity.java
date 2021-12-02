package com.example.fibuv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.fibuv2.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.time.DateTimeException;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    TextView welcomeText;
    private FirebaseAuth mAuth;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();




        welcome();

        timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {

                                   Intent intent = new Intent(MainActivity.this, MainLoggedIn.class);

                                   startActivity(intent);
                                   finish();

                           }
                       }
                ,2500  //later to be set to 2500
        );


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void welcome(){

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);


        String time = String.valueOf(hour);
        welcomeText  = findViewById(R.id.creativeText);;

       Log.d("Time: ", time);

       String WelcomeText = null;

       if(hour < 12 && hour > 5){ WelcomeText = "Good Morning";}
       else if (hour > 12 && hour < 18){ WelcomeText = "Good Afternoon";}
       else { WelcomeText = "Good Evening";}

       welcomeText.setText(WelcomeText);

    }
}