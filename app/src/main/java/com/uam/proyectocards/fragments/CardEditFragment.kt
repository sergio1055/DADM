package com.uam.proyectocards.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.uam.proyectocards.CardsApplication
import com.uam.proyectocards.R
import com.uam.proyectocards.databinding.FragmentCardEditBinding
import com.uam.proyectocards.model.Card

class CardEditFragment : Fragment() {
    lateinit var card : Card
    lateinit var binding: FragmentCardEditBinding
    lateinit var question: String
    lateinit var answer: String
    var deckId : String? = null

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
        card = CardsApplication.getCard(args.cardId)!!
        binding.card = card
        question = card.question
        answer = card.answer


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        String.format(getResources().getString(R.string.id_text), card.id.substring(0,4))
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
            Snackbar.make(it, R.string.create_card_text, Snackbar.LENGTH_LONG).show()
            it.findNavController()
                    .navigate(
                        CardEditFragmentDirections.actionCardEditFragmentToCardListFragment2(deckId)
                    )
        }

        binding.cancelCardEditButton.setOnClickListener {
            card.question = question
            card.answer = answer
            it.findNavController()
                    .navigate(
                        CardEditFragmentDirections.actionCardEditFragmentToCardListFragment2(deckId)
                    )
        }

        binding.removeCardButton.setOnClickListener {
            CardsApplication.removeCard(card)
            Snackbar.make(it, R.string.remove_card_text, Snackbar.LENGTH_LONG).show()

            it.findNavController()
                    .navigate(
                        CardEditFragmentDirections.actionCardEditFragmentToCardListFragment2(deckId)
                    )
        }
    }

}