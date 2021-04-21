package com.uam.proyectocards.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.uam.proyectocards.*
import com.uam.proyectocards.adapter.DeckAdapter
import com.uam.proyectocards.databinding.FragmentDeckListBinding
import com.uam.proyectocards.model.Deck

class DeckListFragment : Fragment() {
    private lateinit var adapter: DeckAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDeckListBinding>(
                inflater,
                R.layout.fragment_deck_list,
                container,
                false)

        adapter = DeckAdapter()
        adapter.data = CardsApplication.decks
        binding.deckRecyclerView?.adapter = adapter


        binding.newCardFab.setOnClickListener {
            val deck = Deck("")
            CardsApplication.addDeck(deck)

            it.findNavController()
                .navigate(
                    DeckListFragmentDirections
                        .actionDeckListFragmentToDeckEditFragment(deck.id))
        }

        return binding.root
    }
}