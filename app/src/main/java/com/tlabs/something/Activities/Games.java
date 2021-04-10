package com.tlabs.something.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tlabs.something.R;
import com.tlabs.something.Utils.Methods;

public class Games extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        BottomNavigationView bottomNavigation=findViewById(R.id.bottomNavigation);
        Methods.addBottomNavigation(bottomNavigation,R.id.games,this);
        Button ttt=findViewById(R.id.ttt);
        ttt.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),TTT.class)));

        Button sudoku=findViewById(R.id.sudoku);
        sudoku.setOnClickListener(v -> startActivity(new Intent(this,Sudoku.class)));

        Button cows=findViewById(R.id.cows);
        cows.setOnClickListener(v -> startActivity(new Intent(this,BullsAndCows.class)));
    }
}