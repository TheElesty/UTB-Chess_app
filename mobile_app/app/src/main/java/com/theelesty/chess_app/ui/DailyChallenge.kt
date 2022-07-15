package com.theelesty.chess_app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.theelesty.chess_app.DailyChallengeViewModel
import com.theelesty.chess_app.DailyChallengeViewModelFactory
import com.theelesty.chess_app.R
import com.theelesty.chess_app.data.BoardInterface
import com.theelesty.chess_app.data.BoardModel
import com.theelesty.chess_app.data.Post
import com.theelesty.chess_app.data.UserDatastoreRepository
import com.theelesty.chess_app.data.api.Repository
import com.theelesty.chess_app.data.chess.MoveData
import com.theelesty.chess_app.data.chess.Piece
import com.theelesty.chess_app.data.chess.SquareData
import com.theelesty.chess_app.databinding.ActivityDailyChallengeBinding
import kotlinx.android.synthetic.main.activity_daily_challenge.*
import kotlinx.coroutines.launch
import retrofit2.Response

private const val TAG = "Debug: DailyChallenge"

class DailyChallenge : AppCompatActivity(), BoardInterface {
    private var _binding: ActivityDailyChallengeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DailyChallengeViewModel
    private var board = BoardModel()
    private lateinit var boardView :BoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDailyChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let { ab ->
            ab.title = getString(R.string.title_daily)
            ab.subtitle = getString(R.string.daily_loading)
        }

        UserDatastoreRepository.setAppContext(this)

        getDailyPuzzle()

        boardView = binding.dailyChallenge
        boardView.boardInterface = this

        getRandom.setOnClickListener{
            this.getNewPuzzle()
        }
    }

    private fun getDailyPuzzle() {
        val repository = Repository()
        val viewModelFactory = DailyChallengeViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[DailyChallengeViewModel::class.java]
        viewModel.getDaily()

        viewModel.dailyResponse.observe(this, { response -> parseResponse(response)})
    }

    private fun getNewPuzzle() {
        val repository = Repository()
        val viewModelFactory = DailyChallengeViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[DailyChallengeViewModel::class.java]
        viewModel.getRandom()

        viewModel.randomResponse.observe(this, { response -> parseResponse(response)})
    }

    private fun parseResponse(response: Response<Post>) {
            if(response.isSuccessful){
                board.setFEN(response.body()?.fen!!)
                board.reload()

                supportActionBar?.let { ab ->
                    ab.subtitle = response.body()?.title!!
                }

                val pgn = response.body()?.pgn!!.replace('[', ' ').split("]")
                solutionText.text = getString(R.string.solution, pgn[pgn.size - 1].trim())

                val boardView = findViewById<BoardView>(R.id.dailyChallenge)
                boardView.invalidate()
            } else {
                Log.d(TAG, "Can´t load challenge")
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