package com.example.mymovieapp.screen_person.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Cons.Companion.POSTER_BASE_URL
import com.example.mymovieapp.databinding.ItemPersonBinding
import com.example.mymovieapp.screen_person.domain.models.Person
import com.squareup.picasso.Picasso


interface PersonItemOnClickListener {
    fun showDetailsPerson(id: Int)
}

class PersonAdapter(private val actionListener: PersonItemOnClickListener) :
    PagingDataAdapter<Person, PersonAdapter.HomeNewsViewHolder>(PersonDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewsViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        val binding = ItemPersonBinding.bind(inflater)
        return HomeNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class HomeNewsViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            val poster = POSTER_BASE_URL + person.profile
            binding.apply {
                Picasso.get()
                    .load(poster)
                    .placeholder(R.drawable.ic_placeholder_no_text)
                    .error(R.drawable.ic_placeholder_no_text)
                    .into(imageView2)
                textView.text = person.name


            }

            itemView.setOnClickListener {
                actionListener.showDetailsPerson(id = person.id)
            }
        }
    }
}


private object PersonDiffItemCallback : DiffUtil.ItemCallback<Person>() {

    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.name == newItem.name && oldItem.id == newItem.id
    }
}