package com.madura.movieapp.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.madura.movieapp.presentation.favorite_movies_screen.FavoriteMovieScreen
import com.madura.movieapp.presentation.homeScreen.BottomNavigationItem
import com.madura.movieapp.presentation.homeScreen.HomeScreen
import com.madura.movieapp.presentation.movie_search_screen.MovieSearchScreen
import com.madura.movieapp.presentation.movie_watch_list_screen.MovieWatchListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InitialScreen(
    navController: NavController,
) {
    val pages = listOf(
        HomeScreen(navController = navController),
        MovieSearchScreen(navController = navController),
        FavoriteMovieScreen(navController = navController),
        MovieWatchListScreen(navController = navController)
    )

    val pagerState = rememberPagerState(pageCount = {
        pages.size
    })

    val items = listOf(
        BottomNavigationItem(
            title = "For you",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = Screen.HomeScreen.route
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            route = Screen.SearchScreen.route
        ),
        BottomNavigationItem(
            title = "Favorite",
            selectedIcon = Icons.Filled.Favorite,
            unSelectedIcon = Icons.Outlined.Favorite,
            route = Screen.FavoriteScreen.route
        ),
        BottomNavigationItem(
            title = "Watch List",
            selectedIcon = Icons.Filled.List,
            unSelectedIcon = Icons.Outlined.List,
            route = Screen.WatchListScreen.route
        )
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            coroutineScope.launch {
                                selectedItemIndex = index
                                pagerState.animateScrollToPage(page =  index, pageOffsetFraction = 0f)
                            }

                        },
                        label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unSelectedIcon,
                                contentDescription = item.title
                            )
                        })
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            HorizontalPager(
                userScrollEnabled = false,
                pageSize = PageSize.Fill,
                state = pagerState, modifier = Modifier.fillMaxSize()
            ) { page ->
                if (page == 0) {
                    HomeScreen(navController = navController)
                }
                if (page == 1) {
                    MovieSearchScreen(navController = navController)
                }
                if (page == 2) {
                    FavoriteMovieScreen(navController = navController)
                }
                if (page == 3) {
                    MovieWatchListScreen(navController = navController)
                }
            }
        }

    }
}