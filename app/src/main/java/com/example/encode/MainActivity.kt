package com.example.encode

import android.graphics.ColorSpace
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.encode.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import timber.log.Timber
import java.time.LocalDateTime
import java.lang.Exception

object Model {
    @RequiresApi(Build.VERSION_CODES.O)
    var card: Card = Card("Despertarse", "To wake up")
}

private const val ANSWERED_KEY = "es.uam.eps.dadm.cards:answered"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var player : Player
    @RequiresApi(Build.VERSION_CODES.O)
    var card = Card("Tree", "Árbol")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = Player(this.lifecycle)
        Timber.i("onCreate CALLED")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.card = Model.card
        binding.apply {
            answerButton.setOnClickListener {
                Model.card?.answered = true
                invalidateAll()
            }
        }

        binding.invalidateAll()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        card.answered = savedInstanceState?.getBoolean(ANSWERED_KEY) ?: false
    }

    override fun onStart() {
        super.onStart()
        Timber.i("OnStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("OnResume")
        binding.invalidateAll()
    }

    override fun onPause() {
        super.onPause()
        Timber.i("OnPAuse");
    }

    override fun onStop() {
        super.onStop()
        Timber.i("OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Timber.i("onSaveInstanceState called")
        outState.putBoolean(ANSWERED_KEY, card.answered)
    }
}