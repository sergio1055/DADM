package com.example.encode

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
open class Card (
    var question: String,
    var answer: String,
    var date: String = LocalDateTime.now().toString(),
    var id: String = UUID.randomUUID().toString(),
    var quality: Int = 0,
    var repetitions: Int = 0,
    var interval: Long = 1L,
    var nextPracticeDate: String = date,
    var easiness: Double = 2.5,
    var answered : Boolean = false
) {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun fromString(cad : String) : Card {
            var tokens = cad.split("|")
            val question = tokens.get(1)
            val answer = tokens.get(2)
            val date = tokens.get(3)
            val id = tokens.get(4)
            val easiness = tokens.get(5).toDouble()
            val repetitions = tokens.get(6).toInt()
            val interval = tokens.get(7).toLong()
            val nextPractice = tokens.get(8)

            return Card(question, answer, date=date, id=id, easiness = easiness.toDouble(), repetitions = repetitions, interval=interval, nextPracticeDate = nextPractice)
        }
    }
    open fun show() {
        var dateParse = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.parse(nextPracticeDate)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        println("Fecha actual $dateParse.toLocalDate()\n")
        println("$question teclea INTRO para ver respuesta")
        val key = readLine().toString()
        println("$answer (Teclea 0,3,5)")
        val input2 = readLine()!!.toIntOrNull()
        if (input2 != null) {
            quality = input2
        }
    }

    fun update_easy() {
        quality = 5
        update(LocalDateTime.now())
    }

    fun update_doubt() {
        quality = 3
        update(LocalDateTime.now())
    }

    fun update_hard() {
        quality = 5
        update(LocalDateTime.now())
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
            Math.floor(interval*easiness).toLong()
        }


        nextPracticeDate = currentDate.plusDays(interval).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun simulate(period : Long) {
        println("Simulacion de la tarjeta $question")
        /* Copia del mazo */
        var card2 = Card(this.question, this.answer)

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


}