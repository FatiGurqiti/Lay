package com.example.fibuv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.example.fibuv2.database.Account;
import com.example.fibuv2.database.DatabaseHandler;
import com.example.fibuv2.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.List;
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

        DatabaseHandler db = new DatabaseHandler(this);


        Log.d("account number ", String.valueOf(db.getaccountsCount()));



        if(db.getaccountsCount() == 0){  //if the it's users first time, it creates

            db.addaccount(new Account(1,1,0));
            //send to first time page
        }
        else if (db.getaccountsCount() > 1){ // to delete accounts in case if there's more than one
            for(int i =0; i> db.getaccountsCount();i++)
                try{
            db.deleteaccount(new Account(i,0,0));
                }
            catch (Exception e) {
                db.deleteaccount(new Account(i,1,0));
                Log.d("Delete Status", e.toString());}
        }
        else{ //it's not users first time

            // set first time 0
        }


        //get accout count
        //if account count = 0 create one
        // if not query for is first time
        // than query for is logged in

        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");



        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Account> contacts = db.getAllaccounts();

        for (Account cn : contacts) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getIsFirstTime() + " ,Phone: " +
                    cn.getIsLoggedIn();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }





        if (Build.VERSION.SDK_INT > 9)
        {
                    StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
        }



        welcome();

        timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Intent intent;
                               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                               if (user != null) {
                                   Log.d("Status",user.toString());
                                   intent = new Intent(MainActivity.this, MainLoggedIn.class);
                               } else {
                                   // No user is signed in
                                   Log.d("Status",user.toString());
                                   intent = new Intent(MainActivity.this, MainActivity.class);
                               }


                                   startActivity(intent);
                                   finish();

                           }
                       }
                ,2500
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
