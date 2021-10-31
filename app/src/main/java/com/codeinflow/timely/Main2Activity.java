package com.codeinflow.timely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Main2Activity extends AppCompatActivity {

    private static BottomNavigationView navView;


    private FirebaseFirestore db;
    String a;


    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(a.length()==9){
                        fragment = new home();
                        Log.d("Auth","Constable");
                    }
                    if(a.length()==13){

                        fragment = new HomeSubInsp();
                        Log.d("Auth","Sub Inspector");
                    }
                    if(a.length()==3){
                        fragment = new HomeInspector();

                        Log.d("Auth","Inspector");
                    }
                    break;
                case R.id.navigation_profile:


                    Toast.makeText(Main2Activity.this,"Profile",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_announcements:
                    Toast.makeText(Main2Activity.this,"Announcements",Toast.LENGTH_SHORT).show();

                    break;
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
            fragmentTransaction.replace(R.id.frag_container2, fragment);
            fragmentTransaction.commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        if (currentuser.length() > 5) {
            DocumentReference docRef = db.collection("Users").document(currentuser);

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        if (document != null) {
                            a = document.getString("desig");
                            Log.d("LOGGER", a);
                            if(a.length()==9){
                                fragment = new home();
                                Log.d("Auth","Constable");
                            }
                            if(a.length()==13){
                                fragment = new HomeSubInsp();
                                Log.d("Auth","Sub Inspector");
                            }
                            if(a.length()==3){
                                fragment = new HomeInspector();
                                Log.d("Auth","Inspector");
                            }

                            navView.setSelectedItemId(R.id.navigation_home);
                        } else {
                            Log.d("LOGGER", "Error");
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });
        }

    }
}