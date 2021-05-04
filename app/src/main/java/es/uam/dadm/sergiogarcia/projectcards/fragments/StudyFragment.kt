package es.uam.dadm.sergiogarcia.projectcards.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.uam.dadm.sergiogarcia.projectcards.R
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.StudyViewModel
import es.uam.dadm.sergiogarcia.projectcards.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {
    lateinit var binding: FragmentStudyBinding

    private val studyViewModel: StudyViewModel by lazy {
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

        studyViewModel.update(quality)

        if(studyViewModel.card == null) {
            Toast.makeText(
                this.context,
                resources.getString(R.string.no_more_cards),
                Toast.LENGTH_LONG
            ).show()
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

        binding.answerButton.setOnClickListener {
            studyViewModel.card?.answered = true
            binding.invalidateAll()
        }

        binding.hardButton.setOnClickListener(listener)
        binding.easyButton.setOnClickListener(listener)
        binding.doubtButton.setOnClickListener(listener)

        studyViewModel.dueCard.observe(
            viewLifecycleOwner,
            Observer {
                studyViewModel.card = it
                binding.studyViewModel = studyViewModel
                binding.invalidateAll()
            })

        return binding.root
    }
}