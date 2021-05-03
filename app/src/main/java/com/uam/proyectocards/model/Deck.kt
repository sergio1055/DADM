package com.uam.proyectocards.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "decks_table")
data class Deck(
    var name: String,
    @PrimaryKey var id: Long,
) {




}
