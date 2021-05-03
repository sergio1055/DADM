package com.uam.proyectocards.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uam.proyectocards.model.Card
import com.uam.proyectocards.model.Deck

@Database(entities = [Card::class, Deck::class], version = 2, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {
    abstract val cardDao: CardDAO

    companion object {
        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDatabase::class.java,
                    "cards_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
        }
    }
