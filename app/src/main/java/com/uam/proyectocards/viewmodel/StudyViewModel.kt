package com.uam.proyectocards.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uam.proyectocards.CardsApplication
import com.uam.proyectocards.model.Card
import timber.log.Timber
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class StudyViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    var card: Card? = null
    var cards: MutableList<Card> = mutableListOf<Card>()
    private val _cardsLeft = MutableLiveData<Int>()
    val cardsLeft : LiveData<Int>
        get() = _cardsLeft

    init {
        Timber.i("MainViewModel created")
        cards = CardsApplication.cards
        card = random_card()

        _cardsLeft.value = cards.size
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }

    fun update(quality: Int) {
        card?.quality = quality
        card?.update(LocalDateTime.now())
        card = random_card()
        _cardsLeft.value = cardsLeft.value?.minus(1)
        Timber.i("$cardsLeft.value")
    }

    private fun random_card() = try {
        cards.filter { card ->
            card.isDue(LocalDateTime.now())
        }.random()
    } catch (e: NoSuchElementException) {
        null
    }
}