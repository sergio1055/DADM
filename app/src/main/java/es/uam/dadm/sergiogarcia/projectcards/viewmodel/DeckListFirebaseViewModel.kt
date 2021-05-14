package es.uam.dadm.sergiogarcia.projectcards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import es.uam.dadm.sergiogarcia.projectcards.model.Deck
import es.uam.dadm.sergiogarcia.projectcards.model.DeckWithCards
import java.util.concurrent.Executors

class DeckListFirebaseViewModel(application : Application) : AndroidViewModel(application){
    private val executor = Executors.newSingleThreadExecutor()
    var _decks = MutableLiveData<List<DeckWithCards>>()
    private val context = getApplication<Application>().applicationContext

    var reference = FirebaseDatabase.getInstance().getReference("decks")
    private var authFirebase = FirebaseAuth.getInstance()
    val decks: LiveData<List<DeckWithCards>>
        get() = _decks

    init {
        if(authFirebase.currentUser != null) {
            reference.child(authFirebase.currentUser.uid).child("cards")
            reference.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listOfDecks: MutableList<DeckWithCards> = mutableListOf<DeckWithCards>()
                    for (deck in snapshot.children) {
                        var newDeckWithCards = deck.getValue(DeckWithCards::class.java)
                        if (newDeckWithCards != null)
                             executor.execute {
                                CardDatabase.getInstance(context).cardDao.addDeck(newDeckWithCards.deck!!)
                                newDeckWithCards.cards.forEach {
                                    CardDatabase.getInstance(context).cardDao.addCard(it)
                                }
                            }

                            listOfDecks.add(newDeckWithCards!!)
                    }
                    _decks.value = listOfDecks
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

}