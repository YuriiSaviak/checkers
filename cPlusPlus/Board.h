#pragma once
#include <map>
#include <set>
#include <string>
#include <vector>

using std::map, std::string, std::vector, std::pair, std::set;

class Board {
public:
    enum Piece {
        EMPTY = 0,
        WHITE_PAWN = -1,
        WHITE_QUEEN = -2,
        BLACK_PAWN = 1,
        BLACK_QUEEN = 2,
    };

    static constexpr int BOARD_SIZE = 8;

    Piece cells[BOARD_SIZE][BOARD_SIZE]{
        {EMPTY, BLACK_PAWN, EMPTY, BLACK_PAWN, EMPTY, BLACK_PAWN, EMPTY, BLACK_PAWN},
        {BLACK_PAWN, EMPTY, BLACK_PAWN, EMPTY, BLACK_PAWN, EMPTY, BLACK_PAWN, EMPTY},
        {EMPTY, BLACK_PAWN, EMPTY, BLACK_PAWN, EMPTY, BLACK_PAWN, EMPTY, BLACK_PAWN},
        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
        {WHITE_PAWN, EMPTY, WHITE_PAWN, EMPTY, WHITE_PAWN, EMPTY, WHITE_PAWN, EMPTY},
        {EMPTY, WHITE_PAWN, EMPTY, WHITE_PAWN, EMPTY, WHITE_PAWN, EMPTY, WHITE_PAWN},
        {WHITE_PAWN, EMPTY, WHITE_PAWN, EMPTY, WHITE_PAWN, EMPTY, WHITE_PAWN, EMPTY}
    };

    map<pair<int, int>, Piece> movePiece(int fromCol, int fromRow, int toRow, int toCol);

    [[nodiscard]] bool canChoosePiece(int x, int y) const;

    [[nodiscard]] string getWinner() const;

private:
    bool turn = true; // if true - white, black - false

    [[nodiscard]] map<pair<int, int>, vector<pair<int, int> > > checkCaptures() const;

    void switchTurn();

    [[nodiscard]] Piece getPiece(int x, int y) const;

    void setPiece(int x, int y, Piece piece);

    [[nodiscard]] bool isOccupied(int x, int y) const;

    [[nodiscard]] bool isMyEnemy(int x, int y) const;

    [[nodiscard]] bool isEnemyFigure(int x1, int y1, int x2, int y2) const;
};
