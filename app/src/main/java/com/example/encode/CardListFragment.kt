package com.example.encode

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.encode.databinding.FragmentCardList2Binding
import com.google.android.material.snackbar.Snackbar

class CardListFragment : Fragment() {
    private lateinit var adapter: CardAdapter
    private lateinit var binding: FragmentCardList2Binding
    @RequiresApi(Build.VERSION_CODES.O)
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
            val card = Card("", "")
            CardsApplication.addCard(card)

            // Navega al fragmento CardEditFragment
            // pasando el id de card como argumento
            it.findNavController()
                .navigate(CardListFragmentDirections
                    .actionCardListFragmentToCardEditFragment(card.id))
        }



        return binding.root
    }
}