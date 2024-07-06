package com.ilmiddin1701.chatapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.ilmiddin1701.chatapp.utils.MyData

class MainActivity : AppCompatActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { check ->
                MyData.writePermissionGranted = check[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: MyData.writePermissionGranted
            }
        updateOrRequestPermission()
    }

    private fun updateOrRequestPermission() {
        val hasWritePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val minSdk24 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        MyData.writePermissionGranted = hasWritePermission || minSdk24

        val permissionToRequest = mutableListOf<String>()
        if (!MyData.writePermissionGranted) {
            permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionToRequest.toTypedArray())
        }
    }
}