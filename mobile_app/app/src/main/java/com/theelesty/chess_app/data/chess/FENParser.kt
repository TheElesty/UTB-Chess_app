package com.theelesty.chess_app.data.chess

import com.theelesty.chess_app.R

class FENParser(var fen: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") {

    fun setFEN(fen: String) {
        this.fen = fen
    }

    fun parseFEN(): Board {
        val split = fen.split(' ')
        val pieces = mutableListOf<Piece>()

        var row = 7
        var col = 0

        for (c in split[0]){
            // new row split
            if(c == '/') {
                row -= 1
                col = 0
                continue
            }

            //number represents empty squares
            if(c.isDigit()) {
                col += c.digitToInt()
                continue
            }

            c.isLowerCase()

            pieces.add(Piece(col,
                row,
                getPlayer(c),
                getRank(c),
                getPieceVector(getPlayer(c), getRank(c))))
            col += 1
        }

        return Board(
            pieces,
            if (split[1] == "w") Player.WHITE else Player.BLACK,
            split[2],
            split[3],
            split[4].toInt() ,
            split[5].toInt())
    }

    fun getFEN(board: Board): String {
        var fen = ""
        var tmpCount = 0

        for(row in 7 downTo 0){
            for (col in 0..7) {
                var tmpPiece: Piece? = null

                //look for piece on square(col, row)
                board.pieces.forEach { piece ->
                    if (piece.col == col && piece.row == row) tmpPiece = piece
                }

                //if exists write its FEN notation else handle empty squares notation
                if (tmpPiece == null) {
                    tmpCount++
                } else if (tmpPiece!!.col == col && tmpPiece!!.row == row) {
                    if(tmpCount != 0) fen += tmpCount.toString()

                    tmpCount = 0

                    fen +=  if(tmpPiece!!.player == Player.WHITE) tmpPiece!!.rank.white.toString()
                    else tmpPiece!!.rank.black.toString()
                }

            }
            if(tmpCount != 0) fen += tmpCount.toString()

            fen += "/"
            tmpCount = 0
        }

        //append end of FEN notation
        fen += " " + board.player.char.toString()
        fen += " " + board.castling
        fen += " " + board.enpTarget
        fen += " " + board.halfMove
        fen += " " + board.move

        return fen
    }

    private fun getPlayer(c: Char): Player {
        return if(c.isLowerCase()) Player.BLACK
        else Player.WHITE
    }

    private fun getRank(c: Char) : Rank {
        return when (c.lowercaseChar()) {
            'k' -> Rank.KING
            'q' -> Rank.QUEEN
            'r' -> Rank.ROOK
            'b' -> Rank.BISHOP
            'n' -> Rank.KNIGHT
            'p' -> Rank.PAWN
            else -> Rank.PAWN
        }
    }

    private fun getPieceVector(player: Player, rank: Rank): Int {
        return when (player) {
            Player.WHITE -> {
                when (rank) {
                    Rank.KING -> R.drawable.pieces_white_king
                    Rank.QUEEN -> R.drawable.pieces_white_queen
                    Rank.ROOK -> R.drawable.pieces_white_rook
                    Rank.BISHOP -> R.drawable.pieces_white_bishop
                    Rank.KNIGHT -> R.drawable.pieces_white_knight
                    Rank.PAWN -> R.drawable.pieces_white_pawn
                }
            }
            Player.BLACK -> {
                when (rank) {
                    Rank.KING -> R.drawable.pieces_black_king
                    Rank.QUEEN -> R.drawable.pieces_black_queen
                    Rank.ROOK -> R.drawable.pieces_black_rook
                    Rank.BISHOP -> R.drawable.pieces_black_bishop
                    Rank.KNIGHT -> R.drawable.pieces_black_knight
                    Rank.PAWN -> R.drawable.pieces_black_pawn
                }
            }
        }
    }
}