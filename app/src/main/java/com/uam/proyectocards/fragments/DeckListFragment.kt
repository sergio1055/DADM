package com.uam.proyectocards.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.uam.proyectocards.*
import com.uam.proyectocards.adapter.DeckAdapter
import com.uam.proyectocards.database.CardDatabase
import com.uam.proyectocards.databinding.FragmentDeckListBinding
import com.uam.proyectocards.model.Deck
import com.uam.proyectocards.viewmodel.DeckListViewModel
import java.util.concurrent.Executors
import kotlin.random.Random

class DeckListFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var adapter: DeckAdapter

    private val deckListViewModel by lazy {
        ViewModelProvider(this).get(DeckListViewModel::class.java)
    }

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
        adapter.data = emptyList()
        binding.deckRecyclerView?.adapter = adapter


        binding.newCardFab.setOnClickListener {
            val deck = Deck("", Random.nextLong(100))
            executor.execute {
                CardDatabase.getInstance(deckListViewModel.getApplication()).cardDao.addDeck(deck)
            }

            it.findNavController()
                .navigate(
                    DeckListFragmentDirections
                        .actionDeckListFragmentToDeckEditFragment(deck.id))
        }

        deckListViewModel.decks.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it
                adapter.notifyDataSetChanged()
            })

        return binding.root
    }
}