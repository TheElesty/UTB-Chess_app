package com.theelesty.chess_app.data.api

import com.theelesty.chess_app.data.Post
import retrofit2.Response
import retrofit2.http.GET

interface ChessApi {
    @GET ("pub/puzzle")
    suspend fun getDaily() : Response<Post>

    @GET ("pub/puzzle/random")
    suspend fun getRandom() : Response<Post>
}