package com.fdev.lay.ui.MainScreen.favourite_list.List_Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fdev.lay.R
import com.squareup.picasso.Picasso

data class MovieAdapterModel(
    val id: String,
    val cardImageUrl: String,
    val cardTitleText: String,
    val isSeen: Boolean
)

class FavouriteListAdapter(
    private val items: List<MovieAdapterModel>,
    private val viewModel: FavouriteListViewModel,
    private val ratePop: CardView,
    private val likeButton: ImageView,
    private val dislikeButton: ImageButton,
    private val blackFilter: ImageView
) : RecyclerView.Adapter<FavouriteListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cardImage)
        val title: TextView = itemView.findViewById(R.id.cardTitleText)
        val icon: ImageView = itemView.findViewById(R.id.cardSeenLogo)
        var text: TextView = itemView.findViewById(R.id.cardSeenText)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourites_card_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemModel = items[position]

        holder.title.text = itemModel.cardTitleText
        Picasso.get()
            .load(itemModel.cardImageUrl)
            .fit()
            .centerCrop()
            .into(holder.image)

        if (itemModel.isSeen) {
            holder.text.text = "Seen"
            holder.icon.setImageResource(R.drawable.check)

        } else {
            holder.text.text = "Set as seen"
            holder.icon.setImageResource(R.drawable.calendar)

            holder.text.setOnClickListener {
                blackFilter.isVisible = true
                ratePop.isVisible = true
            }

            blackFilter.setOnClickListener {
                it.isGone = true
                ratePop.isGone = true
            }

            likeButton.setOnClickListener {
                viewModel.like(
                    itemModel.id,
                    itemModel.cardTitleText,
                    itemModel.cardImageUrl,
                    true
                )
                viewModel.addSeenMovies(itemModel.id)
                ratePop.isGone = true
                blackFilter.isGone = true
                holder.text.text = "Seen"
                holder.icon.setImageResource(R.drawable.check)
            }

            dislikeButton.setOnClickListener {
                viewModel.like(
                    itemModel.id,
                    itemModel.cardTitleText,
                    itemModel.cardImageUrl,
                    false
                )
                viewModel.addSeenMovies(itemModel.id)
                ratePop.isGone = true
                blackFilter.isGone = true
                holder.text.text = "Seen"
                holder.icon.setImageResource(R.drawable.check)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}