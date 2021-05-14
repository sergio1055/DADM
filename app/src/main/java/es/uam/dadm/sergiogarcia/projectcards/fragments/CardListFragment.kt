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
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
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

    private var user = FirebaseAuth.getInstance().currentUser
    private var auth = FirebaseAuth.getInstance()

    private lateinit var adapter: CardAdapter
    private var deckId: Long = 0
    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_menu, menu)
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
            false
        )

        val args = CardListFragmentArgs.fromBundle(requireArguments())
        deckId = args.deckId
        cardListViewModel.loadDeckId(args.deckId)
        adapter = CardAdapter()
        adapter.data = emptyList()
        adapter.deckId = deckId
        binding.cardRecyclerView.adapter = adapter

        binding.newCardFab.setOnClickListener {
            if (user != null) {
                val card = Card("", "", deckId = deckId, userId = user.uid)
                executor.execute {
                    CardDatabase.getInstance(cardListViewModel.getApplication()).cardDao.addCard(
                        card
                    )
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

        }

        cardListViewModel.deckWithCards.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it[0].cards
                adapter.notifyDataSetChanged()
            })


        return binding.root
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