package com.example.fibuv2.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.FAQ;
import com.example.fibuv2.MainLoggedIn;
import com.example.fibuv2.R;
import com.example.fibuv2.ResetPassword;
import com.example.fibuv2.database.DatabaseHandler;
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

import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("users");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView username = root.findViewById(R.id.text_username);
        final TextView email = root.findViewById(R.id.text_email);
        final TextView reset = root.findViewById(R.id.change_password);
        final TextView logout = root.findViewById(R.id.logout);
        final TextView FAQ = root.findViewById(R.id.faq);
        final Switch litemode = root.findViewById(R.id.switch1);


        //Get's lite mode status and set's the switch widget according to that
        DatabaseHandler sqldb = new DatabaseHandler(getContext());

        if(sqldb.getIsLiteMode()) litemode.setChecked(true);
        else litemode.setChecked(false);

        Log.d("litemode", String.valueOf(sqldb.getIsLiteMode()));
        litemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sqldb.getIsLiteMode()) // if the lite mode is on
                {
                    sqldb.setLiteModeOff(); //set it off
                }
                else
                {
                    sqldb.setLiteModeOn(); // set it on
                }

            }
        });

        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.fibuv2.FAQ.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler db = new DatabaseHandler(getContext());
                db.setLoginFalse(); // sign's with sqlite

                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ResetPassword.class);
                startActivity(intent);
            }
        });

        email.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());


        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Username", document.getId() + " => " + document.getData());
                                username.setText(document.getString("username"));
                            }
                        } else {
                            Log.d("Username", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return root;
    }



}