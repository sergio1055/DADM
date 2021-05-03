package com.uam.proyectocards.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.model.Deck
import com.uam.proyectocards.model.DeckWithCards

@Dao
interface CardDAO {

    @Query("SELECT * FROM cards_table")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id : String) : LiveData<Card?>

    @Insert
    fun addCard(card: Card)

    @Update
    fun update(card: Card)

    @Delete
    fun removeCard(card: Card)

    @Delete
    fun removeDeck(deck: Deck)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Transaction
    @Query("SELECT * FROM decks_table")
    fun getDecksWithCards(): LiveData<List<DeckWithCards>>

    @Query("SELECT * FROM decks_table WHERE id = :deckId")
    @Transaction
    fun getDeckWithCards(deckId: Long): LiveData<List<DeckWithCards>>

}