package com.theelesty.chess_app.data.chess

data class Piece (var col : Int,
                  var row : Int,
                  var player : Player,
                  var rank : Rank,
                  var resId : Int){

    override fun toString(): String {
        return "c.$col r.$row  - $rank $player"
    }
}