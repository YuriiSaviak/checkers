package game;

import util.Pair;

import java.util.Map;

public class Game {
    static {
        System.loadLibrary("libcPlusPlus");
    }

    public native int[][] initializeBoard();

    // A function for the unit-testing. We pass the board to simulate the game.
    public native void initializeBoard(int[][] board);

    public native Map<Pair, Integer> movePiece(int fromRow, int fromCol, int toRow, int toCol);

    public native boolean canChoosePiece(int i, int j);

    public native String getWinner();
}