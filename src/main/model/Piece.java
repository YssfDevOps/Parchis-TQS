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
        this.id = ++idCounter;
        this.color = color;
        this.atHome = true;
        this.hasFinished = false;
        this.square = null;
    }

    public int getId() {
        return id;
    }

    public void setAtHome(boolean atHome) {
        this.atHome = atHome;
    }

    public boolean isAtHome() {
        return atHome;
    }

    public void sendHome() {
        if (square != null) {
            square.leave(this);
        }
        square = null;
        atHome = true;
    }

    public void enterGame(Board board) {
        if (atHome) {
            Square startSquare = board.getPlayerStartSquare(color);
            // Check if the start square is unblocked for this piece
            if (!startSquare.isBlocked(this)) {
                startSquare.landHere(this);
                square = startSquare;
                atHome = false;
            }
        }
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
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
            location = "on final path at position " + ((FinalPathSquare) square).getIndex();
        } else {
            location = "on global path at position " + square.getPosition();
        }
        return "Piece " + id + " is " + location;
    }
}
