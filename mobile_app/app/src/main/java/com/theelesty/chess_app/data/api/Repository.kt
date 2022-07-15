package com.theelesty.chess_app.data.api

import com.theelesty.chess_app.data.Post
import retrofit2.Response

class Repository {
    suspend fun getDaily(): Response<Post> {
        return RetrofitInstance.api.getDaily()
    }

    suspend fun getRandom(): Response<Post> {
        return RetrofitInstance.api.getRandom()
    }
}