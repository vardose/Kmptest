package com.example.android.exo

import kotlin.random.Random
import kotlin.reflect.typeOf

fun main() {
//    println("Hello world !")
//
//    var v1 = "toto"
//    println(v1.uppercase())
//
//    var v2:String? = "toto"
//    println(v2?.uppercase())
//
//    var v3:String? = null
//    println(v3?.uppercase())
//
//    var v4 : Int? = null
//    //Laisser le curseur de la souris sur Random pour qu'il vous propose de l'importer
//    //Choisir celui de Koltin
//    if(Random.nextBoolean()){
//        v4 = Random.nextInt(10)
//    }
//
//    println(v4 ?: "Pas de valeur")
//
//    if (v3.isNullOrBlank())
//        println("Toto")
//
//    var v5 = v3 + v3
//    println(v5::class.simpleName)

//    println("${boulangerie(croissants = 2)}€")
//    println("${boulangerie(baguettes = 2, sandwiches = 2)}€")

//    var voiture = CarEntity(marque = "Seat", modele = "Leon")
//    println("C'est une ${voiture.marque} ${voiture.modele} de ${voiture.couleur} grise.")

//    val house = HouseEntity("bleue", 4, 3)
//    house.print() //La maison bleue fait 12m²

//    val randomInt = PrintRandomIntEntity(10)
//    val randomInt2 = PrintRandomIntEntity()

//    var t1 = ThermometerEntity(min=-20, max= 50, value =0)
//    println("Température de ${t1.value}") // 0
//
//    //Cas qui marche
//    t1.value = 10
//    println("Température de ${t1.value}") // 10 attendu
//
//    //Borne minimum
//    t1.value = -30
//    println("Température de ${t1.value}") // -20 attendu
//
//    //Borne maximum
//    t1.value = 100
//    println("Température de ${t1.value}") // 50 attendu
//
//    //Pour les plus rapides : Cas de départ
//    t1 = ThermometerEntity(min=-20, max= 50, value =-100)
//    println("Température de ${t1.value}") // -20 attendu

//    val tCelsius = ThermometerEntity.getCelsiusThermometer()
//    tCelsius.value = 18
//    println("La valeur est de ${tCelsius.value}") //18
//    tCelsius.value = -45
//    println("La valeur est de ${tCelsius.value}") //-30
//
//    val tFahrenheit = ThermometerEntity.getFahrenheitThermometer()
//    tFahrenheit.value = 70
//    println("La valeur est de ${tFahrenheit.value}") //70
//    tFahrenheit.value = 130
//    println("La valeur est de ${tFahrenheit.value}") //120

    val randomName = RandomName()
    randomName.add("bobby")
    randomName.addAll("bobby", "Tobby", "Gustavo")
    repeat(10) {
        println(randomName.next() + " ")
    }
    repeat(10) {
        print(randomName.nextDiff() + " ")
    }
    println("\n ${randomName.next2()}")
}

fun boulangerie(croissants: Int = 0, baguettes: Int = 0, sandwiches: Int = 0) =
    CROISSANTS * croissants + BAGUETTES * baguettes + SANDWICHES * sandwiches