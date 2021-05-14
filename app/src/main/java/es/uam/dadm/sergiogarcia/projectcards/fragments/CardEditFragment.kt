package es.uam.dadm.sergiogarcia.projectcards.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import es.uam.dadm.sergiogarcia.projectcards.R
import es.uam.dadm.sergiogarcia.projectcards.activities.SettingsActivity
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.databinding.FragmentCardEditBinding
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.CardEditViewModel
import java.util.concurrent.Executors

class CardEditFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()
    lateinit var card: Card
    lateinit var binding: FragmentCardEditBinding
    lateinit var question: String
    lateinit var answer: String
    private var auth = FirebaseAuth.getInstance()

    var deckId : Long = 0

    private val cardEditviewModel by lazy {
        ViewModelProvider(this).get(CardEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_menu, menu)
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

    private fun logOut() {
        auth.signOut()
        SettingsActivity.setLogged(requireContext(), false)
        Snackbar.make(requireView(), R.string.logout_success, Snackbar.LENGTH_LONG)
        this.findNavController().navigate(R.id.action_cardEditFragment_to_authentication_fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }

            R.id.log_out -> {
                logOut()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}