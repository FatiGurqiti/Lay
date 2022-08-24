package com.fdev.lay;

import static org.junit.jupiter.api.Assertions.*;

import com.fdev.lay.presentation.ui.main.HomePage;
import com.fdev.lay.presentation.ui.search_fragment.SearchFragment;
import com.fdev.lay.presentation.ui.favourite_list.FavouriteListFragment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MainLoggedInTest{


    @Test
    @Tag("Username")
    public void ShouldGetTheUserNameCorrect() {
        assertEquals(HomePage.getUsername(), FavouriteListFragment.getUsername());
    }

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