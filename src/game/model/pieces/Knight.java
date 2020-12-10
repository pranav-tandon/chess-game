package game.model.pieces;

import game.model.Board;
import game.model.Player;
import game.model.Position;

public class Knight extends Piece {

    public Knight(Player player) {
        super(player);
    }

    @Override
    public char getChar() {
        return player != Player.WHITE ? 'n' : 'N';
    }

    @Override
    public boolean moveCheck(Board board, Position p_curr, Position p_new) {
        if(notcapturePiece(board, p_new)) return false;
        int dRow = p_curr.dRow(p_new);
        int dCol = p_curr.dCol(p_new);
        return dRow == 1 && dCol == 2 || dRow == 2 && dCol == 1;
    }

    @Override
    public String toString() {
        return player + " Knight";
    }

}
