package com.theelesty.chess_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.theelesty.chess_app.R
import com.theelesty.chess_app.data.BoardInterface
import com.theelesty.chess_app.data.BoardModel
import com.theelesty.chess_app.data.UserDatastoreRepository
import com.theelesty.chess_app.data.chess.MoveData
import com.theelesty.chess_app.data.chess.Piece
import com.theelesty.chess_app.data.chess.SquareData
import com.theelesty.chess_app.databinding.ActivityGameComputerBinding
import kotlinx.android.synthetic.main.activity_game_player.*
import kotlinx.coroutines.launch

private const val TAG = "Debug: GameComputer"

class GameComputer : AppCompatActivity(), BoardInterface {
    private var _binding: ActivityGameComputerBinding? = null
    private val binding get() = _binding!!

    private var board = BoardModel()
    private lateinit var boardView :BoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGameComputerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_vsComputer)

        UserDatastoreRepository.setAppContext(this)

        boardView = binding.gamePc
        boardView.boardInterface = this

        lifecycleScope.launch {
            val fen = UserDatastoreRepository.read("gamPC")  ?: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
            board.setFEN(fen)
            board.reload()

            boardView.invalidate()
        }

        saveData.setOnClickListener{
            lifecycleScope.launch {
                UserDatastoreRepository.save("gamPC", board.getFEN())
            }
        }

        newGame.setOnClickListener{
            board.setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
            board.reload()
            boardView.invalidate()

            lifecycleScope.launch {
                UserDatastoreRepository.save("gamPC", board.getFEN())
            }
        }

    }

    override fun getPieces(): MutableList<Piece> {
        return board.getPieces()
    }

    override fun movePiece(srcCol: Int, srcRow: Int, trgCol: Int, trgRow: Int) {
        board.movePiece(srcCol, srcRow, trgCol, trgRow)
        boardView.invalidate()
    }

    override fun movePiece(move: MoveData) {
        board.movePiece(move.srcCol, move.srcRow, move.trgCol, move.trgRow)
        boardView.invalidate()
    }

    override fun getLightColor(): String {
        var cLight = "#d38d5f"
        lifecycleScope.launch {
            cLight = UserDatastoreRepository.read("lightColor") ?: cLight
        }
        return cLight
    }

    override fun getDarkColor(): String {
        var cDark = "#784421"
        lifecycleScope.launch {
            cDark = UserDatastoreRepository.read("darkColor")  ?: cDark
        }
        return cDark
    }
    override fun getHighlightedSquares(): MutableList<SquareData> {
        return board.getHighlightedSquares().toMutableList()
    }

    override fun setSourceSquare(col: Int, row: Int) {
        board.setSourceSquare(col, row)
    }

    override fun setTargetSquare(col: Int, row: Int) {
        board.setTargetSquare(col, row)
    }
}