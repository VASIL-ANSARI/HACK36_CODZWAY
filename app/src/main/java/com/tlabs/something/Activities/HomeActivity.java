package com.tlabs.something.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tlabs.something.R;
import com.tlabs.something.Utils.Methods;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigation=findViewById(R.id.bottomNavigation);
        Methods.addBottomNavigation(bottomNavigation,R.id.home,this);

    }
}