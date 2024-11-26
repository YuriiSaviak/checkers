package frame;

import game.Game;
import util.Pair;
import util.Piece;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Objects;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

public class Frame extends JFrame implements KeyListener {
    public static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 70;

    private static final ImageIcon whiteCell = new ImageIcon("resources/texture/white_cell.jpg");
    private static final ImageIcon blackCell = new ImageIcon("resources/texture/black_cell.jpg");
    private static final ImageIcon blackPawn = new ImageIcon("resources/texture/black_pawn.png");
    private static final ImageIcon whitePawn = new ImageIcon("resources/texture/white_pawn.png");
    private static final ImageIcon whiteQueen = new ImageIcon("resources/texture/white_queen.png");
    private static final ImageIcon blackQueen = new ImageIcon("resources/texture/black_queen.png");

    private final Game game = new Game();
    private Pair selected = new Pair(-1, -1);
    private Pair currentKeyboard = new Pair(0, 0);
    private final JLabel[][] boardCells = new JLabel[BOARD_SIZE][BOARD_SIZE];

    public Frame() {
        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE));

        initializeBoard(jLayeredPane);
        add(jLayeredPane);

        addKeyListener(this);

        boardCells[0][0].setBorder(BorderFactory.createLineBorder(Color.GREEN));

        setTitle("Checkers");
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
    }

    private void initializeBoard(JLayeredPane jLayeredPane) {
        int[][] board = game.initializeBoard(); 

        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++) {
                int layer;
                JLabel cell = new JLabel();

                if (board[i][j] != 0) {
                    layer = JLayeredPane.PALETTE_LAYER;
                    cell.setIcon(defineIcon(board[i][j]));
                } else {
                    layer = JLayeredPane.DEFAULT_LAYER;
                    cell.setIcon((i + j) % 2 == 0 ? whiteCell : blackCell);
                }

                cell.setBounds(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                boardCells[i][j] = cell;
                jLayeredPane.add(cell, layer);

                int finalI = i;
                int finalJ = j;
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleClick(finalI, finalJ);
                    }
                });
            }
    }

    private void handleClick(int i, int j) {
        if (Objects.equals(selected, new Pair(j, i))) {
            boardCells[i][j].setBorder(null);
            selected.setNegative();
        } else if (!selected.isNegativePair()) {
            update(game.movePiece(selected.getY(), selected.getX(), i, j));
            boardCells[selected.getY()][selected.getX()].setBorder(BorderFactory.createLineBorder(null));
            selected.setNegative();

            String winner = game.getWinner();
            if (!winner.isEmpty()) {
                int input = JOptionPane.showOptionDialog(null, String.format("%s is the winner! Press Yes to play again or No to exit", winner),
                        "Winner", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    dispose();
                    SwingUtilities.invokeLater(Frame::new);
                } else
                    dispose();
            }
        } else if (game.canChoosePiece(i, j)) {
            boardCells[i][j].setBorder(BorderFactory.createLineBorder(Color.RED));
            selected = new Pair(j, i);
        }
    }

    private void update(Map<Pair, Integer> map) {
        for (Map.Entry<Pair, Integer> entry : map.entrySet())
            boardCells[entry.getKey().getY()][entry.getKey().getX()].setIcon(defineIcon(entry.getValue()));
    }

    private ImageIcon defineIcon(int value) {
        return switch (Piece.fromValue(value)) {
            case EMPTY -> null;
            case WHITE_PAWN -> whitePawn;
            case WHITE_QUEEN -> whiteQueen;
            case BLACK_PAWN -> blackPawn;
            case BLACK_QUEEN -> blackQueen;
            case null -> throw new RuntimeException("idk");
        };
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        Pair pair;
        if (key == VK_RIGHT)
            pair = new Pair(1, 0);
        else if (key == VK_LEFT)
            pair = new Pair(-1, 0);
        else if (key == VK_UP)
            pair = new Pair(0, -1);
        else if (key == VK_DOWN)
            pair = new Pair(0, 1);
        else if (key == VK_ENTER) {
            handleClick(currentKeyboard.getY(), currentKeyboard.getX());
            return;
        } else
            return;

        try {
            int newY = pair.getY() + currentKeyboard.getY();
            int newX = pair.getX() + currentKeyboard.getX();

            Border border = boardCells[currentKeyboard.getY()][currentKeyboard.getX()].getBorder();
            if (border instanceof LineBorder && ((LineBorder) border).getLineColor() != Color.RED)
                boardCells[currentKeyboard.getY()][currentKeyboard.getX()].setBorder(null);
            boardCells[newY][newX].setBorder(BorderFactory.createLineBorder(Color.GREEN));

            currentKeyboard = new Pair(newX, newY);
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }
}