#include "Board.h"

#include <unordered_set>

Board::Piece Board::getPiece(int x, int y) const {
    return cells[y][x];
}

void Board::setPiece(int x, int y, const Piece piece) {
    cells[y][x] = piece;
}

bool Board::isOccupied(int x, int y) const {
    return cells[y][x] != EMPTY;
}

map<pair<int, int>, Board::Piece> Board::movePiece(int fromRow, int fromCol, int toRow, int toCol) {
    if (!canChoosePiece(fromCol, fromRow) || isOccupied(toCol, toRow) || fromCol == toCol || fromRow == toRow)
        return {};

    bool containsMy = false;
    bool able = false;
    for (const auto &entry: checkCaptures()) {
        pair<int, int> key = entry.first;

        for (const auto &variant: entry.second) {
            if (key.first == fromCol && key.second == fromRow && variant.first == toCol && variant.second == toRow) {
                containsMy = true;
                break;
            }
            if (!isMyEnemy(key.first, key.second))
                able = true;
        }

        if (containsMy)
            break;
    }

    if (!containsMy && able)
        return {};

    map<pair<int, int>, Piece> map{};

    int rowDiff = toRow - fromRow;
    int colDiff = toCol - fromCol;

    Piece piece = getPiece(fromCol, fromRow);

    bool isKillingSpree = false;
    if (piece == WHITE_PAWN || piece == BLACK_PAWN) {
        int dir = piece == WHITE_PAWN ? -1 : 1;

        if (rowDiff == dir && abs(colDiff) == 1) {
            if (piece == BLACK_PAWN && toRow == BOARD_SIZE - 1) {
                setPiece(toCol, toRow, BLACK_QUEEN);
                map.insert({{toCol, toRow}, BLACK_QUEEN});
            } else if (piece == WHITE_PAWN && toRow == 0) {
                setPiece(toCol, toRow, WHITE_QUEEN);
                map.insert({{toCol, toRow}, WHITE_QUEEN});
            } else {
                setPiece(toCol, toRow, piece);
                map.insert({{toCol, toRow}, piece});
            }

            setPiece(fromCol, fromRow, EMPTY);
            map.insert({{fromCol, fromRow}, EMPTY});
        } else if (abs(rowDiff) == abs(2 * dir) && abs(colDiff) == 2) {
            int midCol = (fromCol + toCol) / 2;
            int midRow = (fromRow + toRow) / 2;

            if (isMyEnemy(midCol, midRow)) {
                if (piece == BLACK_PAWN && toRow == BOARD_SIZE - 1) {
                    setPiece(toCol, toRow, BLACK_QUEEN);
                    map.insert({{toCol, toRow}, BLACK_QUEEN});
                } else if (piece == WHITE_PAWN && toRow == 0) {
                    setPiece(toCol, toRow, WHITE_QUEEN);
                    map.insert({{toCol, toRow}, WHITE_QUEEN});
                } else {
                    setPiece(toCol, toRow, piece);
                    map.insert({{toCol, toRow}, piece});
                }

                isKillingSpree = true;
                setPiece(midCol, midRow, EMPTY);
                map.insert({{midCol, midRow}, EMPTY});

                setPiece(fromCol, fromRow, EMPTY);
                map.insert({{fromCol, fromRow}, EMPTY});
            }
        } else
            return {};
    } else if (piece == WHITE_QUEEN || piece == BLACK_QUEEN) {
        if (abs(rowDiff) == abs(colDiff)) {
            int stepRow = rowDiff > 0 ? 1 : -1;
            int stepCol = colDiff > 0 ? 1 : -1;

            int currRow = fromRow + stepRow;
            int currCol = fromCol + stepCol;

            while (currRow != toRow && currCol != toCol) {
                if (isOccupied(currCol, currRow) && currRow + stepRow != toRow && currCol + stepCol != toCol)
                    return {};

                currRow += stepRow;
                currCol += stepCol;
            }

            setPiece(fromCol, fromRow, EMPTY);
            map.insert({{fromCol, fromRow}, EMPTY});

            setPiece(toCol, toRow, piece);
            map.insert({{toCol, toRow}, piece});

            if (isOccupied(toCol - stepCol, toRow - stepRow)) {
                isKillingSpree = true;
                setPiece(toCol - stepCol, toRow - stepRow, EMPTY);
                map.insert({{toCol - stepCol, toRow - stepRow}, EMPTY});
            }
        }
    }

    for (const auto &entry: checkCaptures()) {
        pair<int, int> key = entry.first;
        if (!isMyEnemy(key.first, key.second) && isKillingSpree)
            return map;
    }

    switchTurn();

    return map;
}

bool Board::isMyEnemy(int x, int y) const {
    Piece piece = getPiece(x, y);
    return turn
               ? piece == BLACK_PAWN || piece == BLACK_QUEEN
               : piece == WHITE_PAWN || piece == WHITE_QUEEN;
}

bool Board::isEnemyFigure(int x1, int y1, int x2, int y2) const {
    Piece piece1 = getPiece(x1, y1);
    Piece piece2 = getPiece(x2, y2);

    return ((piece1 > 0 && piece2 < 0) || (piece1 < 0 && piece2 > 0));
}


void Board::switchTurn() {
    turn = !turn;
}

bool Board::canChoosePiece(int x, int y) const {
    Piece piece = getPiece(x, y);
    return turn
               ? piece == WHITE_PAWN || piece == WHITE_QUEEN
               : piece == BLACK_PAWN || piece == BLACK_QUEEN;
}

string Board::getWinner() const {
    bool isBlackDead = true;
    bool isWhiteDead = true;

    for (const auto &cell: cells)
        for (auto piece: cell) {
            if (piece == BLACK_PAWN || piece == BLACK_QUEEN)
                isBlackDead = false;

            if (piece == WHITE_PAWN || piece == WHITE_QUEEN)
                isWhiteDead = false;
        }

    if (isBlackDead)
        return "White";

    if (isWhiteDead)
        return "Black";




    return "";
}


map<pair<int, int>, vector<pair<int, int> > > Board::checkCaptures() const {
    map<pair<int, int>, vector<pair<int, int> > > map{};
    vector<pair<int, int> > directions = {{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};
    vector<pair<int, int> > directionsQueen = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

    for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            Piece piece = cells[i][j];

            if (piece == BLACK_PAWN || piece == WHITE_PAWN)
                for (auto [rowOffset, colOffset]: directions) {
                    int targetRow = i + rowOffset;
                    int targetCol = j + colOffset;

                    int midCol = (j + targetCol) / 2;
                    int midRow = (i + targetRow) / 2;

                    if (targetRow >= 0 && targetRow < BOARD_SIZE && targetCol >= 0 && targetCol < BOARD_SIZE)
                        if (isEnemyFigure(midCol, midRow, j, i) && !isOccupied(targetCol, targetRow))
                            map[{j, i}].emplace_back(targetCol, targetRow);
                }
            else if (piece == BLACK_QUEEN || piece == WHITE_QUEEN)
                for (auto [rowOffset, colOffset]: directionsQueen) {
                    int targetCol = j + colOffset;
                    int targetRow = i + rowOffset;

                    bool foundEnemy = false;
                    int enemyCol = -1, enemyRow = -1;

                    while (targetRow >= 0 && targetRow < BOARD_SIZE && targetCol >= 0 && targetCol < BOARD_SIZE) {
                        if (isOccupied(targetCol, targetRow)) {
                            if (isEnemyFigure(targetCol, targetRow, j, i)) {
                                if (foundEnemy) break;
                                foundEnemy = true;
                                enemyCol = targetCol;
                                enemyRow = targetRow;
                            } else {
                                break;
                            }
                        } else if (foundEnemy) {
                            map[{j, i}].emplace_back(targetCol, targetRow);
                            break;
                        }

                        targetRow += rowOffset;
                        targetCol += colOffset;
                    }
                }
        }
    }

    return map;
}
