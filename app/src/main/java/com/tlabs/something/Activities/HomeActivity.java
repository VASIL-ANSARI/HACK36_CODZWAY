package com.tlabs.something.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tlabs.something.Adapters.BannerAdapter;
import com.tlabs.something.Adapters.CategoryAdapter;
import com.tlabs.something.R;
import com.tlabs.something.Utils.Methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigation=findViewById(R.id.bottomNavigation);
        Methods.addBottomNavigation(bottomNavigation,R.id.home,this);

        BannerAdapter bannerAdapter=new BannerAdapter(this,Arrays.asList(R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,
                R.drawable.banner4,R.drawable.banner5));

        RecyclerView bannerRecyclerView=findViewById(R.id.horizontalRecyclerView);
        bannerRecyclerView.setAdapter(bannerAdapter);

        CategoryAdapter categoryAdapter=new CategoryAdapter(this,Arrays.asList("Shoes","Electronics","Groceries","Clothes","Toys"),
                Arrays.asList(R.drawable.shoes,R.drawable.electronics,R.drawable.groceries,R.drawable.clothes,R.drawable.toys));
        RecyclerView categoryRecyclerView=findViewById(R.id.recyclerView);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categoryRecyclerView.setAdapter(categoryAdapter);



        }

}