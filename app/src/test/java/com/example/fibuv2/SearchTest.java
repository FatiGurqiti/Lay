package com.example.fibuv2;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fibuv2.ui.dashboard.DashboardFragment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SearchTest {

    @Test
    @Tag("Search Bar Text")
    public void ShouldGetSearchTextCorrent(){
        assertEquals(DashboardFragment.getSearchbarText(),Search.getSearchContent());
    }
}