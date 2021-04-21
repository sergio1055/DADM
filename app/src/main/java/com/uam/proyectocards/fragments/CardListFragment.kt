package com.uam.proyectocards.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.uam.proyectocards.CardsApplication
import com.uam.proyectocards.R
import com.uam.proyectocards.adapter.CardAdapter
import com.uam.proyectocards.databinding.FragmentCardListBinding
import com.uam.proyectocards.model.Card


class CardListFragment : Fragment() {
    private lateinit var adapter: CardAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCardListBinding>(
            inflater,
            R.layout.fragment_card_list,
            container,
            false)

        val args = CardListFragmentArgs.fromBundle(requireArguments())

        adapter = CardAdapter()
        adapter.deckId = args.deckId
        adapter.data = adapter.deckId?.let { CardsApplication.getDeck(it)?.cards }!!
        binding.cardRecyclerView.adapter = adapter

        binding.newCardFab.setOnClickListener {
            val card = Card("", "")
            CardsApplication.addCard(card)

            // Navega al fragmento CardEditFragment
            // pasando el id de card como argumento
            it.findNavController()
                .navigate(
                    CardListFragmentDirections.actionCardListFragmentToCardEditFragment(
                        card.id,
                        adapter.deckId
                    )
                )
        }


        return binding.root
    }
}