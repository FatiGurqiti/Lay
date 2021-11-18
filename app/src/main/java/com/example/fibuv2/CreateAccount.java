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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {





    private boolean canclick = false;
    private FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    private FirebaseUser user;

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
                    createAccount(emailString,passwordString);
                    userData(emailString,passwordString,usernameString);
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
                             user = mAuth.getCurrentUser();

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("users");

        Map<String, Object> user = new HashMap<>();
        user.put("userID", mAuth.getUid().toString());
        user.put("username", username);
        user.put("email", email);
        user.put("password", password);
        users.document(mAuth.getUid().toString()).set(user);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Document status", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Document status", "Error adding document", e);
                    }
                });
    }

}