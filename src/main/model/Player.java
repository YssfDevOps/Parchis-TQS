package main.model;

import java.util.ArrayList;

public class Player {
    private String name; //nom del jugador
    private Piece[] pieces; //peçes del jugador - 4
    private ArrayList<Square> finalSquares = new ArrayList<>(8);
    private boolean playing; //te alguna peça jugant?
    private boolean winner; //ha guanyat?

    public Player(String name) {} //constructor
    public void movePiece(Piece piece, int moves) {} //
    public boolean wins() {return winner;}
    public boolean isPlaying() {return playing;}
    public void setPlaying(boolean d) {}
}