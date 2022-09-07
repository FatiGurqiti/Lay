package com.fdev.lay.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.fdev.lay.ui.create_account.CreateAccount;
import com.fdev.lay.ui.forgotPassword.PasswordForgot;
import com.fdev.lay.R;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private AutoCompleteTextView emailEditText;
    private EditText passwordInputEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.username);
        passwordInputEditText = findViewById(R.id.password);
        progressBar = findViewById(R.id.loading);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setup();
    }

    private void setup() {
        setupClickEvents();

        passwordInputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId & EditorInfo.IME_MASK_ACTION) != 0) {
                login();
                return true;
            } else return false;
        });
    }

    private void setupClickEvents() {
        Button signUpButton = findViewById(R.id.create_acct_button);
        Button loginButton = findViewById(R.id.login);
        TextView forgotTextView = findViewById(R.id.forgotpassword);

        forgotTextView.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, PasswordForgot.class)));

        signUpButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateAccount.class)));

        loginButton.setOnClickListener(v -> login());
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        String emailString = emailEditText.getText().toString().trim();
        String passwordString = passwordInputEditText.getText().toString().trim();
        viewModel.userLogin(this,progressBar,emailString, passwordString);
    }

    @Override
    public void onBackPressed() {}
}

