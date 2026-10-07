package com.pemmob.otakudesu

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import com.pemmob.otakudesu.data.remote.JikanApi

class ExampleUnitTest {
    @Test
    fun testGetTopAnime() = runBlocking {
        val topAnime = JikanApi.service.getTopAnime()
        assertTrue(topAnime.data.isNotEmpty())
        println("Top Anime #1: ${topAnime.data.first().title} (Score: ${topAnime.data.first().score})")
    }
}