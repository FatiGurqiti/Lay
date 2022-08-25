package com.fdev.lay.ui.FirstTime;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.fdev.lay.presentation.ui.main.SectionsPagerAdapter;
import com.fdev.lay.databinding.ActivityFirstTimeBinding;

public class FirstTime2 extends AppCompatActivity {

    private ActivityFirstTimeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFirstTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);

    }
}