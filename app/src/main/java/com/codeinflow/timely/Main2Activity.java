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

public class Main2Activity extends AppCompatActivity {

    private static BottomNavigationView navView;
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
                        fragment = new SubInspHome();
                        Log.d("Auth","Sub Inspector");
                    }
                    if(a.length()==3){
                        fragment = new InspHome();
                        Log.d("Auth","Inspector");
                    }
                    break;
                case R.id.navigation_profile:
                    fragment = new Profile();
                    break;
                case R.id.navigation_announcements:
                    fragment = new announcement();
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
    }
}
