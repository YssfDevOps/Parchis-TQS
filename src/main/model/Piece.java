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
    }

    public int getId() {
        return id;
    }

    public void setAtHome(boolean atHome) {
        // Precondition
        assert !hasFinished : "Cannot set atHome for a finished piece";

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

        // Invariant
        invariant();
    }

    public void enterGame(Board board) {
        // Precondition
        assert board != null : "Board cannot be null";
        assert atHome : "Piece must be at home to enter game";

        if (atHome) {
            Square startSquare = board.getPlayerStartSquare(color);
            // Check if the start square is unblocked for this piece
            if (!startSquare.isBlocked(this)) {
                startSquare.landHere(this);
                square = startSquare;
                atHome = false;
            } else {
                System.out.println("Your home is full.");
            }
        }

        // Postcondition
        assert (!atHome && square != null) || (atHome && square == null) :
            "Piece must be either at home without a square or on a square";

        // Invariant
        invariant();
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;

        // Invariant
        invariant();
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        // Precondition
        assert !atHome : "Piece at home cannot be set as finished";

        this.hasFinished = hasFinished;

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
