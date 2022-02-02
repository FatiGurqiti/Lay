package com.lay.fibuv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);
                resetpassword.setVisibility(View.INVISIBLE);
                reset.setEnabled(false);
                afterText.setVisibility(View.VISIBLE);
                pb.setVisibility(View.INVISIBLE);

                FirebaseAuth mAuth;

                String password = resetpassword.getText().toString();


                FirebaseAuth.getInstance().sendPasswordResetEmail(password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("EmailStatus", "Email sent.");
                                }
                                else{

                                    Log.d("EmailStatus", "Email isn't sent");
                                    afterText.setText("No such user");
                                }
                            }
                        });

            }
        });
    }
    }
