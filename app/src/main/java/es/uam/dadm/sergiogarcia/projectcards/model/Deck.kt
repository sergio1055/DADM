package es.uam.dadm.sergiogarcia.projectcards.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decks_table")
data class Deck(
    var name: String,
    @PrimaryKey var id: Long,
) {




}
