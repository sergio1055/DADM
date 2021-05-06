package es.uam.dadm.sergiogarcia.projectcards.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.database.FirebaseDatabase
import es.uam.dadm.sergiogarcia.projectcards.R
import es.uam.dadm.sergiogarcia.projectcards.activities.SettingsActivity
import es.uam.dadm.sergiogarcia.projectcards.adapter.CardAdapter
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.databinding.FragmentCardListBinding
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.CardListViewModel
import java.util.concurrent.Executors


class CardListFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()
    private val DATABASENAME = "tarjetas"

    private var reference = FirebaseDatabase
        .getInstance()
        .getReference(DATABASENAME)

    private lateinit var adapter: CardAdapter
    private var deckId : Long = 0
    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

            reference.child(card.id).setValue(card)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_card_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}