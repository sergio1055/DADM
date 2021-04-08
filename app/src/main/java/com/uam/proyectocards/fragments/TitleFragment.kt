package com.uam.proyectocards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uam.proyectocards.CardsApplication
import com.uam.proyectocards.R
import com.uam.proyectocards.databinding.FragmentTitleBinding

class TitleFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false)

        binding.cardsTitleTextView.setOnClickListener { view ->
            if (CardsApplication.numberOfCardsLeft() > 0)
                view.findNavController().navigate(R.id.action_titleFragment_to_cardListFragment)
            else
                Toast.makeText(activity, R.string.no_more_cards, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}