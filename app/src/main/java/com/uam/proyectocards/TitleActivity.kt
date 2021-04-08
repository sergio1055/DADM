package com.uam.proyectocards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.uam.proyectocards.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
        supportActionBar?.hide()

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