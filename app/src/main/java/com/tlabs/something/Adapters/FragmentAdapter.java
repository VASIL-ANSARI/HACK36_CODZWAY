package com.tlabs.something.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tlabs.something.Fragments.LoginFragment;
import com.tlabs.something.Fragments.SignUpFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==1)
            return new SignUpFragment();
        else
            return new LoginFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
