package com.example.mymovieapp.screen_favorite.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Cons.Companion.POSTER_BASE_URL
import com.example.mymovieapp.databinding.ItemFavPersonBinding
import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson
import com.squareup.picasso.Picasso


class FavPersonDiffCallBack(
    private val oldNotes: List<FavoritePerson>,
    private val newNotes: List<FavoritePerson>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldNotes.size

    override fun getNewListSize(): Int = newNotes.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNotes[oldItemPosition]
        val newNote = newNotes[newItemPosition]
        return oldNote.id == newNote.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNotes[oldItemPosition]
        val newNote = newNotes[newItemPosition]
        return oldNote == newNote
    }

}


class FavoritePersonAdapter(private val actionListener: FavItemOnClick) :
    RecyclerView.Adapter<FavoritePersonAdapter.PersonViewHolder>() {
    var favPersonList: List<FavoritePerson> = emptyList()
        set(newValue) {
            val diffCallBack = FavPersonDiffCallBack(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class PersonViewHolder(private val binding: ItemFavPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: FavoritePerson) {
            binding.apply {
                val poster = POSTER_BASE_URL + person.profilePath
                Picasso.get()
                    .load(poster)
                    .placeholder(R.drawable.ic_placeholder_no_text)
                    .into(favPosterPerson)
                favNamePerson.text = person.name
                deletePerson.setOnClickListener {
                    actionListener.deletePerson(person = person)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav_person, parent, false)
        val binding = ItemFavPersonBinding.bind(layoutInflater)
        return PersonViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(favPersonList[position])
    }

    override fun getItemCount(): Int = favPersonList.size

}