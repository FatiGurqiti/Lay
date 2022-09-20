package com.fdev.lay;

import static org.junit.jupiter.api.Assertions.*;

import com.fdev.lay.ui.MainScreen.HomePage;
import com.fdev.lay.ui.MainScreen.search_fragment.SearchFragment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MainLoggedInTest{
    @Test
    @Tag("Quota")
    void ShouldGetQuotaCorrect() {
        assertEquals(HomePage.getQuota(), SearchFragment.getQuota());
    }

    @Test
    @Tag("TopRates")
    void ShouldGetTopRatesCorrect() {
        assertEquals(HomePage.getTopRatedId(), SearchFragment.getid());
        assertEquals(HomePage.getTopRatedImg(), SearchFragment.getimg());
        assertEquals(HomePage.getTopRatedName(), SearchFragment.getname());
        assertEquals(HomePage.getTopRatedId(), SearchFragment.getrate());
    }


}