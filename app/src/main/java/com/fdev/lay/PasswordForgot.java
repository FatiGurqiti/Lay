package com.fdev.lay;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PasswordForgot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgot);


        TextView afterText = (TextView) findViewById(R.id.aftertext);
        Button reset = (Button) findViewById(R.id.reset);
        EditText resetpassword = (EditText) findViewById(R.id.emailreset);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);


        pb.setVisibility(View.INVISIBLE);
        afterText.setVisibility(View.INVISIBLE);

        reset.setOnClickListener(v -> {

            pb.setVisibility(View.VISIBLE);
            resetpassword.setVisibility(View.INVISIBLE);
            reset.setEnabled(false);
            afterText.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
            String password = resetpassword.getText().toString();

            FirebaseAuth.getInstance().sendPasswordResetEmail(password)
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful())
                            afterText.setText("No such user");
                    });

        });
    }
}
