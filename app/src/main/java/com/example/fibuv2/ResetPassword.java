package com.example.fibuv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fibuv2.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Timer;
import java.util.TimerTask;

public class ResetPassword extends AppCompatActivity {

    private  FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("users");
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;

        Log.d("resetemailcheck", user.getEmail());
        ProgressBar pg = findViewById(R.id.resetprogressBar);
        Button resetbtn = findViewById(R.id.restetresetbtn);
        EditText passwordcheck = findViewById(R.id.passwordreset);
        EditText resetpasswordinput = findViewById(R.id.passwordreset);
        TextView resetaftertext = findViewById(R.id.restetaftertext);
        pg.setVisibility(View.INVISIBLE);

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);

                String passwordCheck = passwordcheck.getText().toString();

                Log.d("localPassword", passwordCheck);

                collectionReference
                        .whereEqualTo("email", user.getEmail().toString())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                assert value != null;
                                if (!value.isEmpty()) {

                                    for (QueryDocumentSnapshot snapshot : value) {

                                        String remotePassword = snapshot.getString("password");
                                        Log.d("remotePassword", remotePassword);

                                        if(passwordCheck.equals(remotePassword))
                                        {
                                            Log.d("passwordMatch", "they are the same!");
                                            resetpasswordinput.setVisibility(View.INVISIBLE);
                                            resetaftertext.setVisibility(View.VISIBLE);
                                            resetbtn.setEnabled(false);

                                            timer = new Timer();
                                            timer.schedule(new TimerTask() {
                                                               @Override
                                                               public void run() {

                                                                   mAuth = FirebaseAuth.getInstance();
                                                                   FirebaseAuth.getInstance().signOut();
                                                                   FirebaseUser user = mAuth.getCurrentUser();
                                                                   user = null;
                                                                   System.exit(0);


                                                               }
                                                           }
                                                    ,2500
                                            );

                                            FirebaseAuth.getInstance().sendPasswordResetEmail(user.getEmail())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("EmailStatus", "Email sent.");
                                                            }
                                                            else{

                                                                Log.d("EmailStatus", "Email isn't sent");
                                                                resetaftertext.setText("No such user");
                                                            }
                                                        }
                                                    });
                                        }
                                        else{
                                            Log.d("passwordMatch", "password aren't same");

                                            Toast.makeText(ResetPassword.this, "Umm. It just doesn't feel right",
                                                    Toast.LENGTH_LONG).show();
                                        }


                                    }  } }

                        });


                pg.setVisibility(View.INVISIBLE);

            }
        });
    }
}