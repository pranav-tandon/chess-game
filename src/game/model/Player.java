package game.model;

public enum Player {
    WHITE("White", 1, 1),
    BLACK("Black", -1, Board.SIZE-2);

    public final int forward;
    public final int pawnStartRow;
    public final String name;

    Player(String name, int forward, int pawnStartRow) {
        this.forward = forward;
        this.pawnStartRow = pawnStartRow;
        this.name = name;

    }
    @Override
    public String toString() {
        return name;
    }

    public Player opponent() {return this != WHITE ? WHITE : BLACK; }
}
