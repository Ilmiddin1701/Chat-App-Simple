package com.ilmiddin1701.chatapp.utils

import androidx.activity.result.ActivityResultLauncher

object MyData {
    var writePermissionGranted = false
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
}