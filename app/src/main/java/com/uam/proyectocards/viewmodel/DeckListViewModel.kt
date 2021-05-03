package com.uam.proyectocards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uam.proyectocards.database.CardDatabase
import com.uam.proyectocards.model.Deck
import com.uam.proyectocards.model.DeckWithCards

class DeckListViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    val cardDatabase = CardDatabase.getInstance(context)

    var decks: LiveData<List<DeckWithCards>> = CardDatabase.getInstance(context).cardDao.getDecksWithCards()
}