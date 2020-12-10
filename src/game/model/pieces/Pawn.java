package game.model.pieces;

import game.model.Board;
import game.model.Player;
import game.model.Position;

public class Pawn extends Piece {

    public Pawn(Player player) {
        super(player);
    }

    @Override
    public char getChar() {
        if(player == Player.WHITE) {
            return 'P';
        }
        return 'p';
    }

    @Override
    public boolean moveCheck(Board board, Position p_curr, Position p_new) {
        if((notcapturePiece(board, p_new)) || (p_curr.col != p_new.col) || (p_curr.row != player.pawnStartRow && p_new.row != p_curr.row + 2*player.forward))return false;
        else if(capturePiece(board, p_new)) return p_curr.dCol(p_new) == 1 && p_new.row == p_curr.row + player.forward;
        else return p_new.row == p_curr.row + player.forward;
    }

    @Override
    public String toString() {
        return player + " Pawn";
    }

}
