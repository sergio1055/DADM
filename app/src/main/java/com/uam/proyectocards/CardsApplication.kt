package com.uam.proyectocards

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.uam.proyectocards.database.CardDatabase
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.model.Deck
import timber.log.Timber
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.O)
class CardsApplication : Application() {
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val cardDatabase = CardDatabase.getInstance(context = this)
        executor.execute {

        }
    }

    companion object {
        var cards: MutableList<Card> = mutableListOf<Card>()
        var decks: MutableList<Deck> = mutableListOf<Deck>()


        fun getCard(cardId: String): Card? {

            cards.forEach() {
                if (it.id == cardId) {
                    return it
                }
            }

            return null
        }

        fun addCard(card: Card) {

            if (!cards.contains(card)) {
                cards.add(card)
            }
        }

        fun addDeck(deck: Deck) {
            if (!decks.contains(deck)) {
                decks.add(deck)
            }
        }

        fun getDeck(deckId: Long): Deck? {
            decks.forEach() {
                if (it.id == deckId) {
                    return it
                }
            }

            return null
        }

        fun numberOfDecks(): Int {
            return decks.size
        }
    }
}