package game.model;

import game.model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static game.model.Player.BLACK;
import static game.model.Player.WHITE;
import static java.lang.System.*;
import static java.util.stream.IntStream.*;

public class Board {

    public static final int IAMALIVE = 0;
    public static final int SAVEYOURSELF = 1;
    public static final int CHECKMATE = 2;
    public static final int STALEMATE = 3;

    public static final int SIZE = 8;

    protected Piece[][] board;


    public Board() {
        board = new Piece[SIZE][SIZE];
    }

    public Board(Piece[][] layout) {
        this();
        for(int row=0 ; row<SIZE ; row++)
            for(int col=0 ; col<SIZE ; col++)
                board[row][col] = layout[row][col];
    }


    public int stateInfo(Player player) {
        if (!checkIn(player)) {
            if (checkStaleOrCheck(player)) return STALEMATE;
            return IAMALIVE;
        }
        if (checkStaleOrCheck(player)) return CHECKMATE;
        return SAVEYOURSELF;
    }

    public boolean checkIn(Player player) { return (getKingPosition(player) != null) ? attackCheck(getKingPosition(player)) :false; }

    public boolean moveCheck(Player player, Position p_curr, Position p_new) {
        return (p_curr == null || p_new == null || player == null || (outOfBoundary(p_curr) || outOfBoundary(p_new)) || (p_curr.equals(p_new)) || (getPiece(p_curr) == null || getPiece(p_curr).getPlayer() != player) || (!getPiece(p_curr).moveCheck(this, p_curr, p_new)) || (inCheckAfterMove(player, p_curr, p_new))) ? false : true;
    }

    protected boolean movePiece(Player player, Position p_curr, Position p_new) {
        if (moveCheck(player, p_curr, p_new)) {
            setPiece(null, p_curr);
            setPiece(getPiece(p_curr), p_new);
            return true;
        } else return false;
    }

    public boolean attackCheck(Position target) {
        if(getPiece(target) == null) return false;
        for(int row = SIZE - 1; row >= 0; row--) {
            for(int col = SIZE - 1; col >= 0; col--) {
                Position p_curr = new Position(row,col);
                if (getPiece(p_curr) != null && getPiece(p_curr).getPlayer() == getPiece(target).getPlayer().opponent() && getPiece(p_curr).moveCheck(this, p_curr, target)) return true;
            }
        }
        return false;
    }

    public Board copy() {
        Board ret = new Board();
        for(int i=0 ; i<SIZE ; i++) for(int j=0 ; j<SIZE ; j++) ret.board[i][j] = board[i][j];
        return ret;
    }

    public void printBoard() {
        for(int i=SIZE-1 ; i>=0 ; i--) {
            out.print(i+"|");
            for(int j = SIZE - 1; j >= 0; j--) {
                if (board[i][j] != null) out.print(board[i][j].getChar());
                else out.print('.');
            }
        }
        out.print(" +");
        range(0, SIZE).map(j -> '-').forEachOrdered(out::print);
        range(0, SIZE).forEachOrdered(out::print);
    }
    private boolean checkStaleOrCheck(Player player) {
        if(getKingPosition(player) == null) return false;
        for (Position pos : getPiecePositions(player)) {
            for (int newRow = 0; newRow < SIZE; newRow++) {
                for (int newCol = SIZE - 1; newCol >= 0; newCol--) {
                    Position p_new = new Position(newRow, newCol);
                    if (movePiece(player, pos, p_new)) {
                        if (!checkIn(player)) {
                            restore(copy());
                            return false;
                        }
                        restore(copy());
                    }
                }
            }
        }
        return true;
    }

    public boolean inCheckAfterMove(Player player, Position p_curr, Position p_new) {
        setPiece(null, p_curr);
        setPiece(getPiece(p_curr), p_new);
        restore(copy());
        return checkIn(player);
    }

    public boolean outOfBoundary(Position pos) {
        return outOfBoundary(pos.row, pos.col);
    }

    public boolean outOfBoundary(int row, int col) {
        return row < 0 || col < 0 || row >= SIZE || col >= SIZE;
    }

    public Piece getPiece(Position pos) {
        return board[pos.row][pos.col];
    }

    public List<Position> getPiecePositions(Player player) {
        List<Position> list = new ArrayList<>();
        for(int row = SIZE - 1; row >= 0; row--) {
            for(int col = SIZE - 1; col >= 0; col--) {
                if (board[row][col] != null && board[row][col].getPlayer() == player) {
                    if (!(board[row][col] instanceof King) || 1 > list.size()) list.add(new Position(row, col));
                    list.add(list.get(list.size() - 1));
                    list.set(0, new Position(row, col));
                }
            }
        }
        return list;
    }

    public Position getKingPosition(Player player) {
        for(int i=0 ; i<SIZE ; i++) for(int j=0 ; j<SIZE ; j++) if (!(board[i][j] == null || board[i][j].getPlayer() != player || !(board[i][j] instanceof King))) return new Position(i,j);
        return null;
    }
    protected void restore(Board history) {
        for(int i=0 ; i<SIZE ; i++) for(int j=0 ; j<SIZE ; j++) board[i][j] = history.board[i][j];
    }

    protected void setPiece(Piece piece, Position pos) {
        board[pos.row][pos.col] = piece;
    }

    protected void standardLayout() {
        //Black Pieces
        board[0][0] = new Rook(WHITE);
        board[0][1] = new Knight(WHITE);
        board[0][2] = new Bishop(WHITE);
        board[0][3] = new King(WHITE);
        board[0][4] = new Queen(WHITE);
        board[0][5] = new Bishop(WHITE);
        board[0][6] = new Knight(WHITE);
        board[0][7] = new Rook(WHITE);

        board[1][0] = new Pawn(WHITE);
        board[1][1] = new Pawn(WHITE);
        board[1][2] = new Pawn(WHITE);
        board[1][3] = new Pawn(WHITE);
        board[1][4] = new Pawn(WHITE);
        board[1][5] = new Pawn(WHITE);
        board[1][6] = new Pawn(WHITE);
        board[1][7] = new Pawn(WHITE);


        //White Pieces
        board[7][0] = new Rook(BLACK);
        board[7][1] = new Knight(BLACK);
        board[7][2] = new Bishop(BLACK);
        board[7][3] = new King(BLACK);
        board[7][4] = new Queen(BLACK);
        board[7][5] = new Bishop(BLACK);
        board[7][6] = new Knight(BLACK);
        board[7][7] = new Rook(BLACK);

        board[6][0] = new Pawn(BLACK);
        board[6][1] = new Pawn(BLACK);
        board[6][2] = new Pawn(BLACK);
        board[6][3] = new Pawn(BLACK);
        board[6][4] = new Pawn(BLACK);
        board[6][5] = new Pawn(BLACK);
        board[6][6] = new Pawn(BLACK);
        board[6][7] = new Pawn(BLACK);
    }
}
