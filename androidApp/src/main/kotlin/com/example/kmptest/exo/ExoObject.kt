package com.example.android.exo

import java.util.Random

data class CarEntity(var marque:String = "", var modele:String = "") {
    var couleur = "grise"
}

class HouseEntity(var couleur:String = "", largeur:Int = 0, longueur:Int = 0) {
    var surface:Int = largeur * longueur

    fun print() = println("La maison ${couleur} fait ${surface}m2")
}

class PrintRandomIntEntity(var max:Int){
    private var random: Random = Random()

    init {
        println("init")
        for (i in 1..3){
            println(random.nextInt(max))
        }
    }

    constructor() :this(max = 100){
        println("constructeur")
        println(random.nextInt(max))
    }

}

class ThermometerEntity(var min:Int = 0, var max:Int = 0, value:Int = 0) {
    var value = value.coerceIn(min, max)
        set(value) {
            field = value.coerceIn(min, max)
        }

    companion object {
        fun getCelsiusThermometer() = ThermometerEntity(min = -30, max = 50, value = 0)
        fun getFahrenheitThermometer() = ThermometerEntity(min = 20, max = 120, value = 32)
    }
}

class RandomName(){
    private var names: ArrayList<String> = arrayListOf("toto", "tata", "tutu")
    private var oldValue = ""


    fun add(name:String) = name.isNotBlank() && name !in names && names.add(name)

    fun next() = names.random()

    fun addAll(vararg names:String){
        for (name in names)
            add(name)
        //names.forEach { add(it) }
    }

    fun nextDiff() = names.filter { it != oldValue }.random().also { oldValue = it }

    fun next2() = Pair(nextDiff(), nextDiff())
}