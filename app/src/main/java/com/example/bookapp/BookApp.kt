package com.example.bookapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookApp : Application() {
    companion object {
        lateinit var instance: BookApp
    }

    init {
        instance = this
    }
}
