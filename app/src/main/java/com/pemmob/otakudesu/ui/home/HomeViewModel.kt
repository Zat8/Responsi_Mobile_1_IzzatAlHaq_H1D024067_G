package com.pemmob.otakudesu.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.otakudesu.data.model.Genre
import com.pemmob.otakudesu.data.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _selectedGenre = MutableStateFlow<Genre?>(null)
    val selectedGenre: StateFlow<Genre?> = _selectedGenre.asStateFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val genreOptions = listOf(
        Genre(1, "Action"),
        Genre(4, "Comedy"),
        Genre(8, "Drama"),
        Genre(10, "Fantasy"),
        Genre(22, "Romance"),
        Genre(24, "Sci-Fi")
    )

    init {
        loadTopAnime()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isBlank() && _selectedGenre.value == null) {
            loadTopAnime()
        }
    }

    fun onGenreSelected(genre: Genre) {
        _selectedGenre.value = if (_selectedGenre.value == genre) null else genre
        searchAnime()
    }

    fun loadTopAnime() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val result = repository.getTopAnime()
                _uiState.value = HomeUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    when {
                        e is java.net.UnknownHostException || e is java.net.ConnectException ->
                            "Gagal terhubung ke server API. Periksa koneksi internet Anda."
                        e is java.net.SocketTimeoutException ->
                            "Waktu koneksi habis. Silakan coba lagi."
                        else ->
                            e.localizedMessage ?: "Gagal mengambil data, coba lagi."
                    }
                )
            }
        }
    }

    fun searchAnime() {
        val currentQuery = _query.value.trim()
        val genreId = _selectedGenre.value?.malId

        if (currentQuery.isBlank() && genreId == null) {
            loadTopAnime()
            return
        }

        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val result = repository.searchAnime(currentQuery, genreId)
                _uiState.value = HomeUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    when {
                        e is java.net.UnknownHostException || e is java.net.ConnectException ->
                            "Gagal terhubung ke server API. Periksa koneksi internet Anda."
                        e is java.net.SocketTimeoutException ->
                            "Waktu koneksi habis. Silakan coba lagi."
                        else ->
                            e.localizedMessage ?: "Gagal mengambil data, coba lagi."
                    }
                )
            }
        }
    }
}