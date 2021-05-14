package es.uam.dadm.sergiogarcia.projectcards.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import es.uam.dadm.sergiogarcia.projectcards.R
import es.uam.dadm.sergiogarcia.projectcards.activities.SettingsActivity
import es.uam.dadm.sergiogarcia.projectcards.adapter.DeckAdapter
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.databinding.FragmentDeckListBinding
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import es.uam.dadm.sergiogarcia.projectcards.model.Deck
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.DeckListFirebaseViewModel
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.DeckListViewModel
import java.util.concurrent.Executors
import kotlin.random.Random

class DeckListFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var adapter: DeckAdapter
    private val DATABASENAME = "decks"


    private var reference = FirebaseDatabase
        .getInstance()
        .getReference(DATABASENAME)

    private var user = FirebaseAuth.getInstance().currentUser
    private var auth = FirebaseAuth.getInstance()

    private val deckListViewModel by lazy {
        ViewModelProvider(this).get(DeckListViewModel::class.java)
    }

    private val deckListFirebaseViewModel by lazy {
        ViewModelProvider(this).get(DeckListFirebaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDeckListBinding>(
            inflater,
            R.layout.fragment_deck_list,
            container,
            false
        )

        adapter = DeckAdapter()
        binding.deckRecyclerView?.adapter = adapter
        adapter.data = emptyList()


        binding.newCardFab.setOnClickListener {
            val deck = Deck("", Random.nextLong(100), userId = user.uid)
            executor.execute {
                CardDatabase.getInstance(deckListViewModel.getApplication()).cardDao.addDeck(deck)
            }

            it.findNavController()
                .navigate(
                    DeckListFragmentDirections
                        .actionDeckListFragmentToDeckEditFragment(deck.id)
                )
        }


        deckListViewModel.decks.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it
                adapter.notifyDataSetChanged()
            })



        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_menu, menu)
    }

    private fun uploadDeckInfo() {
        if (user != null) {
            adapter.data.forEach {
                reference.child(user.uid).setValue(it)
            }
        }
    }

    private fun downloadDeckInfo() {
        removeCardsFromRoom()

        deckListFirebaseViewModel.decks.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it
                adapter.notifyDataSetChanged()
            }
        )
    }

    private fun removeCardsFromRoom() {
        executor.execute {
            adapter.data.forEach {
                CardDatabase.getInstance(requireContext()).cardDao.removeDeck(it.deck!!)
                it.cards.forEach {
                    CardDatabase.getInstance(requireContext()).cardDao.removeCard(it)
                }
            }
        }
    }

    private fun logOut() {
        auth.signOut()
        SettingsActivity.setLogged(requireContext(), false)
        this.findNavController().navigate(R.id.action_deckListFragment_to_authentication_fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }

            R.id.upload_firebase -> {
                uploadDeckInfo()
            }

            R.id.log_out -> {
                logOut()
            }

            R.id.download_firebase -> {
                downloadDeckInfo()
            }
        }

        return super.onOptionsItemSelected(item)
    }


}