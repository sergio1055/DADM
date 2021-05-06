package es.uam.dadm.sergiogarcia.projectcards.model

import androidx.room.Embedded
import androidx.room.Relation

data class DeckWithCards(
    @Embedded val deck: Deck,
    @Relation(
        parentColumn = "id",
        entityColumn = "deckId"
    )

    val cards: List<Card>
)