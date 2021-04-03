package com.mirfanrafif.mygithubapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirfanrafif.mygithubapp.models.User
import com.mirfanrafif.mygithubapp.ui.activities.DetailUserActivity

class ListUserAdapter() : RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>() {
    private val listUser = ArrayList<User>()

    class ListUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar : ImageView = itemView.findViewById(R.id.img_item_avatar)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_nama)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ListUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.tvName.text = listUser[position].login
        Glide.with(holder.itemView).load(listUser[position].avatar_url).into(holder.imgAvatar)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, listUser[position].login)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    fun setData(data: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(data)
        notifyDataSetChanged()
    }
}