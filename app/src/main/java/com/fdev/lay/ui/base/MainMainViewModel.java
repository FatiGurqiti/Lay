package com.fdev.lay.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fdev.lay.common.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainMainViewModel extends ViewModel {

    private MutableLiveData<String> username;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<String> getUsername() {
        if (username == null) {
            username = new MutableLiveData<String>();
        }
        return username;
    }

    public void setCurrentName() {
        if (username != null) {
            if (Constants.INSTANCE.getCanShowUserName()) {
                db.collection("users")
                        .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    username.setValue((document.getString("username")));
                                }
                            }
                        });
            }
            username.setValue("");
        }
    }
}