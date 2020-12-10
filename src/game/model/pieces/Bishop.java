package game.model.pieces;

import game.model.Board;
import game.model.Player;
import game.model.Position;

public class Bishop extends Piece {

    public Bishop(Player player) {
        super(player);
    }

    @Override
    public char getChar() {
            return player != Player.WHITE ? 'b' : 'B';
        }

    @Override
    public boolean moveCheck(Board board, Position p_curr, Position p_new) {
        return diagonalCheck(board, p_curr, p_new);
    }

    @Override
    public String toString() {
        return player + " Bishop";
    }

}
