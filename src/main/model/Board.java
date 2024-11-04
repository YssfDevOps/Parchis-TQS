package main.model;

import main.model.square.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private List<Square> globalPath; // cycle board
    private List<List<Square>> playerFinalPath; // final squares of each player
    private static final int NUM_SQUARES = 68; // number of squares on the board
    private static final int NUM_FINAL_SQUARES = 8; // number of final squares

    private List<Integer> firstSquares; // start positions by color
    private List<Integer> shieldSquares; // shield positions

    // Single instance of Board
    private static Board instance;

    // Private constructor to prevent external instantiation
    private Board() {
        globalPath = new LinkedList<>();
        playerFinalPath = new ArrayList<>();

        // Initialize firstSquares with start positions
        firstSquares = new ArrayList<>();
        firstSquares.add(5);
        firstSquares.add(22);
        firstSquares.add(39);
        firstSquares.add(56);

        // Initialize shieldSquares with shield positions
        shieldSquares = new ArrayList<>();
        shieldSquares.add(5);
        shieldSquares.add(12);
        shieldSquares.add(17);
        shieldSquares.add(22);
        shieldSquares.add(29);
        shieldSquares.add(34);
        shieldSquares.add(39);
        shieldSquares.add(46);
        shieldSquares.add(51);
        shieldSquares.add(56);
        shieldSquares.add(63);
        shieldSquares.add(68);

        setUpGlobalPath();
        setUpPlayerFinalPaths();
    }

    // Static method to get the single instance of Board
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    // Set up the global path from 1 to 68 with corresponding squares
    private void setUpGlobalPath() {
        for (int i = 1; i <= NUM_SQUARES; i++) {
            Square square;
            if (isShieldSquarePosition(i)) {
                square = new ShieldSquare(i, this);
            } else {
                square = new RegularSquare(i, this);
            }
            globalPath.add(square);
        }
    }

    // Set up the final paths for each player (each 8 positions long)
    private void setUpPlayerFinalPaths() {
        for (int i = 0; i < 4; i++) {
            List<Square> finalPath = new ArrayList<>();
            for (int j = 1; j <= NUM_FINAL_SQUARES; j++) {
                finalPath.add(new FinalPathSquare(j, this));
            }
            playerFinalPath.add(finalPath);
        }
    }

    // Determines if a given position is a shield square
    private boolean isShieldSquarePosition(int position) {
        return shieldSquares.contains(position);
    }

    // Find a square in the global path by its position
    public Square findSquare(int position) {
        if (position < 1 || position > NUM_SQUARES) {
            //throw new IllegalArgumentException("Invalid position: " + position);
            return null;
        }
        return globalPath.get(position - 1);
    }

    // Get the starting square for a player
    public ShieldSquare getPlayerStartSquare(Player player) {
        int startPos = getPlayerStartPosition(player);
        Square startSquare = findSquare(startPos);
        if (startSquare instanceof ShieldSquare) {
            return (ShieldSquare) startSquare;
        }
        return null;
    }

    // Get a square by its position
    public Square getSquare(int position) {
        if (position <= NUM_SQUARES) {
            return findSquare(position);
        } else {
            int playerIndex = (position - NUM_SQUARES - 1) / NUM_FINAL_SQUARES;
            int finalPathPos = (position - NUM_SQUARES - 1) % NUM_FINAL_SQUARES;
            return playerFinalPath.get(playerIndex).get(finalPathPos);
        }
    }

    // Get the start position for a player based on their color
    public int getPlayerStartPosition(Player player) {
        return firstSquares.get(player.getColorIndex());
    }

    public List<Square> getPlayerFinalPath(Player player) {
        int playerIndex = player.getColorIndex();
        return playerFinalPath.get(playerIndex);
    }
}
