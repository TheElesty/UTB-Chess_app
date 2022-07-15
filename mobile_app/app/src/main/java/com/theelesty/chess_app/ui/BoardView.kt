package com.theelesty.chess_app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.theelesty.chess_app.R
import com.theelesty.chess_app.data.BoardInterface
import com.theelesty.chess_app.data.chess.MoveData
import com.theelesty.chess_app.data.chess.Piece
import com.theelesty.chess_app.data.chess.SquareData
import com.theelesty.chess_app.data.chess.SquareHighlights

private const val TAG = "Debug: BoardView"

class BoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var pieceVectors = mutableMapOf<Int, VectorDrawableCompat?>()
    private val paintBlack = Paint()
    private val paintLight = Paint()
    private val paintDark = Paint()

    private val position = MoveData(-1,-1,-1,-1)
    var squareHighlights =  mutableListOf<SquareData>()
    var boardInterface: BoardInterface? = null


    private val resIds = setOf(
        R.drawable.pieces_white_king,
        R.drawable.pieces_white_queen,
        R.drawable.pieces_white_rook,
        R.drawable.pieces_white_bishop,
        R.drawable.pieces_white_knight,
        R.drawable.pieces_white_pawn,

        R.drawable.pieces_black_king,
        R.drawable.pieces_black_queen,
        R.drawable.pieces_black_rook,
        R.drawable.pieces_black_bishop,
        R.drawable.pieces_black_knight,
        R.drawable.pieces_black_pawn
    )

    init{
        resIds.forEach {
            pieceVectors[it] =  VectorDrawableCompat.create(context?.resources!!, it, null)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        paintBlack.color = Color.BLACK
        paintBlack.textSize = 50f

        val side = (width / 8).toFloat()

        drawBoard(canvas, side)
        highlightsSquares(canvas, side, boardInterface?.getHighlightedSquares())
        drawPieces(canvas, side.toInt())

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?: return false

        val side = width / 8

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                //if source is set -> set target, else set source
                if(position.srcCol != -1 && position.srcRow != -1) {
                    position.trgCol = (event.x / side).toInt()
                    position.trgRow = 7 - (event.y / side).toInt()

                    boardInterface?.setTargetSquare(position.trgCol, position.trgRow)
                    this.invalidate()
                } else {
                    position.srcCol = (event.x / side).toInt()
                    position.srcRow = 7 - (event.y / side).toInt()

                    boardInterface?.setSourceSquare(position.srcCol, position.srcRow)
                    boardInterface?.setTargetSquare(-1, -1)
                    this.invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                //if target is set move piece
                if(position.trgCol != -1 && position.trgRow != -1) {
                    boardInterface?.movePiece(position)
                    resetPosition(position)
                } else {
                    position.trgCol = (event.x / side).toInt()
                    position.trgRow = 7 - (event.y / side).toInt()

                    //if target is same as source set only source
                    if(position.srcCol == position.trgCol && position.srcRow == position.trgRow) {
                        position.trgCol = -1
                        position.trgRow = -1

                        boardInterface?.setSourceSquare(position.srcCol, position.srcRow)
                        this.invalidate()
                    } else {
                        boardInterface?.movePiece(position)

                        boardInterface?.setTargetSquare(position.trgCol, position.trgRow)
                        this.invalidate()

                        resetPosition(position)
                    }
                }
            }
        }

        return true
    }

    private fun resetPosition(move: MoveData) {
        move.srcCol = -1
        move.srcRow = -1
        move.trgCol = -1
        move.trgRow = -1
    }

    private fun drawBoard(canvas: Canvas?, side: Float) {
        val cLight =  boardInterface?.getLightColor() ?: "#d38d5f"
        val cDark = boardInterface?.getDarkColor() ?:"#784421"

        paintLight.color = Color.parseColor(cLight) //Color.rgb(211,141,95)
        paintDark.color = Color.parseColor(cDark) //Color.rgb(120,68,33)

        for(col in 0..7) {
            for(row in 0..7) {
                if((row + col) % 2 == 0 )
                    canvas?.drawRect(
                        col * side,
                        row * side,
                        col * side + side,
                        row * side + side,
                        paintLight)

                else canvas?.drawRect(
                    col * side,
                    row * side,
                    (col + 1) * side,
                    (row + 1) * side,
                    paintDark)
            }
        }
    }

    private fun highlightsSquares(canvas: Canvas?, side: Float, squares: MutableList<SquareData>?) {
        val paint = Paint()

        if(squares == null) return

        for(square in squares) {
            when(square.highlight) {
                SquareHighlights.SOURCE -> {
                    paint.color = Color.parseColor("#5500ff00")
                    canvas?.drawRect(
                        square.col * side,
                        (7 - square.row) * side,
                        square.col * side + side,
                        (7 - square.row) * side + side,
                        paint)
                }

                SquareHighlights.MOVABLE -> {
                    paint.color = Color.parseColor("#5500ff00")
                    canvas?.drawCircle(
                        square.col * side + side/2,
                        (7 - square.row) * side + side/2,
                        side/4,
                        paint)
                }

                SquareHighlights.ATTACKABLE -> {
                    paint.color = Color.parseColor("#55ff0000")
                    canvas?.drawRect(
                        square.col * side,
                        (7 - square.row) * side,
                        square.col * side + side,
                        (7 - square.row) * side + side,
                        paint)
                }

                SquareHighlights.NONE -> TODO()
            }
        }
    }

    private fun drawPieces(canvas: Canvas?, side: Int){
        for(piece in boardInterface?.getPieces()!!) drawPieceAtPosition(canvas, getPieceVector(piece), piece.col, piece.row, side)
    }

    private fun getPieceVector(piece: Piece): Int {
        return piece.resId
    }

    private fun drawPieceAtPosition(canvas: Canvas?, id: Int, col: Int, row: Int, side: Int) {

        // 7 - row because board is drawn from bottom
        pieceVectors[id]?.setBounds(
            col * side,
            (7 - row) * side,
            (col + 1) * side,
            (7 - row + 1) * side)

        pieceVectors[id]?.draw(canvas!!)
    }
}