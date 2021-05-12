package es.uam.dadm.sergiogarcia.projectcards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import es.uam.dadm.sergiogarcia.projectcards.model.Deck
import es.uam.dadm.sergiogarcia.projectcards.model.DeckWithCards

class DeckListFirebaseViewModel() : ViewModel(){

    var _decks = MutableLiveData<List<DeckWithCards>>()
    var reference = FirebaseDatabase.getInstance().getReference("decks")
    private var authFirebase = FirebaseAuth.getInstance()
    val decks: LiveData<List<DeckWithCards>>
        get() = _decks

    init {
        if(authFirebase.currentUser != null) {
            reference = reference.child(authFirebase.currentUser.uid)

            reference.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listOfCards: MutableList<DeckWithCards> = mutableListOf<DeckWithCards>()
                    for (deck in snapshot.children) {
                        var newDeckWithCards = deck.getValue(DeckWithCards::class.java)
                        if (newDeckWithCards != null)
                            listOfCards.add(newDeckWithCards)
                    }
                    _decks.value = listOfCards
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

}