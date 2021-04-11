package com.uam.proyectocards.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.uam.proyectocards.R
import com.uam.proyectocards.databinding.ActivityStudyBinding
import com.uam.proyectocards.databinding.FragmentAboutBinding
import com.uam.proyectocards.databinding.FragmentStudyBinding


class AboutFragment : Fragment() {
    lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAboutBinding>(
            inflater,
            R.layout.fragment_about,
            container,
            false
        )

        return binding.root
    }
}