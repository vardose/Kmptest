package com.example.android.exo;

public class testsJava {
    public static void main(String[] args) {
        //System.out.println(ExoKotlinKt.boulangerie(2,1,1));
        var car = new CarEntity("Seat", "Leon");
        System.out.println("C'est une " + car.getMarque() + " " + car.getModele() + " de couleur " + car.getCouleur() + ".");
    }
}
