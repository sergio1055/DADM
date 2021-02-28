

fun main() {
    println("Bienvenido a la aplicacion Cards\n")
    val mazo = Deck("Ingles", "ing")
    do {
        print("1. Anyadir tarjeta\n2. Lista de tarjetas\n3. Simulacion\n4.Leer tarjetas de fichero \n5.Escribir tarjetas en fichero\n6.Eliminar tarjeta\n7.Salir\n")
        println("Teclea tu opcion: ")
        val opcion = readLine()!!.toInt()
        when(opcion) {
            1 -> mazo.addCard()
            2 -> mazo.listCards()
            3 -> mazo.simulate(10)
            4 -> mazo.readCards("tarjetas.txt")
            5 -> mazo.writeCards("tarjetas.txt")
            6 -> mazo.removeCard()
            7-> {
                println("Hasta Luego!")
                return
            }
            else-> println("No existe dicha opcion")
        }
    } while(opcion != 7)

}