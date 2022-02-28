package com.lay.fibuv2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.lay.fibuv2.ui.main.SectionsPagerAdapter;
import com.lay.fibuv2.databinding.ActivityFirstTimeBinding;


public class FirstTime extends AppCompatActivity {

    private ActivityFirstTimeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFirstTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        FloatingActionButton fab = binding.fab;


        tabs.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

    }
}