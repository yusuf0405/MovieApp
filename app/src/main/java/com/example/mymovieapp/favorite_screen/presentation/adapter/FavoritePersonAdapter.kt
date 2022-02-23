package com.example.mymovieapp.favorite_screen.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Utils.Companion.POSTER_BASE_URL
import com.example.mymovieapp.databinding.FavPersonItemBinding
import com.example.mymovieapp.favorite_screen.domain.models.FavoritePerson
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

interface FavPersonItemOnClick {
    fun deletePerson(person: FavoritePerson)
}

class FavoritePersonAdapter(private val actionListener: FavPersonItemOnClick) :
    RecyclerView.Adapter<FavoritePersonAdapter.PersonViewHolder>() {
    var favPersonList: List<FavoritePerson> = emptyList()
        set(newValue) {
            val diffCallBack = FavPersonDiffCallBack(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class PersonViewHolder(private val binding: FavPersonItemBinding) :
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
            LayoutInflater.from(parent.context).inflate(R.layout.fav_person_item, parent, false)
        val binding = FavPersonItemBinding.bind(layoutInflater)
        return PersonViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(favPersonList[position])
    }

    override fun getItemCount(): Int = favPersonList.size

}