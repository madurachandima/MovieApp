package com.madura.movieapp.presentation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object MovieDetailsScreen : Screen("movie_details_screen")
    data object FavoriteScreen : Screen("favorite_screen")
    data object WatchListScreen : Screen("watch_list_screen")
    data object SearchScreen : Screen("search_screen")
}