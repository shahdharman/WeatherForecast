package com.example.weatherforecast.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforecast.screens.MainScreen
import com.example.weatherforecast.screens.WeatherSplashScreen
import com.example.weatherforecast.screens.about.AboutScreen
import com.example.weatherforecast.screens.favourites.FavouritesScreen
import com.example.weatherforecast.screens.main.MainViewModel
import com.example.weatherforecast.screens.settings.SettingsScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
//handles navigation related to our whole application.
fun WeatherNavigation(){
    val navController = rememberNavController()
    //NAV HOST hosts our screens
    //nav controller handles the navigation from one screen to another.
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
            composable(WeatherScreens.SplashScreen.name){
                WeatherSplashScreen(navController = navController)
            }
            val route = WeatherScreens.MainScreen.name
            composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city"){
                    type = NavType.StringType
                })){
                navBack ->
                    navBack.arguments?.getString( "city").let{
                        //city is the value passed like lisbon,moscow,etc.
                            city ->
                        val mainViewModel = hiltViewModel<MainViewModel>()
                        MainScreen(navController = navController,mainViewModel,city = city)
                    }

            }
            composable(WeatherScreens.SearchScreen.name){
                SearchScreen(navController = navController)
            }
            composable(WeatherScreens.AboutScreen.name){
                AboutScreen(navController = navController)
            }
            composable(WeatherScreens.FavouritesScreen.name){
                FavouritesScreen(navController = navController)
            }
            composable(WeatherScreens.SettingsScreen.name){
                SettingsScreen(navController = navController)
            }
    }
}