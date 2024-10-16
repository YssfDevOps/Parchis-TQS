package main.model;

import java.util.ArrayList;

public class Board {
    private ArrayList<Square> squares = new ArrayList<>(68); //tauler casilles normals
    private static final int NUM_SQUARES = 68; //num de caselles del tauler
    private static final int[] firstSquares = {5,22,39,56}; //yellow, blue, red, green - caselles de sortida
    private static final int[] shieldSquares = {5,12,17,22,29,34,39,46,51,56,63,68}; //caselles escut on els jugadors no poden morir

    public Board() {}
    public FirstSquare getFirstSquare(String color) {return null;}
    public Square findSquare(String color) {return null;}
}