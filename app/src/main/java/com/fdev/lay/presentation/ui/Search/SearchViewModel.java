package com.fdev.lay.presentation.ui.Search;
import androidx.lifecycle.ViewModel;
import com.fdev.lay.api.SearchAPI;

public class SearchViewModel extends ViewModel {

    public void prepareResults(String searchContent) {
        SearchAPI.autoCompleteAPI(searchContent);
    }

}
