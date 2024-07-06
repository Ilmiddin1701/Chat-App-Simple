package com.ilmiddin1701.chatapp.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
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
import com.ilmiddin1701.chatapp.databinding.FragmentCreateNewChatBinding
import com.ilmiddin1701.chatapp.models.Users
import com.ilmiddin1701.chatapp.utils.MySharedPreferences
import com.squareup.picasso.Picasso

class CreateNewChatFragment : Fragment(), UsersAdapter.RvAction {
    private val binding by lazy { FragmentCreateNewChatBinding.inflate(layoutInflater) }

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
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

            auth = FirebaseAuth.getInstance()

            edtSearch.addTextChangedListener { text ->
                reference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        list = ArrayList()
                        list.clear()
                        val children = snapshot.children
                        for (child in children) {
                            val user = child.getValue(Users::class.java)
                            if (user?.uid != auth.uid) {
                                if (edtSearch.text.isNotBlank() && user?.name.toString().contains(text.toString())){
                                    list.add(user!!)
                                }
                            }
                        }
                        rv.adapter = UsersAdapter(this@CreateNewChatFragment, list)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        return binding.root
    }

    override fun onClick(users: Users) {
        findNavController().popBackStack()
        findNavController().navigate(
            R.id.chatFragment,
            bundleOf("keyUser" to users, "currentUserUID" to auth.uid.toString(), "currentUserPhotoUrl" to auth.currentUser?.photoUrl.toString())
        )
    }

    private fun showKeyboard(view: View) {
        view.requestFocus()
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onResume() {
        super.onResume()
        showKeyboard(binding.edtSearch)
    }
}