package es.uam.dadm.sergiogarcia.projectcards

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import es.uam.dadm.sergiogarcia.projectcards.model.Deck
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
    }
}