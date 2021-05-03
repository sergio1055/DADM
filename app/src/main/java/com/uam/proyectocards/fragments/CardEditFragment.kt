package com.uam.proyectocards.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.uam.proyectocards.R
import com.uam.proyectocards.database.CardDatabase
import com.uam.proyectocards.databinding.FragmentCardEditBinding
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.viewmodel.CardEditViewModel
import java.util.concurrent.Executors

class CardEditFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()
    lateinit var card: Card
    lateinit var binding: FragmentCardEditBinding
    lateinit var question: String
    lateinit var answer: String
    var deckId : Long = 0

    private val cardEditviewModel by lazy {
        ViewModelProvider(this).get(CardEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentCardEditBinding>(
            inflater,
            R.layout.fragment_card_edit,
            container,
            false
        )

        val args = CardEditFragmentArgs.fromBundle(requireArguments())
        deckId = args.deckId

        cardEditviewModel.loadCardId(args.cardId)
        cardEditviewModel.card.observe(
            viewLifecycleOwner,
            Observer {
                card = it
                binding.card = card
                question = card.question
                answer = card.answer
            }
        )


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val questionTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                card.question = s.toString()
            }
        }

        val answerTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                card.answer = s.toString()
            }
        }

        binding.questionEditText.addTextChangedListener(questionTextWatcher)
        binding.answerEditText.addTextChangedListener(answerTextWatcher)


        binding.acceptCardEditButton.setOnClickListener {
            val cardDatabase =
                CardDatabase.getInstance(context = cardEditviewModel.getApplication())
            executor.execute {
                cardDatabase.cardDao.update(card)
            }

            Snackbar.make(it, R.string.update_card_text, Snackbar.LENGTH_LONG).show()

            it.findNavController()
                .navigate(
                    CardEditFragmentDirections.actionCardEditFragmentToCardListFragment2(deckId)
                )
        }

        binding.cancelCardEditButton.setOnClickListener {
            card.question = question
            card.answer = answer

            if (card.question == "" || card.answer == "") {
                val cardDatabase =
                    CardDatabase.getInstance(context = cardEditviewModel.getApplication())
                executor.execute {
                    cardDatabase.cardDao.removeCard(card)
                }
            }
            it.findNavController()
                .navigate(
                    CardEditFragmentDirections.actionCardEditFragmentToCardListFragment2(deckId)
                )
        }

        binding.removeCardButton.setOnClickListener {
            val cardDatabase =
                CardDatabase.getInstance(context = cardEditviewModel.getApplication())

            executor.execute {
                cardDatabase.cardDao.removeCard(card)
            }

            Snackbar.make(it, R.string.remove_card_text, Snackbar.LENGTH_LONG).show()

            it.findNavController()
                .navigate(
                    CardEditFragmentDirections.actionCardEditFragmentToCardListFragment2(deckId)
                )
        }
    }
}