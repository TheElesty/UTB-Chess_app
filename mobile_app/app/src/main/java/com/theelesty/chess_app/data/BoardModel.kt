package com.theelesty.chess_app.data

import com.theelesty.chess_app.data.chess.*

private const val TAG = "Debug: BoardModel"

class BoardModel(fen: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") {
    private var board: Board
    private var fenParser: FENParser = FENParser(fen)

    private val source = SquareData(-1,-1, SquareHighlights.SOURCE)
    private val target = SquareData(-1,-1,SquareHighlights.SOURCE)

    init {
        board = fenParser.parseFEN()
    }

    override fun toString(): String {
        var boardString = " \n"

        for(row in 7 downTo 0 ) {
            boardString += "$row"
            for (col in 0..7) {
                val piece = getPieceByPosition(col, row)
                val white = piece?.player == Player.WHITE

                boardString += if(piece != null) {
                    " " + when (piece.rank) {
                        Rank.KING -> (if (white) Rank.KING.white else Rank.KING.black)
                        Rank.QUEEN -> (if (white) Rank.QUEEN.white else Rank.QUEEN.black)
                        Rank.ROOK -> (if (white) Rank.ROOK.white else Rank.ROOK.black)
                        Rank.BISHOP -> (if (white) Rank.BISHOP.white else Rank.BISHOP.black)
                        Rank.KNIGHT -> (if (white) Rank.KNIGHT.white else Rank.KNIGHT.black)
                        Rank.PAWN -> (if (white) Rank.PAWN.white else Rank.PAWN.black)
                    }
                } else " ."
            }
            boardString += "\n"
        }

        boardString += "  0 1 2 3 4 5 6 7\n"

        return boardString
    }

    private fun getPieceByPosition(col : Int, row : Int): Piece? {
        for (piece in board.pieces) if (piece.col == col && piece.row == row) return piece

        return null
    }

    private fun isSquareOccupied(col : Int, row : Int): Boolean {
        for (piece in board.pieces) if (piece.col == col && piece.row == row) return true
        return false
    }

    private fun removePiece(piece: Piece) {
        board.pieces.remove(piece)
    }

    private fun isMovePseudoValid(piece: Piece, trgCol: Int, trgRow: Int): Boolean {
        return true
    }

    fun reload() {
        board = fenParser.parseFEN()

        source.col = -1
        source.row = -1

        target.col = -1
        target.row = -1
    }

    fun setFEN(fen: String) {
        fenParser.setFEN(fen)
    }

    fun getPieces(): MutableList<Piece> {
        return board.pieces.toMutableList()
    }

    fun movePiece(srcCol: Int, srcRow: Int, trgCol: Int, trgRow: Int) {
        if(srcCol == trgCol && srcRow == trgRow) return
        
        val pieceToMove = getPieceByPosition(srcCol, srcRow) ?: return
        val pieceOnTarget = getPieceByPosition(trgCol, trgRow)

        if(!isMovePseudoValid(pieceToMove, trgCol, trgRow)) return

        if(isSquareOccupied(trgCol, trgRow)) removePiece(pieceOnTarget!!)

        pieceToMove.col = trgCol
        pieceToMove.row = trgRow
    }

    fun getFEN(): String {
        return fenParser.getFEN(board)
    }

    fun getHighlightedSquares(): MutableList<SquareData> {
        val list = mutableListOf<SquareData>()

        list.add(source)
        list.add(target)

        //TODO add movable a attackable squares
        return list
    }

    fun setSourceSquare(col: Int, row: Int) {
        source.col = col
        source.row = row
    }
    fun setTargetSquare(col: Int, row: Int) {
        target.col = col
        target.row = row
    }

}