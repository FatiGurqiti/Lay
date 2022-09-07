package com.fdev.lay.ui.resetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fdev.lay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Timer;
import java.util.TimerTask;

public class ResetPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private CollectionReference collectionReference ;
    FirebaseUser user;

    private EditText passwordCheck;
    private EditText resetPasswordInput;
    private TextView resetAfterText;
    private ProgressBar pg;
    private Button resetButton;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("users");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        assert user != null;

        pg = findViewById(R.id.resetprogressBar);
        resetButton = findViewById(R.id.restetresetbtn);
        passwordCheck = findViewById(R.id.passwordreset);
        resetPasswordInput = findViewById(R.id.passwordreset);
        resetAfterText = findViewById(R.id.restetaftertext);
        pg.setVisibility(View.INVISIBLE);

        resetButton.setOnClickListener(v -> {
            resetPassword();
        });

        resetPasswordInput.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId & EditorInfo.IME_MASK_ACTION) != 0) {
                resetPassword();
                return true;
            } else
                return false;
        });
    }

    private void resetPassword() {
        pg.setVisibility(View.VISIBLE);
        String passwordCheck = this.passwordCheck.getText().toString();

        collectionReference
                .whereEqualTo("email", user.getEmail())
                .addSnapshotListener((value, error) -> {

                    assert value != null;
                    if (!value.isEmpty()) {
                        for (QueryDocumentSnapshot snapshot : value) {
                            String remotePassword = snapshot.getString("password");

                            if (passwordCheck.equals(remotePassword)) {
                                resetPasswordInput.setVisibility(View.INVISIBLE);
                                resetAfterText.setVisibility(View.VISIBLE);
                                resetButton.setEnabled(false);

                                timer = new Timer();
                                timer.schedule(new TimerTask() {
                                                   @Override
                                                   public void run() {
                                                       mAuth = FirebaseAuth.getInstance();
                                                       FirebaseAuth.getInstance().signOut();
                                                       System.exit(0);
                                                   }
                                               }
                                        , 2500
                                );

                                FirebaseAuth.getInstance().sendPasswordResetEmail(user.getEmail())
                                        .addOnCompleteListener(task -> {
                                            if (!task.isSuccessful())
                                                resetAfterText.setText("No such user");
                                        });
                            } else {
                                Toast.makeText(ResetPassword.this, "Umm. It just doesn't feel right",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
        pg.setVisibility(View.INVISIBLE);
    }
}