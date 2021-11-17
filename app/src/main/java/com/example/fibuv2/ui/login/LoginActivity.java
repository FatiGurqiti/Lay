package com.example.fibuv2.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;

import com.example.fibuv2.Admin;
import com.example.fibuv2.CreateAccount;
import com.example.fibuv2.MainLoggedIn;
import com.example.fibuv2.PasswordForgot;
import com.example.fibuv2.R;

public class LoginActivity extends AppCompatActivity {

    private Button HesapAcButton;
    private Button GirisButton;
    private Button DevelopersByPass;
    private AutoCompleteTextView Email;
    private EditText sifre;
    private ProgressBar progressBar;
    private TextView forgot;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      //  firebaseAuth = FirebaseAuth.getInstance();
        HesapAcButton = findViewById(R.id.create_acct_button);
        GirisButton = findViewById(R.id.login);
        Email= findViewById(R.id.username);
        sifre = findViewById(R.id.password);
        progressBar= findViewById(R.id.loading);
        DevelopersByPass = findViewById(R.id.byPass);
        forgot = findViewById(R.id.forgotpassword);


        DevelopersByPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainLoggedIn.class);
                startActivity(intent);
            }
        });


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordForgot.class);
                startActivity(intent);
            }
        });


        HesapAcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                HESAPAC();
            }
        });

        GirisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                GirisEmailSifreKullanici(sifre.getText().toString().trim(), Email.getText().toString().trim());



            }
        });
    }

    private void HESAPAC(){
        startActivity(new Intent(LoginActivity.this,CreateAccount.class));
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void  GirisEmailSifreKullanici(final String email, String Sifre){

        if(email.toString().contentEquals("admin")){

            startActivity(new Intent(LoginActivity.this, Admin.class));
            progressBar.setVisibility(View.INVISIBLE);
        }

        else if(!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(Sifre)){
            //here you log in


        }


    }

}

