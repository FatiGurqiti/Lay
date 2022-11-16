package com.fdev.lay.ui.MainScreen.favourite_list.List_Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fdev.lay.R
import com.fdev.lay.common.Instants
import com.fdev.lay.common.models.MovieAdapterModel

class FavouriteListViewFragment(
    private val pop: CardView,
    private val likeButton: ImageButton,
    private val dislikeButton: ImageButton,
    private val blackBg: ImageView
) : Fragment() {

    private lateinit var viewModel: FavouriteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FavouriteListViewModel::class.java]

        Instants.savedMovieDetailsLiveData.observe(viewLifecycleOwner) { savedMovie ->
            Instants.savedMovieIds.observe(viewLifecycleOwner) { seenMovie ->
                val items: MutableList<MovieAdapterModel> = arrayListOf()
                for (i in 0 until savedMovie.id.size) {
                    items.add(
                        MovieAdapterModel(
                            id = savedMovie.id[i],
                            cardTitleText = savedMovie.title[i],
                            cardImageUrl = savedMovie.imgURL[i],
                            isSeen = savedMovie.id[i] in seenMovie
                        )
                    )
                }
                FavouriteListAdapter( items, viewModel, pop, likeButton, dislikeButton, blackBg, requireActivity()
                ).also { movieAdapter ->
                    view.findViewById<RecyclerView>(R.id.favouriteListRecycleView).adapter = movieAdapter
                }
            }
        }
    }
}