package com.example.encode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.encode.databinding.FragmentCardList2Binding
import com.google.android.material.snackbar.Snackbar

class CardListFragment : Fragment() {
    private lateinit var adapter: CardAdapter
    private lateinit var binding: FragmentCardList2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCardList2Binding>(
            inflater,
            R.layout.fragment_card_list2,
            container,
            false)
        adapter = CardAdapter()
        adapter.data = CardsApplication.cards
        binding.cardRecyclerView.adapter = adapter

        binding.newCardFab.setOnClickListener {
            Snackbar.make(it, "Tarjeta añadida", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }
}