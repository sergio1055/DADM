package com.example.encode

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import timber.log.Timber

class MainViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    var card = Card("To wake up", "Despertarse")

    init {
        Timber.i("MainViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }
}