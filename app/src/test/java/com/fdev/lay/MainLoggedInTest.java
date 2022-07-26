package com.fdev.lay;

import static org.junit.jupiter.api.Assertions.*;

import com.fdev.lay.presentation.ui.search_fragment.SearchFragment;
import com.fdev.lay.presentation.ui.favourite_list.FavouriteListFragment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MainLoggedInTest{


    @Test
    @Tag("Username")
    public void ShouldGetTheUserNameCorrect() {
        assertEquals(MainLoggedIn.getUsername(), FavouriteListFragment.getUsername());
    }

    @Test
    @Tag("Quota")
    void ShouldGetQuotaCorrect() {
        assertEquals(MainLoggedIn.getQuota(), SearchFragment.getQuota());
    }


    @Test
    @Tag("TopRates")
    void ShouldGetTopRatesCorrect() {
        assertEquals(MainLoggedIn.getTopRatedId(), SearchFragment.getid());
        assertEquals(MainLoggedIn.getTopRatedImg(), SearchFragment.getimg());
        assertEquals(MainLoggedIn.getTopRatedName(), SearchFragment.getname());
        assertEquals(MainLoggedIn.getTopRatedId(), SearchFragment.getrate());
    }


}