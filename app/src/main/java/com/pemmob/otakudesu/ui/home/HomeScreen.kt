package com.pemmob.otakudesu.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pemmob.otakudesu.ui.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAnimeClick: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory)
) {
    val query by viewModel.query.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("AniSearch") }) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = query,
                onValueChange = viewModel::onQueryChange,
                placeholder = { Text("Cari judul anime...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.searchAnime() }),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.genreOptions) { genre ->
                    FilterChip(
                        selected = selectedGenre == genre,
                        onClick = { viewModel.onGenreSelected(genre) },
                        label = { Text(genre.name) }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            when (val state = uiState) {
                is HomeUiState.Idle -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is HomeUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is HomeUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = state.message,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = {
                                if (query.isBlank() && selectedGenre == null) {
                                    viewModel.loadTopAnime()
                                } else {
                                    viewModel.searchAnime()
                                }
                            }) {
                                Text("Coba Lagi")
                            }
                        }
                    }
                }
                is HomeUiState.Success -> {
                    if (state.animeList.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Tidak ditemukan. Coba kata kunci lain.")
                        }
                    } else {
                        LazyColumn {
                            item {
                                val listTitle = when {
                                    query.isNotBlank() -> "Hasil Pencarian: \"$query\""
                                    selectedGenre != null -> "Genre: ${selectedGenre?.name}"
                                    else -> "Anime Rating Tertinggi 🔥"
                                }
                                Text(
                                    text = listTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                            items(state.animeList, key = { it.malId }) { anime ->
                                AnimeItemCard(anime = anime, onClick = { onAnimeClick(anime.malId) })
                            }
                        }
                    }
                }
            }
        }
    }
}