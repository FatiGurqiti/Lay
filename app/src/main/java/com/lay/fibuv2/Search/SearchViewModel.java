package com.lay.fibuv2.Search;
import androidx.lifecycle.ViewModel;
import com.lay.fibuv2.api.SearchAPI;

public class SearchViewModel extends ViewModel {

    public void prepareResults(String searchContent) {
        SearchAPI.autoCompleteAPI(searchContent);
    }

}
