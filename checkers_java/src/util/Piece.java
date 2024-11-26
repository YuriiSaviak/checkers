package util;


public enum Piece {
    EMPTY(0),
    WHITE_PAWN(-1),
    WHITE_QUEEN(-2),
    BLACK_PAWN(1),
    BLACK_QUEEN(2);

    private final int value;

    Piece(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Piece fromValue(int value) {
        for (Piece piece : Piece.values())
            if (piece.getValue() == value)
                return piece;

        return null;
    }
}