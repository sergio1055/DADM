package com.uam.proyectocards

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
class CardsApplication : Application() {

    init {
        cards.add(Card("To wake up", "Despertarse"))
        cards.add(Card("To pick up", "Recoger"))

    }
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var cards: MutableList<Card> = mutableListOf<Card>()

        fun numberOfCardsLeft() : Int {
            return cards.size
        }
    }
}