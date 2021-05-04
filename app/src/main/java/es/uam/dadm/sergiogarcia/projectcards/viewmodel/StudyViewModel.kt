package es.uam.dadm.sergiogarcia.projectcards.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import es.uam.dadm.sergiogarcia.projectcards.database.CardDatabase
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.O)
class StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val executor = Executors.newSingleThreadExecutor()

    private val context = getApplication<Application>().applicationContext

    @RequiresApi(Build.VERSION_CODES.O)
    var card: Card? = null
    var cards: LiveData<List<Card>>  = CardDatabase.getInstance(context).cardDao.getCards()
    var dueCard: LiveData<Card?> =
        Transformations.map(cards, ::due)
     val cardsLeft: LiveData<Int> =
        Transformations.map(cards, ::left)

    private fun left(cards: List<Card>) =
        cards.filter { card -> card.isDue(LocalDateTime.now()) }.size

    private fun due(cards: List<Card>) = try {
        cards.filter { card -> card.isDue(LocalDateTime.now()) }.random()
    } catch (e: Exception) {
        null
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }

    fun update(quality: Int) {
        card?.quality = quality
        card?.update(LocalDateTime.now())

        executor.execute {
            CardDatabase.getInstance(context).cardDao.update(card!!)
        }
    }
}