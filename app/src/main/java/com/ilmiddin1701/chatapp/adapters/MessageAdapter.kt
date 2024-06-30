package com.ilmiddin1701.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilmiddin1701.chatapp.databinding.MessageItemReceivedBinding
import com.ilmiddin1701.chatapp.databinding.MessageItemSendBinding
import com.ilmiddin1701.chatapp.models.MyMessage
import com.squareup.picasso.Picasso

class MessageAdapter(val list: ArrayList<MyMessage>, val currentUserUid: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TYPE_FROM = 1
    val TYPE_TO = 0
    inner class ToVh(var receivedItem: MessageItemReceivedBinding): RecyclerView.ViewHolder(receivedItem.root){
        fun onBind(message: MyMessage){
            receivedItem.tvSms.text = message.text
            receivedItem.tvDate.text = message.date
            Picasso.get().load(message.toUserPhotoUrl).into(receivedItem.userImage)
        }
    }

    inner class FromVh(var sendItem: MessageItemSendBinding): RecyclerView.ViewHolder(sendItem.root){
        fun onBind(message: MyMessage){
            sendItem.tvSms.text = message.text
            sendItem.tvDate.text = message.date
            Picasso.get().load(message.fromUserPhotoUrl).into(sendItem.userImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType==0){
            ToVh(MessageItemReceivedBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            FromVh(MessageItemSendBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ToVh){
            holder.onBind(list[position])
        }else if (holder is FromVh){
            holder.onBind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].fromUID == currentUserUid) {
            TYPE_FROM
        } else {
            TYPE_TO
        }
    }
}