package com.uam.proyectocards

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.model.Deck
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
class CardsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var cards: MutableList<Card> = mutableListOf<Card>()
        var decks: MutableList<Deck> = mutableListOf<Deck>()

        init {
            decks.add(Deck("Ingles"))
            decks[0].addCard(Card("To get up", "Levantarse"))

            for(deck in decks) {
                deck.cards.forEach {
                    addCard(it)
                }
            }
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

        fun addDeck(deck: Deck) {
            if(!decks.contains(deck)) {
                decks.add(deck)
            }
        }

        fun getDeck(deckId: String): Deck? {
            decks.forEach() {
                if(it.id == deckId) {
                    return it
                }
            }

            return null
        }

        fun numberOfDecks(): Int {
            return decks.size
        }

        fun removeCard(card: Card) {
            decks.forEach {
                if(it.cards.contains(card)) {
                    it.cards.remove(card)
                }
            }

            cards.remove(card)
        }

        fun removeDeck(deck: Deck) {
            if(decks.contains(deck)) {
                decks.remove(deck)
            }
        }
    }
}