package com.example.fibuv2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public void onBackPressed() {}


    String TAG = "rateMovie";
    private void topRateQuery(){
        CollectionReference collectionRef = db.collection("MovieRate");
        collectionRef.orderBy("rate", Query.Direction.DESCENDING).limit(4)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int i =0;i<4;i++){
                            id.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("id")));
                            img.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("img")));
                            name.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("name")));
                            rate.add(String.valueOf(queryDocumentSnapshots.getDocuments().get(i).get("rate")));
                        }

                    }
                });
    }

    private void usernameQuery(){
        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Username", document.getId() + " => " + document.getData());
                                username = (document.getString("username"));
                            }
                        } else {
                            Log.d("Username", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void quotaQuery(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Username", document.getId() + " => " + document.getData());
                            quota = Math.toIntExact((document.getLong("quota")));
                            Log.d("QuotainLoop", String.valueOf(quota));
                        }
                    } else {
                        Log.d("Username", "Error getting documents: ", task.getException());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected static void lowerQuota(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            quota = Math.toIntExact((document.getLong("quota")));
                            Log.d("QuotainLoop", String.valueOf(quota));
                            if(quota >0)
                            quota --;

                            Map<String, Object> user = new HashMap<>();
                            user.put("quota", quota);

                            db.collection("users").document(mAuth.getCurrentUser().getEmail())
                                    .set(user, SetOptions.merge());


                        }
                    } else {
                        Log.d("Username", "Error getting documents: ", task.getException());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void renewQuota(){
        db.collection("users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("UpdateQuota", String.valueOf(document.get("last_update")));

                            int month = Math.toIntExact(document.getLong("last_update_month"));
                            int day   = Math.toIntExact(document.getLong("last_update_day"));

                            int monthNow = Calendar.MONTH;
                            int dayNow = Calendar.DAY_OF_MONTH;

                            if(month - monthNow >= 0)
                            {
                                if(day - dayNow < 0)
                                {
                                    //Renew quota
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("quota", 10);
                                    user.put("last_update_month", monthNow);
                                    user.put("last_update_day", dayNow);

                                    db.collection("users").document(mAuth.getCurrentUser().getEmail())
                                            .set(user, SetOptions.merge());
                                }
                            }


                        }
                    } else {
                        Log.d("UpdateQuota", "Error getting documents: ", task.getException());
                    }
                });
    }



    public static String getUsername(){
        return username;
    }
    public static int getQuota(){
        return quota;
    }
    public static ArrayList<String> getTopRatedId  () {return id  ;}
    public static ArrayList<String> getTopRatedImg () {return img ;}
    public static ArrayList<String> getTopRatedName() {return name;}
    public static ArrayList<String> getTopRatedRate() {return rate;}


}