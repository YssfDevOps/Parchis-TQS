package main.model;

import main.model.square.ShieldSquare;
import main.model.square.Square;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private List<Square> globalPath; //cycle board
    private List<List<Square>> playerFinalPaths; //final squares of each player
    private static final int NUM_SQUARES = 68; //number of squares in the board
    private static final int[] firstSquares = {5,22,39,56}; //yellow, blue, red, green
    private static final int[] shieldSquares = {5,12,17,22,29,34,39,46,51,56,63,68}; //the players can't die


    public Board() {
        globalPath = new LinkedList<>();
        playerFinalPaths = new LinkedList<>();
        setUpGlobalPath();
        setUpPlayerFinalPaths();
    }

    // Set up the global path from 1 to 68 with corresponding squares
    private void setUpGlobalPath() {}

    // Set up the final paths for each player (each 8 positions long)
    private void setUpPlayerFinalPaths() {}

    // Determines if a given position is a shield square
    private boolean isShieldSquarePosition(int position) { return true; }

    // Find a square in the global path by its position
    public Square findSquare(int position) { return null; }

    // Get the starting square for a player
    public ShieldSquare getPlayerStartSquare(Player player) { return null; }

    // Get a square by its position
    public Square getSquare(int position) { return null; }

    // Get the start position for a player based on their color
    private int getPlayerStartPosition(Player player) { return 0; }
}
