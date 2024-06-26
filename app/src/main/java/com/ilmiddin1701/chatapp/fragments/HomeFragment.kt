package com.ilmiddin1701.chatapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.chatapp.R
import com.ilmiddin1701.chatapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor = Color.parseColor("#1C1D1F")
        binding.apply {
            binding.root.setOnClickListener {
                findNavController().navigate(R.id.chatFragment)
            }
        }
        return binding.root
    }
}