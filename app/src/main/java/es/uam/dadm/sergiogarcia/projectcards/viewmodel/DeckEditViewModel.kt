package es.uam.dadm.sergiogarcia.projectcards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseAuth
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.model.DeckWithCards

class DeckEditViewModel(application: Application) : AndroidViewModel(application){
    private val context = getApplication<Application>().applicationContext
    private var user = FirebaseAuth.getInstance().currentUser

    private val deckId = MutableLiveData<Long>()

    val deckWithCards: LiveData<List<DeckWithCards>> = Transformations.switchMap(deckId) {
        CardDatabase.getInstance(context).cardDao.getDeckWithCards(it,user.uid)
    }

    fun loadDeckId(id: Long) {
        deckId.value = id
    }

}