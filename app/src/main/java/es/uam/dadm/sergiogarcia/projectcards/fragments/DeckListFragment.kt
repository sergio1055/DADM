package es.uam.dadm.sergiogarcia.projectcards.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import es.uam.dadm.sergiogarcia.projectcards.R
import es.uam.dadm.sergiogarcia.projectcards.activities.SettingsActivity
import es.uam.dadm.sergiogarcia.projectcards.adapter.DeckAdapter
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.databinding.FragmentDeckListBinding
import es.uam.dadm.sergiogarcia.projectcards.model.Deck
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.DeckListFirebaseViewModel
import es.uam.dadm.sergiogarcia.projectcards.viewmodel.DeckListViewModel
import java.util.concurrent.Executors
import kotlin.random.Random

class DeckListFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var adapter: DeckAdapter
    private val DATABASENAME = "decks"

    private lateinit var menuBar: Menu
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
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menuBar = menu
        inflater.inflate(R.menu.fragment_menu_deck, menu)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var navigationView : NavigationView = requireActivity().findViewById(R.id.navView)
        navigationView.getMenu().findItem(R.id.deckListFragment).setEnabled(true)
        navigationView.getMenu().findItem(R.id.studyFragment).setEnabled(true)
    }
    private fun uploadDeckInfo() {
        if (user != null) {
            adapter.data.forEach {
                reference.child(user.uid).setValue(it)
            }
        }
    }

    private fun downloadDeckInfo() {
        /*  removeCardsFromRoom() */
        adapter.data = emptyList()

        deckListFirebaseViewModel.decks.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it
                adapter.notifyDataSetChanged()
            }
        )
    }

    private fun logOut() {
        auth.signOut()
        SettingsActivity.setLogged(requireContext(), false)
        Toast.makeText(requireContext(), R.string.logout_success, Toast.LENGTH_LONG).show()
        this.findNavController().navigate(R.id.action_deckListFragment_to_authentication_fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_firebase -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }

            R.id.upload_firebase -> {
                if (SettingsActivity.getFirebasePreference(requireContext())!!) {
                    uploadDeckInfo()
                    Toast.makeText(requireContext(), R.string.upload_success, Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.error_firebase_upload_preference,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            R.id.log_out_firebase -> {
                logOut()
            }

            R.id.download_firebase -> {
                if (SettingsActivity.getFirebasePreference(requireContext())!!) {
                    downloadDeckInfo()
                    Toast.makeText(requireContext(), R.string.download_success, Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.error_firebase_preference,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }


}