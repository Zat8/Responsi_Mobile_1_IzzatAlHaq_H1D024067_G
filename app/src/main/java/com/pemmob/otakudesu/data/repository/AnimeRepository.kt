package com.pemmob.otakudesu.data.repository

import com.pemmob.otakudesu.data.model.Anime
import com.pemmob.otakudesu.data.remote.JikanApi

class AnimeRepository {
    private val api = JikanApi.service

    suspend fun getTopAnime(): List<Anime> {
        return api.getTopAnime().data
    }

    suspend fun searchAnime(query: String?, genreId: Int?): List<Anime> {
        return if (query.isNullOrBlank() && genreId == null) {
            api.getTopAnime().data
        } else {
            api.searchAnime(query = query?.ifBlank { null }, genreId = genreId).data
        }
    }

    suspend fun getAnimeDetail(id: Int): Anime {
        return api.getAnimeDetail(id).data
    }
}