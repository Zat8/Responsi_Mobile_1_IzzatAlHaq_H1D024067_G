package com.pemmob.otakudesu

import android.app.Application
import com.pemmob.otakudesu.data.repository.AnimeRepository

class AnimeApplication : Application() {
    val repository by lazy { AnimeRepository() }
}