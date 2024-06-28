package com.ilmiddin1701.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilmiddin1701.chatapp.databinding.ItemRv1Binding
import com.ilmiddin1701.chatapp.models.Users
import com.squareup.picasso.Picasso

class UsersAdapter (var rvAction: RvAction,var list: ArrayList<Users>): Adapter<UsersAdapter.Vh>(){

    inner class Vh(var itemRvBinding: ItemRv1Binding): RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(users: Users, position: Int){
            itemRvBinding.userName.text = users.name
            Picasso.get().load(users.photoUrl).into(itemRvBinding.userImage)
            itemRvBinding.root.setOnClickListener {
                rvAction.onClick(users, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRv1Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction{
        fun onClick(users: Users, position: Int)
    }
}