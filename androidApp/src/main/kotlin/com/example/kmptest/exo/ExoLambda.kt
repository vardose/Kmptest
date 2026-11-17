package com.example.android.exo


fun main() {
    exo1()
    println("\n--- EXO 3 ---")
    exo3()
}
fun exo1(){
    val lower : (String) -> String = { it.lowercase() }
    val hour : (Int) -> Int = { java.time.LocalTime.now().hour }
    val max : (Int, Int) -> Int = { a, b -> maxOf(a, b) }
    val reverse : (String) -> String = { it.reversed() }
    val minToMinHour : (Int?) -> Pair<Int, Int>? = { if (it == null) null else Pair(it/60, it%60) }


    println(lower("LYONNAIS SOUS NOS COULEURS, LYONNAIS DANS NOTRE COEUR !"))
    println(hour(460))
    println(max(12,1))
    println(reverse("la kekette qui colle"))
    println(minToMinHour(460))
    println(minToMinHour(null))
}
data class PersonEntity(var name:String, var note:Int)

fun exo3(){
    val list = arrayListOf(
        PersonEntity("toto", 16),
        PersonEntity("Tata", 20),
        PersonEntity("Toto", 8),
        PersonEntity("Charles", 14)
    )

    // Lambdas réutilisables pour éviter les répétitions (points 10 & 11)
    val isToto: (PersonEntity) -> Boolean = { it.name.equals("toto", ignoreCase = true) }
    val hasMoyenne: (PersonEntity) -> Boolean = { it.note >= 10 }

    println("1. Afficher la sous liste de personne ayant 10 et +")
    println(list.filter(hasMoyenne).joinToString("\n") { "-${it.name} : ${it.note}" })

    println("\n2. Afficher combien il y a de Toto dans la classe ?")
    println(list.count(isToto))

    println("\n3. Afficher combien de Toto ayant la moyenne (10 et +)")
    // Utilisation des lambdas réutilisables
    println(list.count { isToto(it) && hasMoyenne(it) })

    println("\n4. Afficher combien de Toto ont plus que la moyenne de la classe")
    val averageNote = list.map { it.note }.average()
    println("Moyenne de la classe: $averageNote")
    println(list.count { isToto(it) && it.note > averageNote })

    println("\n5. Afficher la liste des noms triés par ordre alphabétique sans doublon")
    println(list.map { it.name }.distinct().sortedBy { it.lowercase() })

    println("\n6. Ajouter un point à ceux n’ayant pas la moyenne (<10)")
    list.filter { !hasMoyenne(it) }.forEach { it.note++ }
    println(list)

    println("\n7. Ajouter un point à tous les Toto")
    list.filter(isToto).forEach { it.note++ }
    println(list)

    println("\n8. Retirer de la liste ceux ayant la note la plus petite. (Il peut y en avoir plusieurs)")
    val minNote = list.minOfOrNull { it.note }
    if (minNote != null) {
        list.removeIf { it.note == minNote }
    }
    println(list)

    println("\n9. Afficher les noms de ceux ayant la moyenne (10 et +) par ordre alphabétique")
    println(list.filter(hasMoyenne).map { it.name }.sortedBy { it.lowercase() })

    println("\n12. Dupliquer la liste ainsi que tous les utilisateurs (nouvelle instance) qu'elle contient (map + copy)")
    val duplicatedList = list.map { it.copy() }
    println("Liste originale : $list")
    println("Liste dupliquée : $duplicatedList")

    println("\nBonus. Afficher par notes croissantes les élèves ayant eu cette note")
    list.groupBy { it.note }
        .toSortedMap()
        .forEach { (note, persons) ->
            println("$note : ${persons.joinToString(", ") { it.name }}")
        }
}