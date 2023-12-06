package com.example.weatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.model.Favourite
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.screens.favourites.FavoriteViewModel

@Composable
fun WeatherAppBar(
    title : String = "",
    icon : ImageVector?= null,
    isMainScreen : Boolean = true,
    elevation : Dp = 0.dp,
    navController:NavController,
    favouriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked :() -> Unit = {},
    onButtonClicked : () -> Unit = {}
) {
    val showDialog = remember{
        mutableStateOf(false)
    }
    val showIt = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    if(showDialog.value){
        ShowSettingDropDownMenu(showDialog, navController)
    }

    TopAppBar(title = {
                      Text(text = title,
                        color = MaterialTheme.colors.onSecondary,
                        fontWeight = FontWeight.Bold,
                          fontSize = 15.sp
                     )
    },
        actions = {
            if(isMainScreen){
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(imageVector = Icons.Default.Search,
                    contentDescription = "search icon")
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(imageVector = Icons.Default.MoreVert,
                        contentDescription = "more vertical icon")
                }
            }
            else {
                Box(){}
            }
        },
        navigationIcon = {
                if(icon != null && !isMainScreen){
                    Icon(imageVector = icon,
                        contentDescription = "back button",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
                }
            if(isMainScreen){
                val isAlreadyFavList = favouriteViewModel
                    .favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }
                if(isAlreadyFavList.isNullOrEmpty()){
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                favouriteViewModel.insertFavorite(
                                    Favourite(
                                        city = title.split(",")[0],
                                        country = title.split(",")[1]
                                    )).run{
                                        showIt.value = true
                                }
                            }, tint = Color.Red.copy(alpha = 0.6f))
                }
                else {
                    showIt.value = false
                    Box{}
                }
                ShowToast(context = context, showIt)
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation)
}
@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, " Added to Favorites",
            Toast.LENGTH_SHORT).show()
    }
}
@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember{ mutableStateOf(true)}
    val items = listOf<String>("About", "Favourites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)){
                DropdownMenu(expanded = expanded,
                    onDismissRequest = {expanded = false},
                    modifier = Modifier
                        .width(140.dp)
                        .background(Color.White)) {
                    items.forEachIndexed{
                        index,text ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            showDialog.value = false
                        }) {
                            Icon(imageVector = when(text){
                                "About" -> Icons.Default.Info
                                "Favourites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                                }
                                , contentDescription = null
                                ,tint = Color.LightGray)
                            Text(text = text,
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    when(text){
                                        "About" -> WeatherScreens.AboutScreen.name
                                        "Favourites" -> WeatherScreens.FavouritesScreen.name
                                         else -> WeatherScreens.SettingsScreen.name
                                    }
                                )
                            }, fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

    }
}
