package com.fdev.lay.presentation.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fdev.lay.CreateAccount;
import com.fdev.lay.MainLoggedIn;
import com.fdev.lay.PasswordForgot;
import com.fdev.lay.R;
import com.fdev.lay.database.DatabaseHandler;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView emailEditText;
    private EditText passwordInputEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signUpButton = findViewById(R.id.create_acct_button);
        Button loginButton = findViewById(R.id.login);
        emailEditText = findViewById(R.id.username);
        passwordInputEditText = findViewById(R.id.password);
        progressBar = findViewById(R.id.loading);
        TextView forgotTextView = findViewById(R.id.forgotpassword);


        forgotTextView.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, PasswordForgot.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            createUser();
        });

        loginButton.setOnClickListener(v -> {
            login();
        });

        passwordInputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId & EditorInfo.IME_MASK_ACTION) != 0) {
                login();
                return true;
            } else
                return false;
        });

    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        String emailString = emailEditText.getText().toString().trim();
        String passwordString = passwordInputEditText.getText().toString().trim();
        userLogin(emailString, passwordString);
    }

    @Override
    public void onBackPressed() {
    }

    private void createUser() {
        startActivity(new Intent(LoginActivity.this, CreateAccount.class));
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void userLogin(final String email, String password) {

        if (TextUtils.isEmpty(email) &&
                TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Would you mind if you fill the inputs?",
                    Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            //Set's the log in true. So, the user won't have to sign in again
                            DatabaseHandler db = new DatabaseHandler(LoginActivity.this);
                            db.setLoginTrue();

                            Intent intent = new Intent(LoginActivity.this, MainLoggedIn.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}

