package com.tlabs.something.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.tlabs.something.Adapters.FragmentAdapter;
import com.tlabs.something.Interfaces.FragmentSwitcher;
import com.tlabs.something.R;

public class MainActivity extends AppCompatActivity implements FragmentSwitcher {

    private ViewPager2 mViewPager2;

    //Fetches the View Pager element from the  layout and attaches it to a Adapter for view switching
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewPager2 = findViewById(R.id.viewPager2);
        FragmentAdapter sfa = new FragmentAdapter(this);
        mViewPager2.setAdapter(sfa);


        //TODO: should save user sign up data until he verifies mail see utility class shared preferences
        Intent i =getIntent();
        if(i!=null&&i.getExtras()!=null)
        {
            changeToFragment(i.getExtras().getInt("fragment"));

        }

    }

    //Implement the method from Fragment Switcher interface
    @Override
    public void changeToFragment(int position) {
        mViewPager2.setCurrentItem(position,true);
    }

    @Override
    public void lockViewPager(boolean value) {
        mViewPager2.setUserInputEnabled(!value);
    }
}
