package com.example.fibuv2;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fibuv2.ui.dashboard.DashboardFragment;
import com.example.fibuv2.ui.home.HomeFragment;
import com.example.fibuv2.ui.login.LoginActivity;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MainLoggedInTest {


    @Test
    @Tag("Username")
    public void ShouldGetTheUserNameCorrect() {
        assertEquals(MainLoggedIn.getUsername(), HomeFragment.getUsername());
    }

    @Test
    @Tag("Quota")
    void ShouldGetQuotaCorrent() {
        assertEquals(MainLoggedIn.getQuota(), DashboardFragment.getQuota());
    }


    @Test
    @Tag("TopRates")
    void ShouldGetTopRatresCorrent() {
        assertEquals(MainLoggedIn.getTopRatedId(), DashboardFragment.getid());
        assertEquals(MainLoggedIn.getTopRatedImg(), DashboardFragment.getimg());
        assertEquals(MainLoggedIn.getTopRatedName(), DashboardFragment.getname());
        assertEquals(MainLoggedIn.getTopRatedId(), DashboardFragment.getrate());
    }

    @Test
    @Tag("Email")
    void ShouldGetEmailCorrect(){
        assertEquals(MainLoggedIn.getEmail(), LoginActivity.getEmailString());
    }

}