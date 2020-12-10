package game.model.pieces;

import game.model.Board;
import game.model.Player;
import game.model.Position;

public class Rook extends Piece {

    public Rook(Player player) {
        super(player);
    }

    @Override
    public char getChar() { return player != Player.WHITE ? 'r' : 'R'; }

    @Override
    public String toString() {
        return player + " Rook";
    }

    @Override
    public boolean moveCheck(Board board, Position p_curr, Position p_new) {return straightCheck(board, p_curr, p_new); }

}
