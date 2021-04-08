package com.example.encode

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
class CardsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var cards: MutableList<Card> = mutableListOf<Card>()

        init {
            cards.add(Card("To wake up", "Despertarse"))
            cards.add(Card("To pick up", "Recoger"))

        }

        fun numberOfCardsLeft() : Int {
            return cards.size
        }

        fun getCard(cardId: String): Card? {

            cards.forEach() {
                if(it.id == cardId) {
                    return it
                }
            }

            return null
        }

        fun addCard(card: Card) {

            if(!cards.contains(card)) {
                cards.add(card)
            }
        }
    }
}