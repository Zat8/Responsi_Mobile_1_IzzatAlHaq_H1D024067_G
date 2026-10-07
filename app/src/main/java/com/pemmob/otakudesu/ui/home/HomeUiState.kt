package com.pemmob.otakudesu.ui.home

import com.pemmob.otakudesu.data.model.Anime

sealed interface HomeUiState {
    data object Idle : HomeUiState
    data object Loading : HomeUiState
    data class Success(val animeList: List<Anime>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}