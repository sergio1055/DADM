package com.example.encode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.encode.databinding.FragmentCardList2Binding

class CardListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCardList2Binding>(
            inflater,
            R.layout.fragment_card_list2,
            container,
            false)

        binding.studyButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment2)
        }

        return binding.root
    }
}