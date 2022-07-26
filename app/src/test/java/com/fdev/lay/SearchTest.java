package com.fdev.lay;

import com.fdev.lay.presentation.ui.Search.Search;
import com.fdev.lay.presentation.ui.search_fragment.SearchFragment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SearchTest {

    @Test
    @Tag("Search Bar Text")
    public void ShouldGetSearchTextCorrent(){
        assertEquals(SearchFragment.getSearchbarText(), Search.getSearchContent());
    }
}