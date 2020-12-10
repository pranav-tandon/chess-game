package game.model;

import game.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

import static game.model.Player.BLACK;
import static game.model.Player.WHITE;

public class Game extends Board {

    private List<Board> move_trace;
    private Player player_curr;
    private int chance;
    private boolean start;
    private boolean end;

    public Game() {
        board = new Piece[SIZE][SIZE];
        move_trace = new ArrayList<>();
        chance = 1;
        player_curr = WHITE;
        start = false;
        end = false;
    }

    public void standardLayout() {
        if (start) return;
        super.standardLayout();
    }

    public void layout(Position p, Piece piece) {
        if (start) return;
        setPiece(piece, p);
    }

    public void start() {
        start = true;
        addHistory();
        int state = stateInfo();
        if (!(state != CHECKMATE && state != STALEMATE)) end = true;
    }

    public void hardReset() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = null;
            }
        }

        move_trace.clear();
        chance = 1;
        player_curr = WHITE;
        start = false;
        end = false;
    }

    public void reset() {
        restore(move_trace.get(0));
        move_trace.clear();
        chance = 1;
        player_curr = WHITE;
        start = false;
        end = false;
    }

    public boolean takeChance(Player player, Position p_curr, Position p_new) {
        if ((start) && (!end) && (player == player_curr) && (movePiece(player, p_curr, p_new))) {
            chance++;
            player_curr = player_curr.opponent();
            addHistory();
            if (stateInfo() != Board.STALEMATE && stateInfo() != Board.CHECKMATE) return true;
            end = true;
            return true;
        } else return false;
    }

    public void revertOneStep() {
        if (chance > 1) {
            end = false;
            move_trace.remove(move_trace.size() - 1);
            chance -= 1;
            player_curr = player_curr.opponent();
            restore(move_trace.get(move_trace.size() - 1));
        } else return;
    }

    public void revert(int targetChance) {
        if (targetChance >= 1 && targetChance < this.chance) {
            if (!(move_trace.size() <= targetChance)) {
                do move_trace.remove(move_trace.size() - 1);
                while (!(targetChance >= move_trace.size()));
            }
            this.chance = targetChance;
            end = false;
            player_curr = targetChance % 2 == 0 ? BLACK : WHITE;
            restore(move_trace.get(move_trace.size() - 1));
        } else return;
    }

    public Board getHistory(int targetChance) {
        return (targetChance >= chance || targetChance < 0) ? null : move_trace.get(targetChance);
    }

    public Player getCurrentPlayer() {
        return player_curr;
    }

    public int getChance() {
        return chance;
    }

    public int stateInfo() {
        return stateInfo(player_curr);
    }

    public boolean getStart() {
        return start;
    }

    public boolean getEnd() {
        return end;
    }


    public boolean checkPosition(Position p_curr) {
        return getPiece(p_curr) == null ? false : getPiece(p_curr).getPlayer() == player_curr;
    }

    public boolean takeChance(Position p_curr, Position p_new) {
        return takeChance(player_curr, p_curr, p_new);
    }

    public void printBoard() {
        super.printBoard();
    }

    private void addHistory() {
        move_trace.add(copy());
    }

}
