package com.madura.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.madura.movieapp.presentation.InitialScreen
import com.madura.movieapp.presentation.Screen
import com.madura.movieapp.presentation.favorite_movies_screen.FavoriteMovieScreen
import com.madura.movieapp.presentation.homeScreen.HomeScreen
import com.madura.movieapp.presentation.movieDetailsScreen.MovieDetailsScreen
import com.madura.movieapp.presentation.movie_search_screen.MovieSearchScreen
import com.madura.movieapp.presentation.movie_watch_list_screen.MovieWatchListScreen
import com.madura.movieapp.presentation.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen
                            .InitialScreen.route,
                    ) {

                        composable(route = Screen.InitialScreen.route) {
                            InitialScreen(navController = navController)
                        }

                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.MovieDetailsScreen.route + "/{movie_id}") {
                            MovieDetailsScreen(navController = navController)
                        }
                        composable(route = Screen.SearchScreen.route) {
                            MovieSearchScreen(navController = navController)
                        }
                        composable(route = Screen.FavoriteScreen.route) {
                            FavoriteMovieScreen(navController = navController)
                        }
                        composable(route = Screen.WatchListScreen.route) {
                            MovieWatchListScreen(navController = navController)
                        }
                    }


                }
            }
        }
    }
}


