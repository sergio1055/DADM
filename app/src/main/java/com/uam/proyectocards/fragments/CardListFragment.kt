package com.uam.proyectocards.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.uam.proyectocards.R
import com.uam.proyectocards.adapter.CardAdapter
import com.uam.proyectocards.database.CardDatabase
import com.uam.proyectocards.databinding.FragmentCardListBinding
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.viewmodel.CardListViewModel
import java.util.concurrent.Executors


class CardListFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var adapter: CardAdapter
    private var deckId : Long = 0
    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

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
        deckId = args.deckId
        cardListViewModel.loadDeckId(args.deckId)
        adapter = CardAdapter()
        adapter.data = emptyList()
        adapter.deckId = deckId
        binding.cardRecyclerView.adapter = adapter

        binding.newCardFab.setOnClickListener {
            val card = Card("", "", deckId = deckId)
            executor.execute {
                CardDatabase.getInstance(cardListViewModel.getApplication()).cardDao.addCard(card)
            }

            // Navega al fragmento CardEditFragment
            // pasando el id de card como argumento
            it.findNavController()
                .navigate(
                    CardListFragmentDirections.actionCardListFragmentToCardEditFragment(
                        card.id,
                        deckId
                    )
                )
        }

        cardListViewModel.deckWithCards.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it[0].cards
                adapter.notifyDataSetChanged()
            })


        return binding.root
    }
}