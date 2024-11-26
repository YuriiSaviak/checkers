package test;

import game.Game;
import org.junit.jupiter.api.Test;
import util.Pair;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @Test
    void initializeBoard() {
        game = new Game();

        assertArrayEquals(new int[][]{
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {-1, 0, -1, 0, -1, 0, -1, 0},
                {0, -1, 0, -1, 0, -1, 0, -1},
                {-1, 0, -1, 0, -1, 0, -1, 0}
        }, game.initializeBoard(), "Array was initialized wrongly.");
    }

    @Test
    void pawnCantGoBackward() {
        game = new Game();
        game.initializeBoard();

        assertEquals(new HashMap<>(), game.movePiece(0, 1, 1, 0), "A figure can't go here.");
    }

    @Test
    void pawnCantGoVertically() {
        game = new Game();
        game.initializeBoard();

        assertEquals(new HashMap<>(), game.movePiece(0, 1, 1, 1), "A figure can't go vertically.");
    }

    @Test
    void pawnCantGoHorizontally() {
        game = new Game();
        game.initializeBoard();

        assertEquals(new HashMap<>(), game.movePiece(0, 1, 0, 1), "A figure can't go horizontally.");
    }

    @Test
    void usualPawnMovement() {
        game = new Game();
        game.initializeBoard();

        assertEquals(new HashMap<Pair, Integer>() {
            {
                put(new Pair(0, 5), 0);
                put(new Pair(1, 4), -1);
            }
        }, game.movePiece(5, 0, 4, 1), "A figure can go here.");
    }

    @Test
    void backwardQueenMovement() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, -2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>() {
            {
                put(new Pair(1, 0), -2);
                put(new Pair(4, 3), 0);
            }
        }, game.movePiece(3, 4, 0, 1), "A queen can go here.");
    }

    @Test
    void queenCantGoVertically() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, -2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>(), game.movePiece(3, 4, 4, 4), "A queen can't go here.");
    }

    @Test
    void queenCantGoHorizontally() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, -2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>(), game.movePiece(3, 4, 3, 5), "A queen can't go here.");
    }

    @Test
    void forwardQueenMovement() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, -2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>() {
            {
                put(new Pair(6, 5), -2);
                put(new Pair(4, 3), 0);
            }
        }, game.movePiece(3, 4, 5, 6), "A queen can go here.");
    }

    @Test
    void queenCantMoveOverFigure() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, -2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>(), game.movePiece(3, 4, 0, 1), "A queen can't go over figure.");
    }

    @Test
    void pawnPromotion() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>() {
            {
                put(new Pair(0, 0), -2);
                put(new Pair(1, 1), 0);
            }
        }, game.movePiece(1, 1, 0, 0), "A pawn should become a queen.");
    }

    @Test
    void pawnCapture() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>() {
            {
                put(new Pair(3, 3), -1);
                put(new Pair(1, 1), 0);
                put(new Pair(2, 2), 0);
            }
        }, game.movePiece(1, 1, 3, 3), "A pawn should capture a queen.");
    }

    @Test
    void pawnCantMoveTwice() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        game.movePiece(1, 1, 3, 3);
        assertEquals(new HashMap<Pair, Integer>(), game.movePiece(3, 3, 4, 4), "Cant go twice without capturing.");
    }

    @Test
    void pawnCantCaptureTooFar() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>(), game.movePiece(1, 1, 4, 4), "A pawn shouldn't capture a queen.");
    }

    @Test
    void pawnCantCaptureBecauseOfAnotherPawn() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>(), game.movePiece(1, 1, 4, 4), "A pawn shouldn't capture a queen.");
    }

    @Test
    void pawnKillingSpree() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        game.movePiece(1, 1, 3, 3);

        assertEquals(new HashMap<Pair, Integer>() {
            {
                put(new Pair(3, 3), 0);
                put(new Pair(4, 2), 0);
                put(new Pair(5, 1), -1);
            }
        }, game.movePiece(3, 3, 1, 5), "A pawn should capture a queen.");
    }

    @Test
    void queenCapture() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, -2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>() {
            {
                put(new Pair(4, 4), 0);
                put(new Pair(1, 1), 0);
                put(new Pair(5, 5), -2);
            }
        }, game.movePiece(1, 1, 5, 5), "A queen should capture a pawn.");
    }

    @Test
    void queenCantCaptureBecauseOfAnotherPawn() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, -2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        assertEquals(new HashMap<Pair, Integer>(), game.movePiece(1, 1, 5, 5), "A queen should capture a pawn.");
    }

    @Test
    void canChoosePiece() {
        Game game = new Game();
        game.initializeBoard();

        assertTrue(game.canChoosePiece(7, 0), "You are able to select your cell.");
    }

    @Test
    void cantChoosePiece() {
        Game game = new Game();
        game.initializeBoard();

        assertFalse(game.canChoosePiece(0, 1), "You can't select enemy or empty cell.");
    }

    @Test
    void noWinner() {
        Game game = new Game();
        game.initializeBoard();

        assertEquals("", game.getWinner(), "There is no winner yet.");
    }

    @Test
    void whiteIsWinner() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, -2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        game.movePiece(0, 1, 3, 4);

        assertEquals("White", game.getWinner(), "White is a winner.");
    }

    @Test
    void blackIsWinner() {
        game = new Game();
        game.initializeBoard(new int[][]{
                {0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });

        game.movePiece(2, 3, 1, 2);
        game.movePiece(0, 1, 2, 3);

        assertEquals("Black", game.getWinner(), "Black is a winner.");
    }
}