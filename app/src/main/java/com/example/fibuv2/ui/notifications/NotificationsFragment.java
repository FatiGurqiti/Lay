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
        DatabaseHandler db = new DatabaseHandler(getContext());

        if(db.getIsLiteMode()) litemode.setChecked(true);
        else litemode.setChecked(false);

        Log.d("litemode", String.valueOf(db.getIsLiteMode()));
        litemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.getIsLiteMode()) // if the lite mode is on
                {
                    db.setLiteModeOff(); //set it off
                }
                else
                {
                    db.setLiteModeOn(); // set it on
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



            notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                email.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                Log.d("User ID logged in", user.getUid());
                Log.d("User mail logged in", user.getEmail());



//There's a problem is that is causing the app to crash


//                collectionReference
//                        .whereEqualTo("email", user.getEmail().toString())
//                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                                assert value != null;
//                                if (!value.isEmpty()) {
//
//                                    for (QueryDocumentSnapshot snapshot : value) {
//                                        username.setText( snapshot.getString("username"));
//
//                                        Log.d("Username logged in", snapshot.getString("username"));
//
//
//                                    }  } }
//
//                        });

            }
        });
        return root;
    }



}