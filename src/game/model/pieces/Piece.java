package game.model.pieces;

import game.model.Board;
import game.model.Player;
import game.model.Position;

import java.util.Objects;

public abstract class Piece {

    protected final Player player;

    public Piece(Player player) {
        this.player = player;
    }

    public abstract char getChar();

     public abstract boolean moveCheck(Board board, Position p_curr, Position p_new);

    protected boolean capturePiece(Board board, Position pos) { return board.getPiece(pos) != null && board.getPiece(pos).player != player; }

    protected boolean notcapturePiece(Board board, Position pos) { return board.getPiece(pos) != null && board.getPiece(pos).player == player; }

    protected boolean straightCheck(Board board, Position p_curr, Position p_new) {

        if(p_curr.row != p_new.row && p_curr.col != p_new.col) return false;
        int dRow;
        int dCol;
        if(p_new.row == p_curr.row && p_new.col > p_curr.col) {//right
            dRow = 0;
            dCol = 1;
        }else if(p_new.row == p_curr.row && p_new.col < p_curr.col) {//left
            dRow = 0;
            dCol = -1;
        }else if(p_new.row > p_curr.row) {//up
            dRow = 1;
            dCol = 0;
        }else{//down
            dRow = -1;
            dCol = 0;
        }
        return forwardCheck(board, p_curr, p_new, dRow, dCol);
    }


    protected boolean diagonalCheck(Board board, Position p_curr, Position p_new) {
        if(Math.abs(p_curr.row-p_new.row) != Math.abs(p_curr.col-p_new.col)) return false;
        int dRow;
        int dCol;
        if(!(p_curr.row >= p_new.row || p_new.col <= p_curr.col)) { //up right
            dRow = 1;
            dCol = 1;
        }else if(!(p_curr.row <= p_new.row) && p_new.col > p_curr.col) { //down right
                dRow = -1;
                dCol = 1;
        } else if(!(p_curr.row >= p_new.row || p_new.col >= p_curr.col)){ //up left
            dRow = 1;
            dCol = -1;
        } else{ //down left
            dRow = -1;
            dCol = -1;
        }
        return forwardCheck(board, p_curr, p_new, dRow, dCol);

    }

    private boolean forwardCheck(Board board, Position p_curr, Position p_new, int dRow, int dCol) {
        return backwardCheck(board, p_curr.relativePosition(dRow, dCol), p_new, dRow, dCol);
    }

    private boolean backwardCheck(Board board, Position p_curr, Position p_new, int dRow, int dCol) {
        if (!board.outOfBoundary(p_curr)) {
            Piece piece;
            if ((piece = board.getPiece(p_curr)) == null) return p_curr.equals(p_new) || backwardCheck(board, p_curr.relativePosition(dRow, dCol), p_new, dRow, dCol);
            if (piece.player == player) return false;
            return p_curr.equals(p_new);
        } else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this != o) if (o != null && getClass() == o.getClass()) {
            Piece piece = (Piece) o;
            return player == piece.player;
        } else return false;
        else return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    public Player getPlayer() {
        return player;
    }

}
