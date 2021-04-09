package com.example.encode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.encode.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)

        NavigationUI.setupWithNavController(
            binding.navView,
            findNavController(R.id.navHostFragment))
    }

    override fun onPause() {
        super.onPause()
        Timber.i("OnPause")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("OnResume")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("OnStop")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart")
    }



}