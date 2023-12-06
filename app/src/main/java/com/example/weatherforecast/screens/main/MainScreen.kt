package com.example.weatherforecast.screens

import HumidityWindPressureRow
import SunriseSunsetRow
import WeatherDetailRow
import WeatherStateImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.weatherforecast.R
import com.example.weatherforecast.WeatherApp
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.screens.main.MainViewModel
import com.example.weatherforecast.screens.settings.SettingsViewModel
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDateTime
import com.example.weatherforecast.utils.formatDecimals
import com.example.weatherforecast.widgets.WeatherAppBar
import kotlin.jvm.Throws
import kotlin.time.days

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?) {
   // Text(text = "Main Screen!")
    val curCity: String = if (city!!.isBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
    val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
        initialValue = DataOrException(loading = true)){
        value = mainViewModel.getWeatherData(city = curCity,units = unit)
    }.value
    if(weatherData.loading == true){
        CircularProgressIndicator()
    }
    else if(weatherData.data != null){
       MainScaffold(weather = weatherData.data!!, navController = navController, isImperial = isImperial)
    }
    }
}
@Composable
fun MainScaffold(weather:Weather,navController: NavController,isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city.name + ", ${weather.city.country}",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            onAddActionClicked = {
                                 navController.navigate(WeatherScreens.SearchScreen.name)
            },
        elevation = 5.dp)
    }) {
            MainContent(data = weather, isImperial = isImperial)
    }
}

@Composable
fun MainContent(data: Weather,isImperial: Boolean) {

    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        //for date -> Mon,Nov 29
        Text(text = formatDate(data.list[0].dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp))
        //for circle
        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
        color = Color(0xFFFFC400)) {
            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

                WeatherStateImage(imageUrl)
                //weather degree
                Text(
                    text = formatDecimals(data.list[0].temp.day) + "◦", style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                //weather description ex - rain,sunny,etc.
                Text(text = data.list[0].weather[0].main, fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold)
            }
        }
        HumidityWindPressureRow(data.list[0], isImperial = isImperial)
        Divider()
        SunriseSunsetRow(data.list[0])
        Text(text = "This Week", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Surface(modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp),
        ){

            LazyColumn(modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(1.dp)){
                items(items = data.list){
                    item: WeatherItem ->
                        WeatherDetailRow(item)
                }
            }
        }
    }
}

