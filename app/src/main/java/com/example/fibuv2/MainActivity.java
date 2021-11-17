package com.example.fibuv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fibuv2.ui.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {

                               Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                               startActivity(intent);
                               finish();
                           }
                       }
                ,200  //later to be set to 2500
        );


    }
}