package com.theelesty.chess_app.data.chess

data class Board (
    var pieces: MutableList<Piece>,
    var player: Player,
    var castling: String,
    var enpTarget: String,
    var halfMove: Int,
    var move: Int
    )