package com.fdev.lay.presentation.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fdev.lay.CreateAccount;
import com.fdev.lay.MainLoggedIn;
import com.fdev.lay.PasswordForgot;
import com.fdev.lay.R;
import com.fdev.lay.database.DatabaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button SignUp;
    private Button Login;
    private AutoCompleteTextView Email;
    private EditText passwordinput;
    private ProgressBar progressBar;
    private TextView forgot;

    private static String emailString;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignUp = findViewById(R.id.create_acct_button);
        Login = findViewById(R.id.login);
        Email= findViewById(R.id.username);
        passwordinput = findViewById(R.id.password);
        progressBar= findViewById(R.id.loading);
        forgot = findViewById(R.id.forgotpassword);


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordForgot.class);
                startActivity(intent);
            }
        });


        SignUp.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            createUser();
        });

        Login.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            emailString = Email.getText().toString().trim();
            String passwordString =passwordinput.getText().toString().trim();

            userLogin(emailString,passwordString);



        });

    }

    @Override
    public void onBackPressed() {}

    private void createUser(){
        startActivity(new Intent(LoginActivity.this,CreateAccount.class));
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void  userLogin(final String email, String password){



         if(TextUtils.isEmpty(email) &&
                TextUtils.isEmpty(password) ){
            Toast.makeText(LoginActivity.this, "Would you mind if you fill the inputs?",
                    Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
        else {

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                //Set's the log in true. So, the user won't have to sign in again
                                DatabaseHandler db = new DatabaseHandler(LoginActivity.this);
                                db.setLoginTrue();

                                Intent intent = new Intent(LoginActivity.this,MainLoggedIn.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    public static String getEmailString(){return emailString;}

}

