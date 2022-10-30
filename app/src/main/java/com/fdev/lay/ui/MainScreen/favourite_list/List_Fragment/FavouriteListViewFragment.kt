package com.fdev.lay.ui.MainScreen.favourite_list.List_Fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.fdev.lay.R

class FavouriteListViewFragment(
    pop: CardView,
    blackBg: ImageView
) : Fragment() {

    private lateinit var viewModel: FavouriteListViewModel
    private val ratePop = pop
    private val blackFilter = blackBg

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FavouriteListViewModel::class.java]

        viewModel.savedMovieDetailsLiveData.observe(viewLifecycleOwner) { savedMovie ->

            val items: MutableList<MovieAdapterModel> = arrayListOf()

            for (i in 0 until savedMovie.id.size) {
                items.add(
                    MovieAdapterModel(
                        cardTitleText = savedMovie.title[i],
                        cardImageUrl = savedMovie.imgURL[i],
                        isSeen = false
                    )
                )
            }
            FavouriteListAdapter(items, ratePop, blackFilter).also { movieAdapter ->
                view.findViewById<RecyclerView>(R.id.favouriteListRecycleView)
                    .apply {
                        adapter = movieAdapter
                    }
            }
        }
    }
}