package com.theelesty.chess_app.data

import com.theelesty.chess_app.data.chess.MoveData
import com.theelesty.chess_app.data.chess.Piece
import com.theelesty.chess_app.data.chess.SquareData

interface BoardInterface {
    fun getPieces(): MutableList<Piece>
    fun movePiece(srcCol: Int, srcRow: Int, trgCol: Int, trgRow: Int)
    fun movePiece(move : MoveData)

    fun getLightColor(): String
    fun getDarkColor(): String

    fun getHighlightedSquares(): MutableList<SquareData>

    fun setSourceSquare(col: Int, row: Int)
    fun setTargetSquare(col: Int, row: Int)
}