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

        // Invariant
        invariant();
    }

    private void invariant() {
        assert globalPath != null && globalPath.size() == NUM_SQUARES :
            "Global path must be initialized with correct size";
        assert playerFinalPaths != null && playerFinalPaths.size() == Color.values().length :
            "Final paths must exist for all players";
        assert startPositions != null && startPositions.size() == Color.values().length :
            "Start positions must exist for all players";
        assert shieldPositions != null && !shieldPositions.isEmpty() :
            "Shield positions must be initialized";
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
        // Preconditions
        assert position >= 0 : "position cannot be negative";

        int pos = (position + NUM_SQUARES) % NUM_SQUARES;
        return globalPath.get(pos);
    }

    // Get the starting square for a player
    public ShieldSquare getPlayerStartSquare(Color color) {
        // Preconditions
        assert color != null : "Color cannot be null";
        assert startPositions.containsKey(color) :
                "Color must have a valid start position";

        int startPos = startPositions.get(color);
        return (ShieldSquare) getGlobalSquare(startPos);
    }

    public void setPlayerStartSquare(Color color, Square square) {
        // Preconditions
        assert color != null : "Color cannot be null";
        assert square != null : "Square cannot be null";
        assert square.getPosition() >= 0 && square.getPosition() < NUM_SQUARES :
                "Square position must be within global path range";

        int position = square.getPosition();
        startPositions.put(color, position);

        if (position >= 0 && position < NUM_SQUARES) {
            globalPath.set(position, square);
        }

        // Postconditions
        assert startPositions.get(color) == position :
                "Start position not updated correctly";
        assert globalPath.get(position) == square :
                "Square not updated in global path";
    }

    // Get the final path for a player
    public List<FinalPathSquare> getPlayerFinalPath(Color color) {
        // Preconditions
        assert color != null : "Color cannot be null";
        assert playerFinalPaths.containsKey(color) :
                "Player final path must exist for the given color";

        return playerFinalPaths.get(color);
    }

    // Get the next square for a piece (and maybe handling transitions to final paths)
    public Square getNextSquare(Square currentSquare, Piece piece) {
        // Preconditions
        assert currentSquare != null : "Current square cannot be null";
        assert piece != null : "Piece cannot be null";

        Color color = piece.getColor();

        if (currentSquare instanceof FinalPathSquare) {
            // Move within final path
            int index = ((FinalPathSquare) currentSquare).getIndex();
            if (index + 1 < NUM_FINAL_SQUARES) {
                return playerFinalPaths.get(color).get(index + 1);
            } else {
                // Reached the end of the final path
                return null;
            }
        } else {
            // Move on global path
            int nextPos = (currentSquare.getPosition() + 1) % NUM_SQUARES;
            Square nextSquare = getGlobalSquare(nextPos);

            // Check if the piece should enter the final path
            int finalEntryPos = (startPositions.get(color) - 5 + NUM_SQUARES) % NUM_SQUARES;
            if (nextSquare.getPosition() == finalEntryPos) {
                // Enter the player's final path
                return playerFinalPaths.get(color).get(0);
            } else {
                return nextSquare;
            }
        }
    }

    public void showBoard() {
        System.out.println("Global Path:");
        for (Square square : globalPath) {
            System.out.println(square.toString());
        }

        System.out.println("\nFinal Paths:");
        for (Color color : Color.values()) {
            System.out.println("Final Path for " + color + ":");
            List<FinalPathSquare> finalPath = playerFinalPaths.get(color);
            for (FinalPathSquare square : finalPath) {
                System.out.println(square.toString());
            }
        }
    }
}

