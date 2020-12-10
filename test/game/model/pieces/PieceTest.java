package game.model.pieces;

import game.model.Game;
import game.model.Position;
import org.junit.Test;

import static game.model.Player.BLACK;
import static game.model.Player.WHITE;
import static org.junit.Assert.*;


public class PieceTest {

    public static final int[] cols = new int[]{4,5,6,5,6,4,7,1,2,4,4,4,3,4,0};
    public static final int[] rows = new int[]{5,5,5,4,4,7,7,4,4,4,2,3,5,6,4};

    public void testPiece(Piece piece, int[] arr) throws Exception{
        Position p_curr = new Position(4,4);
        assertEquals(arr.length, rows.length);
        int idx = 0 ;
        if (idx < arr.length) {
            do {
                testPieceSingle(piece, p_curr, new Position(rows[idx], cols[idx]), arr[idx]);
                idx++;
            } while (idx < arr.length);
        }
    }

    public void testPieceSingle(Piece piece, Position p_curr, Position p_new, int expect) throws Exception{
        Game game = new Game();
        game.layout(new Position(4,4), piece);
        game.layout(new Position(4,2), new Pawn(WHITE));
        game.layout(new Position(4,1), new Pawn(BLACK));
        game.start();
        if (expect != 1) assertFalse(piece+" should not move to "+p_new, game.takeTurn(p_curr, p_new));
        assertTrue(piece+" should move to "+p_new, game.takeTurn(p_curr, p_new));
    }

    @Test
    public void testKing() throws Exception { testPiece(new King(WHITE), new int[]{1,1,0,1,0,0,0,0,0,0,0,1,1,0,0}); }

    @Test
    public void testKing() throws Exception { testPiece(new King(WHITE), new int[]{1,0,0,0,0,0,0,0,0,0,0,0,1,0,0}); }

    @Test
    public void testKing() throws Exception { testPiece(new King(WHITE), new int[]{1,1,1,1,1,0,0,0,0,0,0,1,1,0,1}); }

    @Test
    public void testQueen() throws Exception { testPiece(new Queen(WHITE), new int[]{1,1,0,1,1,1,1,0,0,0,0,1,1,1,0}); }

    @Test
    public void testQueen() throws Exception { testPiece(new Queen(WHITE), new int[]{1,0,0,0,0,0,1,0,0,0,0,1,1,1,0}); }

    @Test
    public void testQueen() throws Exception { testPiece(new Queen(WHITE), new int[]{1,1,0,0,0,0,0,1,1,1,1,0,0,1,0}); }

    @Test
    public void testBishop() throws Exception { testPiece(new Bishop(WHITE), new int[]{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0}); }

    @Test
    public void testBishop() throws Exception { testPiece(new Bishop(WHITE), new int[]{0,1,1,0,0,1,1,0,1,1,0,0,1,0,1}); }

    @Test
    public void testBishop() throws Exception { testPiece(new Bishop(WHITE), new int[]{0,1,0,1,0,0,1,0,0,1,0,0,1,0,1}); }

    @Test
    public void testPawn() throws Exception { testPiece(new Pawn(WHITE),  new int[]{1,0,0,0,0,0,0,0,0,0,0,0,1,0,0}); }

    @Test
    public void testPawn() throws Exception { testPiece(new Pawn(WHITE),  new int[]{1,1,1,1,1,0,0,0,0,0,1,1,1,0,0}); }

    @Test
    public void testPawn() throws Exception { testPiece(new Pawn(WHITE),  new int[]{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1}); }

    @Test
    public void testRook() throws Exception { testPiece(new Rook(WHITE), new int[]{1,0,0,1,1,1,0,0,0,0,0,1,0,1,0}); }

    @Test
    public void testRook() throws Exception { testPiece(new Rook(WHITE), new int[]{0,1,1,0,0,0,1,1,1,1,1,0,1,0,1}); }

    @Test
    public void testRook() throws Exception { testPiece(new Rook(WHITE), new int[]{1,1,0,1,1,1,0,0,1,0,0,1,1,0,1}); }

    @Test
    public void testKnight() throws Exception { testPiece(new Knight(WHITE), new int[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}); }

    @Test
    public void testKnight() throws Exception { testPiece(new Knight(WHITE), new int[]{0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0}); }

    @Test
    public void testKnight() throws Exception { testPiece(new Knight(WHITE), new int[]{1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}); }

}
