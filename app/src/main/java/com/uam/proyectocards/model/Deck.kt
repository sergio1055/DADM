package com.uam.proyectocards.model

import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.util.*

class Deck(
    var name: String,
    var id: String = UUID.randomUUID().toString(),
) {
    var cards : MutableList<Card> = mutableListOf()


    fun addCard(card : Card) {

        if(!cards.contains(card)) {
            cards.add(card)
        }
    }

    fun listCards() {
        cards.forEach() {println("${it.question} -> ${it.answer}")}
    }


    fun removeCard() {
        print("Introduce el nombre de la pregunta de la tarjeta a eliminar de las siguientes: \n")
        listCards()
        val nombre = readLine()!!.toString()
        for(card in cards) {
            if(card.question == nombre) {
                cards.remove(card)
                println("Tarjeta eliminada")
            }
        }

        println("No existe esa tarjeta")
    }

    fun simulate(period: Int) {
        println("Simulación del mazo $name:")
        var now = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            throw Exception("Versión incompatible")
        }

        var tarjetas = cards

        tarjetas.forEach{
            for(i in 1..period) {
                val nextPractice = LocalDateTime.parse(it.nextPracticeDate)
                if((nextPractice.isBefore(now)) || (nextPractice.isEqual(now))) {
                    it.show()
                    it.update(now)
                    it.details()
                }

                now = now.plusDays(1)
            }
        }
    }

    fun writeCards(nombre: String) {
        print("Introduzca el fichero donde guardar las tarjetas: ")
        val name_file = readLine()!!.toString()
        val filename = "data/$name_file"
        val file = File(filename)
        cards.forEach() {println(it)}

        file.printWriter().use { out ->
            for(card in cards) {
                out.println("$card")
            }
        }
    }

    fun readCards(name : String) {
        try {
            val lineas: List<String> = File("data/$name").readLines()
            for(linea in lineas) {
                if(linea.contains("card")) {
                    val card = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        Card.fromString(linea)
                    } else {
                        TODO("VERSION.SDK_INT < O")
                    }
                    print(card)
                    println("$card")
                    cards.add(card)
                }

                else {
                    val card = Cloze.fromString(linea)
                    println("$card")
                    cards.add(card)
                }
            }
        } catch(e : FileNotFoundException) {
            println("Error al encontrar el fichero")
        }

    }
}