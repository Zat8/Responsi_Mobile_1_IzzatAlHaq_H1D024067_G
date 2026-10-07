package com.pemmob.otakudesu.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.otakudesu.data.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: AnimeRepository,
    private val animeId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val anime = repository.getAnimeDetail(animeId)
                _uiState.value = DetailUiState.Success(anime)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(
                    when {
                        e is java.net.UnknownHostException || e is java.net.ConnectException ->
                            "Gagal terhubung ke server Jikan API. Periksa koneksi internet Anda."
                        e is java.net.SocketTimeoutException ->
                            "Waktu koneksi habis. Silakan coba lagi."
                        else ->
                            e.localizedMessage ?: "Gagal memuat detail anime."
                    }
                )
            }
        }
    }
}