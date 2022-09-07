package com.fdev.lay.ui.Search;
import androidx.lifecycle.ViewModel;
import com.fdev.lay.data.remote.api.SearchAPI;

public class SearchViewModel extends ViewModel {

    public void prepareResults(String searchContent) {
        SearchAPI.autoCompleteAPI(searchContent);
    }

}
