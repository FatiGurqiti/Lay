package com.fdev.lay.ui.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fdev.lay.common.Constants;
import com.fdev.lay.R;
import com.fdev.lay.ui.login.LoginActivity;
import com.fdev.lay.ui.resetPassword.ResetPassword;
import com.fdev.lay.data.local.database.DatabaseHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private static boolean liteMode;
    private static boolean showSeenContent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView username = root.findViewById(R.id.text_username);
        final TextView email = root.findViewById(R.id.text_email);
        final TextView reset = root.findViewById(R.id.change_password);
        final TextView logout = root.findViewById(R.id.logout);
        final TextView liemodeText = root.findViewById(R.id.text_litemode);
        final Switch litemode = root.findViewById(R.id.switch1);
        final TextView showSeenText = root.findViewById(R.id.seen_content_text);
        final Switch showSeenSwitch = root.findViewById(R.id.switch_saved_content);


        email.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
        username.setText(Constants.INSTANCE.getUsername());


        //Get's lite mode status and set's the switch widget according to that
        DatabaseHandler sqldb = new DatabaseHandler(getContext());


        //Get if show seen content is on
        showSeenContent = sqldb.getShowSeenContents();
        if(sqldb.getShowSeenContents())
        {
            showSeenSwitch.setChecked(true);
            showSeenText.setTextColor(Color.parseColor("#b00768"));
        }
        else  {
            showSeenSwitch.setChecked(false);
            showSeenText.setTextColor(Color.parseColor("#66000000"));
        }



        showSeenSwitch.setOnClickListener(v -> {
            if (sqldb.getShowSeenContents()) // if the show seen content is on
            {
                sqldb.setShowSeenContentsOff(); //set it off
                showSeenText.setTextColor(Color.parseColor("#66000000"));
            } else {
                sqldb.setShowSeenContentsOn(); // set it on
                showSeenText.setTextColor(Color.parseColor("#b00768"));
            }
        });


        //check if lite mode is on
        liteMode = sqldb.getIsLiteMode();
        if (sqldb.getIsLiteMode())
        {
            litemode.setChecked(true);
            liemodeText.setTextColor(Color.parseColor("#00B612"));
        } else litemode.setChecked(false);

        litemode.setOnClickListener(v -> {
            if (sqldb.getIsLiteMode()) // if the lite mode is on
            {
                sqldb.setLiteModeOff(); //set it off
                liemodeText.setTextColor(Color.parseColor("#66000000"));
            } else {
                sqldb.setLiteModeOn(); // set it on
                liemodeText.setTextColor(Color.parseColor("#00B612"));
            }

        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler db = new DatabaseHandler(getContext());
                db.setLoginFalse(); // sign in with sqlite

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ResetPassword.class);
                startActivity(intent);
            }
        });


        return root;
    }

    public static boolean getLiteMode(){return liteMode;}
    public static boolean getShowSeenContent(){return showSeenContent;}

}