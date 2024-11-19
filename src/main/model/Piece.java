package main.model;

import main.model.square.FinalPathSquare;
import main.model.square.Square;

public class Piece {
    private Square square;
    private Color color;
    private boolean atHome;
    private boolean hasFinished;
    private int id;
    private static int idCounter = 0;

    public Piece(Color color) {
        // Precondition
        assert color != null : "Color cannot be null";

        this.id = ++idCounter;
        this.color = color;
        this.atHome = true;
        this.hasFinished = false;
        this.square = null;

        // Invariant
        invariant();
    }

    private void invariant() {
        assert color != null : "Piece color cannot be null";
        assert id > 0 : "Piece ID must be greater than 0";
        assert (atHome && square == null && !hasFinished) ||
                (!atHome && square != null && !hasFinished) ||
                (hasFinished && square == null) :
                "Piece state must be consistent (atHome, on the board, or finished)";
    }

    public int getId() {
        return id;
    }

    public void setAtHome(boolean atHome) {
        this.atHome = atHome;

        invariant();
    }

    public boolean isAtHome() {
        return atHome;
    }

    public void sendHome() {
        // Precondition
        assert square == null || square.getPieces().contains(this) :
                "Piece must be on its current square or null";

        if (square != null) {
            square.leave(this);
        }
        square = null;
        atHome = true;

        // Postcondition
        assert atHome : "Piece should be at home";
        assert square == null : "Square should be null";

        // Invariant
        invariant();
    }

    public void enterGame(Board board) {
        // Precondition
        assert board != null : "Board cannot be null";
        assert atHome : "Piece must be at home to enter game";

        if (atHome) {
            Square startSquare = board.getPlayerStartSquare(color);
            assert startSquare != null : "Start square cannot be null";

            // Check if the start square is unblocked for this piece
            if (!startSquare.isBlocked(this)) {
                startSquare.landHere(this);
                square = startSquare;
                atHome = false;
            }
        }

        // Postcondition
        assert !atHome || square == null :
                "If piece is not at home, it must be on a square";

        // Invariant
        invariant();
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        // Precondition
        assert square == null || square.getPieces().contains(this) || square.getPieces().isEmpty() :
                "Square must be null, contain this piece, or be empty";

        this.square = square;

        // Invariant
        invariant();
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        // Postcondition
        assert this.hasFinished == hasFinished :
                "hasFinished value mismatch";

        // Invariant
        invariant();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        String location;
        if (atHome) {
            location = "at home";
        } else if (hasFinished) {
            location = "has finished";
        } else if (square instanceof FinalPathSquare) {
            location = "on final path at position " + (((FinalPathSquare) square).getIndex() + 1);
        } else {
            location = "on global path at position " + (square.getPosition() + 1);
        }
        return "Piece " + id + " is " + location;
    }
}
