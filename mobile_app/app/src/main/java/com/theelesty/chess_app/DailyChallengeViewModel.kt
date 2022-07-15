package com.theelesty.chess_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theelesty.chess_app.data.Post
import com.theelesty.chess_app.data.api.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class DailyChallengeViewModel(private val repository: Repository) : ViewModel() {
    var dailyResponse : MutableLiveData<Response<Post>> = MutableLiveData()
    var randomResponse : MutableLiveData<Response<Post>> = MutableLiveData()

    fun getDaily(){
        viewModelScope.launch {
            val response = repository.getDaily()
            dailyResponse.value = response
        }
    }

    fun getRandom(){
        viewModelScope.launch {
            val response = repository.getRandom()
            randomResponse.value = response
        }
    }
}