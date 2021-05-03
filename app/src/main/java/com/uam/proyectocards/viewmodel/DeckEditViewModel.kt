package com.uam.proyectocards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.uam.proyectocards.database.CardDatabase
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.model.DeckWithCards

class DeckEditViewModel(application: Application) : AndroidViewModel(application){
    private val context = getApplication<Application>().applicationContext

    private val deckId = MutableLiveData<Long>()

    val deckWithCards: LiveData<List<DeckWithCards>> = Transformations.switchMap(deckId) {
        CardDatabase.getInstance(context).cardDao.getDeckWithCards(it)
    }

    fun loadDeckId(id: Long) {
        deckId.value = id
    }

}