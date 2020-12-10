package game.model.pieces;

import game.model.Board;
import game.model.Player;
import game.model.Position;

public class King extends Piece {

    public King(Player player) {
        super(player);
    }

    @Override
    public char getChar() {
        return player != Player.WHITE ? 'k' : 'K';
    }

    @Override
    public boolean moveCheck(Board board, Position p_curr, Position p_new) {
        return !notcapturePiece(board, p_new) && !(1 < p_new.dRow(p_curr)) && p_curr.dCol(p_new) <= 1;
    }

    @Override
    public String toString() {
        return player + " King";
    }

}
