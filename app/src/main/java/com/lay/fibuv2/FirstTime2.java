package com.lay.fibuv2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.lay.fibuv2.ui.main.SectionsPagerAdapter;
import com.lay.fibuv2.databinding.FragmentFirstTime2Binding;

public class FirstTime2 extends AppCompatActivity {

    private FragmentFirstTime2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentFirstTime2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());



    }
}