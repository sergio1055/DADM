package com.uam.proyectocards.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.uam.proyectocards.CardAdapter
import com.uam.proyectocards.CardsApplication
import com.uam.proyectocards.R
import com.uam.proyectocards.databinding.FragmentCardListBinding

class CardListFragment : Fragment() {
    private lateinit var adapter: CardAdapter
    private lateinit var binding: FragmentCardListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCardListBinding>(
            inflater,
            R.layout.fragment_card_list,
            container,
            false)
        adapter = CardAdapter()
        adapter.data = CardsApplication.cards
        binding.cardRecyclerView.adapter = adapter

        binding.cardsListStudyButton.setOnClickListener {
            if(CardsApplication.numberOfCardsLeft() > 0) {
                it.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment2)
            }

            else {
                Toast.makeText(activity, R.string.no_more_cards, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}