package com.uam.proyectocards.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.uam.proyectocards.CardsApplication
import com.uam.proyectocards.model.Deck
import com.uam.proyectocards.R
import com.uam.proyectocards.databinding.FragmentDeckEditBinding


class DeckEditFragment : Fragment() {
    lateinit var deck: Deck
    lateinit var binding: FragmentDeckEditBinding
    lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentDeckEditBinding>(
            inflater,
            R.layout.fragment_deck_edit,
            container,
            false
        )

        val args = DeckEditFragmentArgs.fromBundle(requireArguments())
        deck = CardsApplication.getDeck(args.deckId)!!
        name = deck.name

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val nameTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                deck.name = s.toString()
            }
        }

        binding.nameEditText.addTextChangedListener(nameTextWatcher)

        binding.acceptDeckEditButton?.setOnClickListener {
            Snackbar.make(it, R.string.create_deck_text, Snackbar.LENGTH_LONG).show()
            it.findNavController().navigate(R.id.action_deckEditFragment_to_deckListFragment)
        }

        binding.cancelDeckEditButton?.setOnClickListener {
            deck.name = name
            it.findNavController().navigate(R.id.action_deckEditFragment_to_deckListFragment)
        }
    }
}