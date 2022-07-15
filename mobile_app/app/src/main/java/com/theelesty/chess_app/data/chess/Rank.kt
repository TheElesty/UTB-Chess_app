package com.theelesty.chess_app.data.chess

enum class Rank(val white: Char, val black: Char) {
    KING('K','k'),
    QUEEN('Q','q'),
    ROOK('R','r'),
    BISHOP('B','b'),
    KNIGHT('N','n'),
    PAWN('P','p');
}