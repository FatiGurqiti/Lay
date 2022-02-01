package com.example.fibuv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fibuv2.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class CreateAccount extends AppCompatActivity {





    private boolean canclick = false;
    private FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    private FirebaseUser userF;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final Button signupBtn = findViewById(R.id.create_acct_button);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText username = (EditText) findViewById(R.id.CreateAccounUsernameInput);
                EditText email =  (EditText) findViewById(R.id.CreateAccountEmailInput);
                EditText password =  (EditText) findViewById(R.id.CreateAccountPasswordInput);

                String usernameString = username.getText().toString();
                String emailString = email.getText().toString();
                String passwordString  = password.getText().toString();

                if(TextUtils.isEmpty(usernameString) &&
                        TextUtils.isEmpty(emailString) &&
                            TextUtils.isEmpty(passwordString)){

                    Log.d("Input Status", "Inputs are empty");
                    Toast.makeText(CreateAccount.this, "Would you mind if you fill the inputs?",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Log.d("Input Status", "Inputs are filled");

                    userData(emailString,passwordString,usernameString);
                    createAccount(emailString,passwordString);
                    Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void createAccount(String email,String password){


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Create User Status", "createUserWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Create User Status", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccount.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }

    private void userData(String email,String password,String username){

        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH) +1;   //It takes January as 0, So I want to avoid that and make it more readable
        int day = rightNow.get(Calendar.DAY_OF_MONTH);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("password", password);
        user.put("quota", 10);
        user.put("last_update_year", year);
        user.put("last_update_month", month);
        user.put("last_update_day", day);

        db.collection("users").document(email)
                .set(user);
    }

}