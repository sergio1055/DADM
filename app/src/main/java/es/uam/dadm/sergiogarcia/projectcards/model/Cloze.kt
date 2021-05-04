package es.uam.dadm.sergiogarcia.projectcards.model

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class Cloze(question: String, answer: String, deckId: Long) : Card(question, answer, deckId = deckId) {

    companion object {
        fun fromString(cad : String) : Card {
            var tokens = cad.split("|")
            val question = tokens.get(1)
            val answer = tokens.get(2)
            val deckId = tokens.get(3).toLong()
            return Cloze(question, answer, deckId)
        }
    }
    override fun show() {
        println("$question teclea INTRO para ver respuesta")
        println("${question.replace("**", answer)} (Teclea 0->Dificil 3-> Dudo 5->Facil")
        val input2 = readLine()!!.toIntOrNull()
        if (input2 != null) {
            quality = input2
        }
    }

    override fun toString(): String {
        return "cloze|$question|$answer|$date|$id|$easiness|$repetitions|$interval|$nextPracticeDate"
    }

}