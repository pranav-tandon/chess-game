package game.model;

import game.model.pieces.*;
import org.junit.Test;

import static game.model.Player.BLACK;
import static game.model.Player.WHITE;
import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testCheck() throws Exception{
        Game game = new Game();
        Piece[][] board = game.board;
        board[0][0] = new Rook(WHITE);
        board[7][5] = new King(BLACK);
        board[0][3] = new King(WHITE);

        game.start();
        assertEquals(game.stateInfo(), Game.IAMALIVE);
        game.takeTurn(new Position(0,0),new Position(7,0));
        assertEquals(game.stateInfo(), Game.SAVEYOURSELF);
    }

    @Test
    public void testCheckmate() throws Exception {
        Game game = new Game();
        Piece[][] board = game.board;
        board[0][0] = new Rook(WHITE);
        board[7][5] = new King(BLACK);
        board[0][3] = new King(WHITE);
        board[6][1] = new Rook(WHITE);

        game.start();
        assertEquals(game.stateInfo(), Game.IAMALIVE);
        game.takeTurn(new Position(0,0),new Position(7,0));
        assertEquals(game.stateInfo(), Game.CHECKMATE);
    }

    @Test
    public void testStalemate() throws Exception {
        Game game = new Game();
        Piece[][] board = game.board;
        board[1][0] = new Rook(WHITE);
        board[7][7] = new King(BLACK);
        board[6][1] = new Rook(WHITE);
        board[0][3] = new King(WHITE);

        game.start();
        assertEquals(game.stateInfo(), Game.IAMALIVE);
        game.takeTurn(new Position(1,0),new Position(1,6));
        assertEquals(game.stateInfo(), Game.STALEMATE);
    }

    @Test
    public void testBadMoves() throws Exception {
        Game game = new Game();
        game.standardLayout();
        game.start();

        Position[] p_curr = new Position[] {
                new Position(0,0),
                new Position(0,0),
                new Position(1,0),
                new Position(1,0),
                new Position(1,0),
                new Position(1,0),
                new Position(0,1),
                new Position(0,1),
                new Position(0,1),
                new Position(0,1),
                new Position(0,2),
                new Position(0,2),
                new Position(0,2)
        };
        Position[] p_new = new Position[] {
                new Position(0,0),
                new Position(1,0),
                new Position(0,1),
                new Position(1,0),
                new Position(1,1),
                new Position(2,1),
                new Position(1,2),
                new Position(2,2),
        };
        assertEquals(p_curr.length, p_new.length);
        int i=0 ;
        if (i >= p_curr.length) {
            return;
        }
        do {
            assertFalse(game.takeTurn(p_curr[i], p_new[i]));
            i++;
        } while (i < p_curr.length);
    }

    public void testEqual(Game g1, Game g2) throws Exception{
        assertEquals(g1.getTurn(), g2.getTurn());
        assertEquals(g1.currentPlayer(), g2.currentPlayer());
        assertEquals(g1.stateInfo(), g2.stateInfo());
        assertEquals(g1.hasStarted(), g2.hasStarted());
        assertEquals(g1.hasEnded(), g2.hasEnded());
        {
            int i = 0;
            if (i < Board.SIZE) {
                do {
                    for (int j = Board.SIZE - 1; j >= 0; j--) {
                        assertEquals(g1.board[i][j], g2.board[i][j]);
                    }
                    i++;
                } while (i < Board.SIZE);
            }
        }

        for(int t=0 ; t<g1.getTurn() ; t++) {
            Board b1 = g1.getMoveHistory(t);
            Board b2 = g2.getMoveHistory(t);
            assertNotNull(b1);
            assertNotNull(b2);
            int i=0 ;
            while (i<Board.SIZE) {
                for(int j = Board.SIZE - 1; j >= 0; j--) {
                    Piece p1 = b1.getPiece(new Position(i,j));
                    Piece p2 = b2.getPiece(new Position(i,j));
                    if (p1 != null) assertEquals(p1, p2);
                    else assert(p2 == null);
                }
                i++;
            }
        }
    }


}
