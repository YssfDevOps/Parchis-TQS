package test.model;

import main.model.Board;
import main.model.FirstSquare;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {

    @org.junit.jupiter.api.Test
    void getFirstSquare() {
        FirstSquare firstSquare = new FirstSquare(id: 5);
        Board board = new Board();

        FirstSquare fs = board.getFirstSquare("yellow");

        //mirem que retorni la primera casella correctment
        assertEquals(firstSquare.getId(), fs.getId());
    }

    @org.junit.jupiter.api.Test
    void getLastSquares() {
    }

    @org.junit.jupiter.api.Test
    void findSquare() {
    }
}