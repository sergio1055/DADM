package es.uam.dadm.sergiogarcia.projectcards.database

import androidx.lifecycle.LiveData
import androidx.room.*
import es.uam.dadm.sergiogarcia.projectcards.model.Card
import es.uam.dadm.sergiogarcia.projectcards.model.Deck
import es.uam.dadm.sergiogarcia.projectcards.model.DeckWithCards

@Dao
interface CardDAO {

    @Query("SELECT * FROM cards_table WHERE userId = :userId")
    fun getCards(userId: String): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id AND userId = :userId")
    fun getCard(id : String, userId: String) : LiveData<Card?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card: Card)

    @Update
    fun update(card: Card)

    @Update
    fun update(deck: Deck)

    @Delete
    fun removeCard(card: Card)

    @Delete
    fun removeDeck(deck: Deck)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Transaction
    @Query("SELECT * FROM decks_table WHERE userId = :userId")
    fun getDecksWithCards(userId: String): LiveData<List<DeckWithCards>>

    @Query("SELECT * FROM decks_table WHERE id = :deckId AND userId = :userId")
    @Transaction
    fun getDeckWithCards(deckId: Long, userId: String): LiveData<List<DeckWithCards>>

    @Query("DELETE FROM cards_table WHERE userId = :userId")
    fun deleteCardsTableFromUser(userId: String)

    @Query("DELETE FROM decks_table WHERE userId = :userId")
    fun deleteDecksTableFromUser(userId: String)

    @Query("SELECT EXISTS (SELECT 1 FROM decks_table WHERE id = :deckId AND userId = :userId)")
    fun existsDeck(deckId: Long, userId: String) :  Boolean

    @Query("SELECT EXISTS (SELECT 1 FROM cards_table WHERE id = :cardId AND userId = :userId)")
    fun existsCard(cardId: String, userId: String) : Boolean
}