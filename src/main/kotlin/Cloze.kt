package card

import Card

class Cloze(question: String, answer: String) : Card(question, answer) {

    companion object {
        fun fromString(cad : String) : Card{
            var tokens = cad.split("|")
            val question = tokens.get(1)
            val answer = tokens.get(2)
            val date = tokens.get(3)
            val id = tokens.get(4)
            val easiness = tokens.get(5).toDouble()
            val repetitions = tokens.get(6).toInt()
            val interval = tokens.get(7).toLong()
            val nextPractice = tokens.get(8)

            return Cloze(question, answer)
        }
    }
    override fun show() {
        println("Fecha actual $date\n")
        println("$question teclea INTRO para ver respuesta")
        val key = readLine().toString()
        println("$answer (Teclea 0->Dificil 3-> Dudo 5->Facil")
        val input2 = readLine()!!.toIntOrNull()
        if (input2 != null) {
            quality = input2
        }
    }

    override fun toString(): String {
        return "cloze|$question|$answer|$date|$id|$easiness|$repetitions|$interval|$nextPracticeDate"
    }

}

