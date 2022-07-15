package com.theelesty.chess_app.data.api

import com.theelesty.chess_app.data.api.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : ChessApi by lazy {
        retrofit.create(ChessApi::class.java)
    }
}