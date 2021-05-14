package es.uam.dadm.sergiogarcia.projectcards.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToLong

@Entity(tableName = "cards_table")

@RequiresApi(Build.VERSION_CODES.O)
open class Card (
    @ColumnInfo(name = "card_question")
    var question: String,
    var answer: String,
    var date: String = LocalDateTime.now().toString(),
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var deckId : Long = 0,
    var userId : String
) {

    constructor() : this(
        "Pregunta",
        "Respuesta",
        LocalDateTime.now().toString(),
        UUID.randomUUID().toString(),
        0,
        "Usuario"
    )

    var quality: Int = 0
    var repetitions: Int = 0
    var interval: Long = 1L
    var nextPracticeDate: String = date
    var easiness: Double = 2.5
    @Ignore
    var answered : Boolean = false

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromString(cad : String) : Card {
            var tokens = cad.split("|")
            val question = tokens.get(1)
            val answer = tokens.get(2)
            val date = tokens.get(3)
            val deckId = tokens.get(4).toLong()
            val userId = tokens.get(5)
            return Card(
                question,
                answer,
                date = date,
                deckId = deckId,
                userId = userId
            )
        }
    }

    open fun show() {
        var dateParse = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.parse(nextPracticeDate)
        } else {
            throw Exception("Versión incorrecta")
        }

        println("$question teclea INTRO para ver respuesta")
        println("$answer (Teclea 0,3,5)")
        val input2 = readLine()!!.toIntOrNull()
        if (input2 != null) {
            quality = input2
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun update(currentDate : LocalDateTime) {
        easiness = maxOf(1.3, easiness + 0.1 - ((5 - quality) * (0.08 + (5- quality) * 0.02)))

        repetitions = if(quality < 3) {
            0
        } else {
            repetitions+1
        }

        interval = if(repetitions <= 1) {
            1
        } else if(repetitions == 2) {
            6
        } else {
            (interval*easiness).roundToLong()
        }


        nextPracticeDate = currentDate.plusDays(interval).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun simulate(period : Long) {
        println("Simulacion de la tarjeta $question")
        /* Copia del mazo */
        var card2 = Card(
            this.question,
            this.answer,
            deckId = this.deckId,
            userId = this.userId
        )

        /* Simulacion avanzada (testing) */
        var now = LocalDateTime.now()

        for(i in 1..period) {
            val nextPractice = LocalDateTime.parse(card2.nextPracticeDate)
            if((nextPractice.isBefore(now)) || (nextPractice.isEqual(now))) {
                card2.show()
                card2.update(now)
                card2.details()
            }

            now = now.plusDays(1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun details() {
        var dateParse = LocalDateTime.parse(nextPracticeDate)
        println("eas=${"%.2f".format(easiness)}, rep=$repetitions, int=$interval next=${dateParse.toLocalDate()}")
    }

    override fun toString(): String {
        return "card|$question|$answer|$date|$id|$easiness|$repetitions|$interval|$nextPracticeDate"
    }


    fun isDue(now: LocalDateTime?): Boolean {
        val nextPractice = LocalDateTime.parse(nextPracticeDate)

        if(nextPractice.isBefore(now) || nextPractice.isBefore(now)) {
            return true
        }

        return false
    }

}