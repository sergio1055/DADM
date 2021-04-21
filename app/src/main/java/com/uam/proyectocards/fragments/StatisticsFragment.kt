package com.uam.proyectocards.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.uam.proyectocards.R
import com.uam.proyectocards.databinding.FragmentStatisticsBinding
import com.uam.proyectocards.databinding.FragmentStudyBinding
import com.uam.proyectocards.viewmodel.StatisticsViewModel

class StatisticsFragment : Fragment() {
    lateinit var binding: FragmentStatisticsBinding

    private val viewModel: StatisticsViewModel by lazy {
        ViewModelProvider(this).get(StatisticsViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentStatisticsBinding>(
            inflater,
            R.layout.fragment_statistics,
            container,
            false
        )

        binding.statisticsViewModel = viewModel


        return binding.root
    }
}