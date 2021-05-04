package es.uam.dadm.sergiogarcia.projectcards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.model.Card

class CardEditViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val cardId = MutableLiveData<String>()
    
    val card: LiveData<Card> = Transformations.switchMap(cardId) {
        CardDatabase.getInstance(context).cardDao.getCard(it)
    }

    fun loadCardId(id: String) {
        cardId.value = id
    }
}