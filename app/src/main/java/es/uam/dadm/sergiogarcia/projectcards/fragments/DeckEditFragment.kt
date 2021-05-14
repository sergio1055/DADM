package es.uam.dadm.sergiogarcia.projectcards.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import es.uam.dadm.sergiogarcia.projectcards.R
import es.uam.dadm.sergiogarcia.projectcards.model.Deck
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.databinding.FragmentDeckEditBinding
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.DeckEditViewModel
import java.util.concurrent.Executors


class DeckEditFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()
    lateinit var deck: Deck
    lateinit var cards: List<Card>
    lateinit var binding: FragmentDeckEditBinding
    lateinit var name: String

    private val deckEditViewModel by lazy {
        ViewModelProvider(this).get(DeckEditViewModel::class.java)
    }

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
        deckEditViewModel.loadDeckId(args.deckId)
        deckEditViewModel.deckWithCards.observe(viewLifecycleOwner) {
            deck = it[0].deck!!
            cards = it[0].cards
            binding.deck = deck
            name = deck.name
        }


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
            val cardDatabase =
                CardDatabase.getInstance(context = deckEditViewModel.getApplication())

            executor.execute {
                cardDatabase.cardDao.update(deck)
            }

            Snackbar.make(it, R.string.update_deck_text, Snackbar.LENGTH_LONG).show()
            it.findNavController().navigate(R.id.action_deckEditFragment_to_deckListFragment)
        }

        binding.cancelDeckEditButton?.setOnClickListener {
            deck.name = name

            if (deck.name == "") {
                val cardDatabase =
                    CardDatabase.getInstance(context = deckEditViewModel.getApplication())
                executor.execute {
                    cardDatabase.cardDao.removeDeck(deck)
                }
            }

            it.findNavController().navigate(R.id.action_deckEditFragment_to_deckListFragment)
        }

        binding.removeDeckButton.setOnClickListener {
            val cardDatabase =
                CardDatabase.getInstance(context = deckEditViewModel.getApplication())

            executor.execute {
                cardDatabase.cardDao.removeDeck(deck)
                cards.forEach {
                    cardDatabase.cardDao.removeCard(it)
                }
            }

            Snackbar.make(it, R.string.remove_deck_text, Snackbar.LENGTH_LONG).show()

            it.findNavController().navigate(R.id.action_deckEditFragment_to_deckListFragment)

        }

    }
}