package es.uam.dadm.sergiogarcia.projectcards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.model.DeckWithCards

class StatisticsDeckViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val deckSelected = MutableLiveData<Long>()

    val deckWithCards: LiveData<List<DeckWithCards>> = Transformations.switchMap(deckSelected) {
        CardDatabase.getInstance(context).cardDao.getDeckWithCards(it)
    }

    fun loadDeckId(id: Long) {
        deckSelected.value = id
    }

}