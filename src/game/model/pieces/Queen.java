package game.model.pieces;

import game.model.Board;
import game.model.Player;
import game.model.Position;

public class Queen extends Piece {

    public Queen(Player player) {
        super(player);
    }

    @Override
    public char getChar() {return player != Player.WHITE ? 'q' : 'Q'; }

    @Override
    public String toString() {return player + " Queen"; }

    @Override
    public boolean moveCheck(Board board, Position p_curr, Position p_new) {return !(!diagonalCheck(board, p_curr, p_new) && !straightCheck(board, p_curr, p_new)); }

}
