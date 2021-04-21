package com.uam.proyectocards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uam.proyectocards.CardsApplication
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.model.Deck
import timber.log.Timber

class StatisticsViewModel : ViewModel() {

    var cards : MutableList<Card> = mutableListOf<Card>()
    var decks : MutableList<Deck> = mutableListOf<Deck>()
    private val _numberofCards = MutableLiveData<Int>()
    private val _numberofDecks = MutableLiveData<Int>()

    val numberofCards: LiveData<Int>
        get() = _numberofCards

    val numberofDecks: LiveData<Int>
        get() = _numberofDecks

    init {
        cards = CardsApplication.cards
        decks = CardsApplication.decks

        _numberofCards.value = cards.size
        _numberofDecks.value = decks.size
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("StatisticsViewModel destroyed")
    }

    fun calculateNumberOfCards() : Int {
        return cards.size
    }

    fun calculateNumberOfDecks() : Int {
        return decks.size
    }

    fun calculateAverageEasinessOfEachDeck() : Double{
        var sum : Double = 0.0
        var numCards: Int = 0
        decks.forEach  {
            it.cards.forEach {
                sum =+ it.easiness
            }

            numCards =+ it.cards.size
        }

        return sum/numCards
    }


}