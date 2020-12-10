package game.model;

import game.model.pieces.*;
import org.junit.Test;

import java.util.List;

import static game.model.Player.BLACK;
import static game.model.Player.WHITE;
import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testBasic() {
        Board board = Board.standardBoard();
        assertTrue(board.outOfBoundary(-1,-1));
        assertTrue(board.outOfBoundary(-1,3));

        assertNull(board.getPiece(2,4));
        assertNull(board.getPiece(3,3));

        assertEquals(new Pawn(WHITE), board.getPiece(1,3));
        assertEquals(new King(WHITE), board.getPiece(0,4));

        List<Position> whitePieces = board.getPiecePositions(WHITE);
        List<Position> blackPieces = board.getPiecePositions(BLACK);
        assertEquals(whitePieces.size(), 16);
        assertEquals(blackPieces.size(), 16);

    }

    @Test
    public void testposition() {
        Board board = Board.standardBoard();
        assertEquals(new Position(0, 4), board.getKingPosition(WHITE));
        assertEquals(new Position(7, 4), board.getKingPosition(BLACK));
    }

    @Test
    public void testIAMALIVE() {
        Board board = Board.standardBoard();
        assertEquals(Board.IAMALIVE, board.stateInfo(WHITE));
        assertEquals(Board.IAMALIVE, board.stateInfo(BLACK));

        Piece[][] layout = new Piece[Board.SIZE][Board.SIZE];
        layout[0][4] = new King(WHITE);
        layout[0][0] = new King(BLACK);
        layout[1][3] = new Queen(WHITE);
        board = new Board(layout);
        assertEquals(Board.IAMALIVE, board.stateInfo(BLACK));

    }

    @Test
    public void testCheck() {
        Board board;
        Piece[][] layout = new Piece[Board.SIZE][Board.SIZE];
        layout[0][4] = new King(WHITE);
        layout[0][0] = new King(BLACK);
        layout[1][1] = new Queen(WHITE);
        board = new Board(layout);
        assertEquals(Board.SAVEYOURSELF, board.stateInfo(BLACK));

    }

    @Test
    public void testStaleMate() {
        Board board;
        Piece[][] layout = new Piece[Board.SIZE][Board.SIZE];
        layout[0][4] = new King(WHITE);
        layout[0][0] = new King(BLACK);
        layout[1][2] = new Queen(WHITE);
        board = new Board(layout);
        assertEquals(Board.STALEMATE, board.stateInfo(BLACK));

    }

    @Test
    public void testCheckMate() {
        Board board;
        Piece[][] layout = new Piece[Board.SIZE][Board.SIZE];
        layout[1][2] = new King(WHITE);
        layout[0][0] = new King(BLACK);
        layout[1][1] = new Queen(WHITE);
        board = new Board(layout);

    }

    @Test
    public void testPrint() {
        Board board = Board.standardBoard();
        board.printBoard();
        Piece[][] layout = new Piece[Board.SIZE][Board.SIZE];
        layout[0][4] = new King(WHITE);
        layout[7][4] = new King(BLACK);
        layout[0][0] = new Sniper(WHITE);
        layout[7][1] = new Cannon(BLACK);
        Board board1 = new Board(layout);
        board1.printBoard();
    }





}
