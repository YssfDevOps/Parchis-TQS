package main.model;


public class Piece {
    private Square square;
    private int id;
    private String color;
    private boolean playing;

    public Piece(String color, Square square) {}
    public void moveForward(int moves) {}
    public int getPosition() {return 0;}
    public boolean isPlaying() {return playing;}
    public void setPlaying(boolean playing) {this.playing = playing;}
    public void sendHome() { playing = false; }
    public void enterFinalPath() {}
    public void setSquare(Square square) {this.square = square;}
    public int getId() {return id;}
    public String getColor() {return color;}
}