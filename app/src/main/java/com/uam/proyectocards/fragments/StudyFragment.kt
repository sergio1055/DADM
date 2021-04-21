package com.uam.proyectocards.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uam.proyectocards.R
import com.uam.proyectocards.viewmodel.StudyViewModel
import com.uam.proyectocards.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {
    lateinit var binding: FragmentStudyBinding

    private val viewModel: StudyViewModel by lazy {
        ViewModelProvider(this).get(StudyViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private var listener = View.OnClickListener { v ->
        val quality = when(v?.id) {
            R.id.easy_button -> 0
            R.id.doubt_button -> 3
            R.id.hard_button -> 5
            else -> 0
        }

        viewModel.update(quality)

        if(viewModel.card == null) {
            Toast.makeText(this.context, getString(R.string.cards_no_more_cards), Toast.LENGTH_LONG).show()
            binding.answerButton.visibility = View.INVISIBLE
            findNavController().navigate(R.id.action_studyFragment_to_deckListFragment)
        }

        binding.invalidateAll()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentStudyBinding>(
                inflater,
            R.layout.fragment_study,
                container,
                false
        )

        binding.studyViewModel= viewModel
        binding.answerButton.setOnClickListener {
            viewModel.card?.answered = true
            binding.invalidateAll()
        }

        binding.hardButton.setOnClickListener(listener)
        binding.easyButton.setOnClickListener(listener)
        binding.doubtButton.setOnClickListener(listener)

        return binding.root
    }
}