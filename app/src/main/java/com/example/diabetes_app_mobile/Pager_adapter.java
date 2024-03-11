package com.example.diabetes_app_mobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.diabetes_app_mobile.fragments.DatabaseReaderFragment;
import com.example.diabetes_app_mobile.fragments.SettingsFragment;
import com.example.diabetes_app_mobile.fragments.Values_taker;

public class Pager_adapter extends FragmentStateAdapter {
    public Pager_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
               return new Values_taker();
            case 1:
                return new DatabaseReaderFragment();
            case 2:
                return new SettingsFragment();
            default:
                return new Values_taker();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
