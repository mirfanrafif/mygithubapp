package com.mirfanrafif.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirfanrafif.consumerapp.databinding.UserListItemBinding

class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    val listFavourites = ArrayList<Favorite>()

    fun setData(data: ArrayList<Favorite>) {
        listFavourites.clear()
        listFavourites.addAll(data)
        notifyDataSetChanged()
    }

    class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = UserListItemBinding.bind(itemView)

        fun bind(item: Favorite) {
            binding.tvItemNama.text = item.login
            Glide.with(itemView).load(item.imageUrl).into(binding.imgItemAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.bind(listFavourites[position])
    }

    override fun getItemCount(): Int {
        return listFavourites.size
    }
}