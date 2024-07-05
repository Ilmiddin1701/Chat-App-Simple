package com.ilmiddin1701.chatapp.utils

import android.os.Build

object StorageUtil {
    inline fun <T> sdk24AdnUp(onSdk24: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            onSdk24()
        } else null
    }
}