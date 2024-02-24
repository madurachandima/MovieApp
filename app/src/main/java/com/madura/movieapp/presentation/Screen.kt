package com.madura.movieapp.presentation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
}