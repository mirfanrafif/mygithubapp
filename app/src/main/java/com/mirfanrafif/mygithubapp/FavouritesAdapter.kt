package com.mirfanrafif.mygithubapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirfanrafif.mygithubapp.databinding.UserListItemBinding
import com.mirfanrafif.mygithubapp.models.FavouritesItem
import com.mirfanrafif.mygithubapp.ui.activities.DetailUserActivity

class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    val listFavourites = ArrayList<FavouritesItem>()

    fun setData(data: ArrayList<FavouritesItem>) {
        listFavourites.clear()
        listFavourites.addAll(data)
        notifyDataSetChanged()
    }

    class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = UserListItemBinding.bind(itemView)

        fun bind(item: FavouritesItem) {
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
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_ID, listFavourites[position].id)
            intent.putExtra(DetailUserActivity.EXTRA_USER, listFavourites[position].login)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listFavourites.size
    }
}