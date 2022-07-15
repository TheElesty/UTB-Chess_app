package com.theelesty.chess_app.data

import com.google.gson.annotations.SerializedName

data class Post (
    var title :String,
    var comments: String, // ???? optional
    var url: String,
    var timeStamp: Int,
    var fen: String,
    var pgn: String,
    @SerializedName("image")
    var imgUrl: String)