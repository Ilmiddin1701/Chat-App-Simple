package com.ilmiddin1701.chatapp.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ilmiddin1701.chatapp.R
import com.ilmiddin1701.chatapp.adapters.MessageAdapter
import com.ilmiddin1701.chatapp.databinding.FragmentChatBinding
import com.ilmiddin1701.chatapp.models.MyMessage
import com.ilmiddin1701.chatapp.models.Users
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date

class ChatFragment : Fragment() {
    private val binding by lazy { FragmentChatBinding.inflate(layoutInflater) }

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var currentUserUID: String
    private lateinit var list: ArrayList<MyMessage>
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor = Color.parseColor("#1B2026")
        val media = MediaPlayer.create(context, R.raw.sound_send)

        list = ArrayList()
        currentUserUID = arguments?.getString("currentUserUID").toString()
        val userDetails = arguments?.getSerializable("keyUser") as Users

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")

        binding.title.text = userDetails.name
        val toUserPhotoUrl = userDetails.photoUrl
        Picasso.get().load(toUserPhotoUrl).into(binding.userImage)
        messageAdapter = MessageAdapter(list, currentUserUID ?: "")
        binding.rv.adapter = messageAdapter

        binding.apply {
            btnSend.setOnClickListener {
                val text = edtMessage.text.toString()
                if (text.isNotBlank()) {
                    val message = MyMessage(text, userDetails.uid, currentUserUID, getDate())
                    val key = reference.push().key
                    reference.child(userDetails.uid ?: "").child("messages").child(currentUserUID)
                        .child(key ?: "").setValue(message)

                    reference.child(currentUserUID).child("messages").child(userDetails.uid ?: "")
                        .child(key ?: "").setValue(message)

                    media.start()
                    binding.edtMessage.text.clear()
                }
            }

            reference.child(currentUserUID).child("messages").child(userDetails.uid ?: "")
                .addValueEventListener(object : ValueEventListener {

                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    val children = snapshot.children
                    for (child in children) {
                        val message = child.getValue(MyMessage::class.java)
                        if (message != null) {
                            list.add(message)
                        }
                    }
                    messageAdapter.notifyItemInserted(list.size - 1)
                    binding.rv.scrollToPosition(list.size - 1)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            })

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

    private fun getDate(): String {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("HH:mm\ndd.MM.yyyy")
        return simpleDateFormat.format(date)
    }
}