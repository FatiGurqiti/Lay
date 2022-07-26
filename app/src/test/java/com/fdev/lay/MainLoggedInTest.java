package com.fdev.lay;

import static org.junit.jupiter.api.Assertions.*;

import com.fdev.lay.presentation.ui.dashboard.DashboardFragment;
import com.fdev.lay.presentation.ui.home.HomeFragment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MainLoggedInTest{


    @Test
    @Tag("Username")
    public void ShouldGetTheUserNameCorrect() {
        assertEquals(MainLoggedIn.getUsername(), HomeFragment.getUsername());
    }

    @Test
    @Tag("Quota")
    void ShouldGetQuotaCorrect() {
        assertEquals(MainLoggedIn.getQuota(), DashboardFragment.getQuota());
    }


    @Test
    @Tag("TopRates")
    void ShouldGetTopRatesCorrect() {
        assertEquals(MainLoggedIn.getTopRatedId(), DashboardFragment.getid());
        assertEquals(MainLoggedIn.getTopRatedImg(), DashboardFragment.getimg());
        assertEquals(MainLoggedIn.getTopRatedName(), DashboardFragment.getname());
        assertEquals(MainLoggedIn.getTopRatedId(), DashboardFragment.getrate());
    }


}