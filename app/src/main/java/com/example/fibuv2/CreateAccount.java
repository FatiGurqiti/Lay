package com.example.fibuv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccount extends AppCompatActivity {



    private String usernameString;
    private String emailString;
    private String passwordString;

    private boolean canclick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

         EditText username = (EditText) findViewById(R.id.CreateAccounUsernameInput);
         EditText email =  (EditText) findViewById(R.id.CreateAccountEmailInput);
         EditText password =  (EditText) findViewById(R.id.CreateAccountPasswordInput);
        final Button signupBtn = findViewById(R.id.create_acct_button);

        usernameString = username.toString();
        emailString = email.toString();
        passwordString  = password.toString();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(usernameString) &&
                        !TextUtils.isEmpty(emailString) &&
                        !TextUtils.isEmpty(passwordString)){

                    Log.d("Input Status", "Inputs are empty");
                }
                else {
                    Log.d("Input Status", "Inputs are filled");
                }
            }
        });


    }
}