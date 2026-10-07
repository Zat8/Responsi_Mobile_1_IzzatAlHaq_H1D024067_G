package com.pemmob.otakudesu.data.remote

import com.pemmob.otakudesu.data.model.AnimeDetailResponse
import com.pemmob.otakudesu.data.model.AnimeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): AnimeListResponse

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String? = null,
        @Query("genres") genreId: Int? = null,
        @Query("order_by") orderBy: String? = null,
        @Query("sort") sort: String? = null
    ): AnimeListResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): AnimeDetailResponse
}