package com.example.encode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.encode.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity(), TitleFragment.onTitleFragmentInteractionListener {
    lateinit var binding: ActivityTitleBinding

    override fun onStudy() {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, StudyFragment())
                .addToBackStack("onStudy")
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
        supportActionBar?.hide()

        var fragment = supportFragmentManager
                .findFragmentById(R.id.fragment_container)

        if (fragment == null){
            fragment = TitleFragment()

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }

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