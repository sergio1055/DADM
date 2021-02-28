import Card
import card.Cloze
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime

class Deck(
    var name: String,
    var id : String,
    var cards : MutableList<Card> = mutableListOf()
) {
    fun addCard() {
        print("Teclea el tipo (0 -> Card 1 -> Cloze): ")
        val opcion = readLine()?.toIntOrNull()
        opcion.let {
            if(it == 0) {
                print("Teclea la pregunta: ")
                val question = readLine()!!.toString()
                print("Teclea la respuesta: ")
                val answer = readLine()!!.toString()

                cards.add(Card(question, answer))
            }

            if(it == 1) {
                print("Teclea la pregunta: ")
                val question = readLine()!!.toString()
                print("Teclea la respuesta: ")
                val answer = readLine()!!.toString()

                cards.add(Cloze(question, answer))
            }
        }

        println("Tarjeta añadida correctamente")
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
        var now = LocalDateTime.now()
        var tarjetas = cards

            tarjetas.forEach{
                println("Simulando tarjeta ${it.question}")
                for(i in 1..period) {
                    println("Fecha actual: ${now.toLocalDate()}")
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
        val filename = "data/$nombre"
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
            var trozos : List<String>
            for(linea in lineas) {
                if(linea.contains("card")) {
                    val card = Card.fromString(linea)
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