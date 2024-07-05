package com.ilmiddin1701.chatapp.fragments

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.chatapp.databinding.FragmentImageViewBinding
import com.ilmiddin1701.chatapp.models.MyMessage
import com.ilmiddin1701.chatapp.utils.MyData
import com.ilmiddin1701.chatapp.utils.StorageUtil.sdk24AdnUp
import com.squareup.picasso.Picasso
import java.io.IOException
import java.util.UUID

class ImageViewFragment : Fragment() {
    private val binding by lazy { FragmentImageViewBinding.inflate(layoutInflater) }
    private var vis = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor = Color.BLACK
        val imageDetail = arguments?.getSerializable("imageDetail") as MyMessage
        binding.apply {
            image.setOnClickListener {
                if (!vis) {
                    line1.visibility = View.VISIBLE
                    vis = true
                } else {
                    line1.visibility = View.GONE
                    vis = false
                }
            }
            Picasso.get().load(imageDetail.image).into(image)
            title.text = imageDetail.date
            btnSave.setOnClickListener {
                if (MyData.writePermissionGranted) {
                    saveToExternalStorage(UUID.randomUUID().toString(), image.drawToBitmap())
                    Toast.makeText(context, "Rasm ytuklab olindi", Toast.LENGTH_SHORT).show()
                }
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

    private fun saveToExternalStorage(displayName: String, bmp: Bitmap): Boolean {
        val imageCollection = sdk24AdnUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image.jpeg")
        }
        return try {
            requireActivity().contentResolver.insert(imageCollection, contentValues)?.also { uri ->
                requireActivity().contentResolver.openOutputStream(uri).use { outputStream ->
                    if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, outputStream!!)) {
                        throw IOException("Couldn't save bitmap")
                    }
                }
            } ?: throw IOException("Couldn't create MediaStore entry")
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}