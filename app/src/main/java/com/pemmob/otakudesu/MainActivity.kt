package com.pemmob.otakudesu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pemmob.otakudesu.ui.detail.DetailScreen
import com.pemmob.otakudesu.ui.home.HomeScreen
import com.pemmob.otakudesu.ui.theme.AnimeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeAppTheme {
                AnimeNavGraph()
            }
        }
    }
}

@Composable
fun AnimeNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onAnimeClick = { id -> navController.navigate("detail/$id") })
        }
        composable(
            route = "detail/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            DetailScreen(animeId = animeId, onBackClick = { navController.popBackStack() })
        }
    }
}