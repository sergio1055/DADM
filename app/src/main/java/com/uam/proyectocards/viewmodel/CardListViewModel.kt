package com.uam.proyectocards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.uam.proyectocards.database.CardDatabase
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.model.DeckWithCards

class CardListViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val deckSelected = MutableLiveData<Long>()

    val deckWithCards: LiveData<List<DeckWithCards>> = Transformations.switchMap(deckSelected) {
        CardDatabase.getInstance(context).cardDao.getDeckWithCards(it)
    }

    fun loadDeckId(id: Long) {
        deckSelected.value = id
    }
}