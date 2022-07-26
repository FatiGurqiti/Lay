package com.fdev.lay;

import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MainLoggedIn extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static String username;
    private static int quota;
    private static ArrayList<String> id = new ArrayList<>();
    private static ArrayList<String> img = new ArrayList<>();
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> rate = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged_in);

        usernameQuery();
        topRateQuery();
        quotaQuery();
        renewQuota();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

    }

    public void onBackPressed() {
    }


    String TAG = "rateMovie";

    private void topRateQuery() {
        CollectionReference collectionRef = db.collection("MovieRate");
        collectionRef.orderBy("rate", Query.Direction.DESCENDING).limit(4)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int i = 0; i < 4; i++) {
                            id.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("id")));
                            img.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("img")));
                            name.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("name")));
                            rate.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("rate")));
                        }

                    }
                });
    }

    private void usernameQuery() {
        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                username = (document.getString("username"));
                            }
                        }
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void quotaQuery() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            quota = Math.toIntExact((document.getLong("quota")));
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void lowerQuota() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            quota = Math.toIntExact((document.getLong("quota")));
                            if (quota > 0)
                                quota--;

                            Map<String, Object> user = new HashMap<>();
                            user.put("quota", quota);

                            db.collection("users").document(mAuth.getCurrentUser().getEmail())
                                    .set(user, SetOptions.merge());
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void renewQuota() {
        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            int year = Math.toIntExact(document.getLong("last_update_year"));
                            int month = Math.toIntExact(document.getLong("last_update_month"));
                            int day = Math.toIntExact(document.getLong("last_update_day"));

                            Calendar cal = Calendar.getInstance();
                            int yearNow = cal.get(Calendar.YEAR);
                            int monthNow = cal.get(Calendar.MONTH) + 1;
                            int dayNow = cal.get(Calendar.DAY_OF_MONTH);

                            //Renew quota
                            if (yearNow - year == 0) //It's the same year
                            {
                                if (monthNow - month == 0) { //It's the same month

                                    if (dayNow - day > 0) { // It's not the same day

                                        updateQuota(monthNow, dayNow, yearNow);
                                    }
                                } else { //Not the same month
                                    updateQuota(monthNow, dayNow, yearNow);
                                }
                            } else { //Not the same year
                                updateQuota(monthNow, dayNow, yearNow);
                            }
                        }
                    }
                });

    }

    private void updateQuota(int monthNow, int dayNow, int yearNow) {
        Map<String, Object> user = new HashMap<>();
        user.put("quota", 15);
        user.put("last_update_month", monthNow);
        user.put("last_update_day", dayNow);
        user.put("last_update_year", yearNow);

        db.collection("users").document(mAuth.getCurrentUser().getEmail())
                .set(user, SetOptions.merge());
    }

    public static String getUsername() {
        return username;
    }

    public static int getQuota() {
        return quota;
    }

    public static ArrayList<String> getTopRatedId() {
        return id;
    }

    public static ArrayList<String> getTopRatedImg() {
        return img;
    }

    public static ArrayList<String> getTopRatedName() {
        return name;
    }

    public static ArrayList<String> getTopRatedRate() {
        return rate;
    }


}