package main.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name; //nom del jugador
    private List<Piece> pieces; //peçes del jugador - 4
    private String color;
    private boolean playing; //te alguna peça jugant?
    private boolean winner; //ha guanyat?

    public Player(String name, String color) {} //constructor
    public void movePiece(Piece piece, int moves) {}
    public void setPlaying(boolean d) {}
    public boolean haveAnyPiecePlaying() { return false; } //no sabem si necesari o no

    public boolean isWinner() {return winner;}
    public boolean isPlaying() {return playing;}

    public String getName() {return name;}
    public List<Piece> getPieces() {return pieces;}
    public String getColor() {return color;}
}