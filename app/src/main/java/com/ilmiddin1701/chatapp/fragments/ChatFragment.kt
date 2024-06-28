package com.ilmiddin1701.chatapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.chatapp.R
import com.ilmiddin1701.chatapp.databinding.FragmentChatBinding
import com.ilmiddin1701.chatapp.models.Users
import com.squareup.picasso.Picasso

class ChatFragment : Fragment() {
    private val binding by lazy { FragmentChatBinding.inflate(layoutInflater)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor = Color.parseColor("#1B2026")
        val userDetails = arguments?.getSerializable("keyUser") as Users
        val userPosition = arguments?.getInt("keyUserPosition")
        binding.apply {
            title.text = userDetails.name
            Picasso.get().load(userDetails.photoUrl).into(userImage)
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSend.setOnClickListener {
                Toast.makeText(context, "Yuborilidi", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}