package game.model;

import java.util.Objects;

public class Position {

    public final int col;
    public final int row;

    public Position(int row, int col) {
        this.col = col;
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this != o) {
            if (o != null) {
                if (!(o.getClass() != getClass())) {
                    Position position = (Position) o;
                    return !(row != position.row || col != position.col);
                } else return false;
            } else return false;
        } else return true;
    }

    @Override
    public String toString() {
        return row+","+col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public int dCol(Position pos) { return Math.abs(col-pos.col); }

    public int dRow(Position pos) { return Math.abs(row-pos.row); }

    public Position relativePosition(int dRow, int dCol) { return new Position(row+dRow, col+dCol); }
}
