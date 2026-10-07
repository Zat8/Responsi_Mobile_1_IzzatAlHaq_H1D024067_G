package com.pemmob.otakudesu

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import com.pemmob.otakudesu.data.remote.JikanApi

class ExampleUnitTest {
    @Test
    fun testTenraiApi() = runBlocking {
        val animeDetail = JikanApi.service.getAnimeDetail(1)
        assertEquals(1, animeDetail.data.malId)
        assertEquals("Cowboy Bebop", animeDetail.data.title)
    }
}