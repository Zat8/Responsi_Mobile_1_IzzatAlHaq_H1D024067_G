package com.pemmob.otakudesu.data.model

import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("mal_id") val malId: Int,
    val title: String,
    val type: String? = null,
    val episodes: Int? = null,
    val status: String? = null,
    val score: Double? = null,
    val synopsis: String? = null,
    @SerializedName("genres") private val _genres: List<Genre>? = emptyList(),
    val images: AnimeImages? = null
) {
    val genres: List<Genre>
        get() = _genres ?: emptyList()

    val imageUrl: String?
        get() = images?.jpg?.largeImageUrl
            ?: images?.jpg?.imageUrl
            ?: images?.webp?.largeImageUrl
            ?: images?.webp?.imageUrl
}

data class AnimeImages(
    val jpg: ImageUrls? = null,
    val webp: ImageUrls? = null
)

data class ImageUrls(
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("small_image_url") val smallImageUrl: String? = null,
    @SerializedName("large_image_url") val largeImageUrl: String? = null
)

data class Genre(
    @SerializedName("mal_id") val malId: Int,
    val name: String
)

data class AnimeListResponse(val data: List<Anime>)
data class AnimeDetailResponse(val data: Anime)