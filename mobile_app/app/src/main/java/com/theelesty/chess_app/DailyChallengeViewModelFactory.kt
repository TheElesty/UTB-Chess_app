package com.theelesty.chess_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.theelesty.chess_app.data.api.Repository

class DailyChallengeViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyChallengeViewModel(repository) as T
    }

}