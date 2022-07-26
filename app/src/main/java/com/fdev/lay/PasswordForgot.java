package com.fdev.lay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PasswordForgot extends AppCompatActivity {

    private TextView afterText;
    private Button resetButton;
    private EditText resetPasswordEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgot);

        afterText = (TextView) findViewById(R.id.aftertext);
        resetButton = (Button) findViewById(R.id.reset);
        resetPasswordEditText = (EditText) findViewById(R.id.emailreset);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
        afterText.setVisibility(View.INVISIBLE);

        resetButton.setOnClickListener(v -> {
            resetPassword();
        });

        resetPasswordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId & EditorInfo.IME_MASK_ACTION) != 0) {
                resetPassword();
                return true;
            } else
                return false;
        });
    }

    private void resetPassword() {
        progressBar.setVisibility(View.VISIBLE);
        resetPasswordEditText.setVisibility(View.INVISIBLE);
        resetButton.setEnabled(false);
        afterText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        String password = resetPasswordEditText.getText().toString();

        FirebaseAuth.getInstance().sendPasswordResetEmail(password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful())
                        afterText.setText("No such user");
                });
    }
}
