package com.uam.proyectocards

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.uam.proyectocards.databinding.FragmentCardListBinding


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
        adapter = CardAdapter()
        adapter.data = CardsApplication.cards
        binding.cardRecyclerView.adapter = adapter

        binding.newCardFab.setOnClickListener {
            val card = Card("", "")
            CardsApplication.addCard(card)

            // Navega al fragmento CardEditFragment
            // pasando el id de card como argumento
            it.findNavController()
                .navigate(
                    CardListFragmentDirections
                    .actionCardListFragmentToCardEditFragment(card.id))
        }


        return binding.root
    }
}