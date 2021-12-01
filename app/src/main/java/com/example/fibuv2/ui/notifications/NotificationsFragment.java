package com.example.fibuv2.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fibuv2.MainLoggedIn;
import com.example.fibuv2.R;
import com.example.fibuv2.ResetPassword;
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
        final Button logout = root.findViewById(R.id.logoutbtn);
        final TextView reset = root.findViewById(R.id.change_password);

         ImageView notfoundIMAGE = root.findViewById(R.id.notFoundImage);
         TextView notfoundText = root.findViewById(R.id.notFoundText);
         Button coolBtn = root.findViewById(R.id.coolbutton);
         Button coolsearchBtn = root.findViewById(R.id.coolsearchbtn);




        coolsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notfoundIMAGE.setVisibility(View.INVISIBLE);
                notfoundText.setVisibility(View.INVISIBLE);
                coolBtn.setVisibility(View.INVISIBLE);
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ResetPassword.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d("isLoggedOut","logged out");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                email.setText(mAuth.getCurrentUser().getEmail().toString());

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                Log.d("User ID logged in", user.getUid().toString());
                Log.d("User mail logged in", user.getEmail().toString());




                collectionReference
                        .whereEqualTo("email", user.getEmail().toString())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                assert value != null;
                                if (!value.isEmpty()) {

                                    for (QueryDocumentSnapshot snapshot : value) {
                                        username.setText( snapshot.getString("username"));

                                        Log.d("Username logged in", snapshot.getString("username"));


                                    }  } }

                        });

            }
        });
        return root;
    }



}