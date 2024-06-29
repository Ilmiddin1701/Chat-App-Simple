package com.ilmiddin1701.chatapp.fragments

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ilmiddin1701.chatapp.R
import com.ilmiddin1701.chatapp.adapters.UsersAdapter
import com.ilmiddin1701.chatapp.databinding.FragmentChatBinding
import com.ilmiddin1701.chatapp.models.Users
import com.ilmiddin1701.chatapp.utils.MyObject
import com.squareup.picasso.Picasso

class ChatFragment : Fragment() {
    private val binding by lazy { FragmentChatBinding.inflate(layoutInflater) }

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val media = MediaPlayer.create(context, R.raw.sound_send)
        requireActivity().window.statusBarColor = Color.parseColor("#1B2026")
        val userDetails = arguments?.getString("keyUserUID")

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("messages")

        binding.apply {
            MyObject.reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    for (child in children) {
                        val user = child.getValue(Users::class.java)
                        if (user?.uid == userDetails) {
                            title.text = user?.name
                            Picasso.get().load(user?.photoUrl).into(userImage)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            })
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSend.setOnClickListener {
                media.start()
            }
        }
        return binding.root
    }
}