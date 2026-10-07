package com.pemmob.otakudesu.ui.detail

import com.pemmob.otakudesu.data.model.Anime

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val anime: Anime) : DetailUiState
    data class Error(val message: String) : DetailUiState
}