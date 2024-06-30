package com.ilmiddin1701.chatapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ilmiddin1701.chatapp.R
import com.ilmiddin1701.chatapp.adapters.UsersAdapter
import com.ilmiddin1701.chatapp.databinding.FragmentHomeBinding
import com.ilmiddin1701.chatapp.models.Users
import com.squareup.picasso.Picasso

class HomeFragment : Fragment(), UsersAdapter.RvAction {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var auth: FirebaseAuth
    private lateinit var list: ArrayList<Users>
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor = Color.parseColor("#1C1D1F")

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")

        binding.apply {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

            auth = FirebaseAuth.getInstance()

            registerForContextMenu(btnUser)

            Picasso.get().load(auth.currentUser?.photoUrl).into(binding.btnUser)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list = ArrayList()
                    val children = snapshot.children
                    for (child in children) {
                        val user = child.getValue(Users::class.java)
                        if (user?.uid != auth.uid) {
                            list.add(user!!)
                        }
                    }
                    rv.adapter = UsersAdapter(this@HomeFragment, list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
        return binding.root
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.my_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logOut -> {
                googleSignInClient.signOut()
                auth.signOut()
                findNavController().popBackStack()
                findNavController().navigate(R.id.signInFragment)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onClick(users: Users) {
        findNavController().navigate(
            R.id.chatFragment,
            bundleOf("keyUser" to users, "currentUserUID" to auth.uid.toString(), "currentUserPhotoUrl" to auth.currentUser?.photoUrl.toString())
        )
    }
}