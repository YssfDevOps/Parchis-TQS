package main.model;

import main.model.square.*;

import java.util.*;

public class Board {
    private List<Square> globalPath; // Cyclic board positions 0 to 67
    private Map<Color, List<FinalPathSquare>> playerFinalPaths; // Final paths for each player
    private static final int NUM_SQUARES = 68; // Number of squares on the board
    private static final int NUM_FINAL_SQUARES = 8; // Number of final path squares per player

    private Map<Color, Integer> startPositions; // Start positions for each color
    private Set<Integer> shieldPositions; // Positions of shield squares

    public Board() {
        globalPath = new ArrayList<>(NUM_SQUARES);
        playerFinalPaths = new HashMap<>();
        initializeStartPositions();
        initializeShieldPositions();
        setUpGlobalPath();
        setUpPlayerFinalPaths();
    }

    // Initialize start positions based on colors
    private void initializeStartPositions() {
        startPositions = new HashMap<>();
        startPositions.put(Color.YELLOW, 4);
        startPositions.put(Color.BLUE, 21);
        startPositions.put(Color.RED, 38);
        startPositions.put(Color.GREEN, 55);
    }

    // Initialize shield positions
    private void initializeShieldPositions() {
        shieldPositions = new HashSet<>(Arrays.asList(
            4, 11, 16, 21, 28, 33, 38, 45, 50, 55, 62, 67
        ));
    }

    // Set up the global path with squares
    private void setUpGlobalPath() {
        for (int i = 0; i < NUM_SQUARES; i++) {
            Square square;
            if (shieldPositions.contains(i)) {
                square = new ShieldSquare(i);
            } else {
                square = new RegularSquare(i);
            }
            globalPath.add(square);
        }
    }

    // Set up final paths for each player
    private void setUpPlayerFinalPaths() {
        for (Color color : Color.values()) {
            List<FinalPathSquare> finalPath = new ArrayList<>(NUM_FINAL_SQUARES);
            for (int i = 0; i < NUM_FINAL_SQUARES; i++) {
                finalPath.add(new FinalPathSquare(i, color));
            }
            playerFinalPaths.put(color, finalPath);
        }
    }

    // Get a square from the global path, wrapping around if necessary
    public Square getGlobalSquare(int position) {
        int pos = (position + NUM_SQUARES) % NUM_SQUARES;
        return globalPath.get(pos);
    }

    // Get the starting square for a player
    public ShieldSquare getPlayerStartSquare(Color color) {
        int startPos = startPositions.get(color);
        return (ShieldSquare) getGlobalSquare(startPos);
    }

    public void setPlayerStartSquare(Color color, Square square) {
        int position = square.getPosition();
        startPositions.put(color, position);
        // Update the global path with the new square if necessary
        if (position >= 0 && position < NUM_SQUARES) {
            globalPath.set(position, square);
        }
    }

    // Get the final path for a player
    public List<FinalPathSquare> getPlayerFinalPath(Color color) {
        return playerFinalPaths.get(color);
    }

    // Get the next square for a piece (and maybe handling transitions to final paths)
    public Square getNextSquare(Square currentSquare, Piece piece) {
        return null;
    }
}

