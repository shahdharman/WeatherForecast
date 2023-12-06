package com.example.weatherforecast.screens.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.Favourite
import com.example.weatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository)
    : ViewModel(){
    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
                repository.getFavourites().distinctUntilChanged()
                    .collect{
                        listOfFavs ->
                        if(listOfFavs.isNullOrEmpty()){
                            Log.d("TAG", ": Empty favs ")
                        }
                        else{
                            _favList.value = listOfFavs
                            Log.d("FAVS", ":${favList.value} ")
                        }
                    }
        }
    }
    fun insertFavorite(favorite: Favourite) = viewModelScope.launch { repository.insertFavourite(favorite) }
    fun updateFavorite(favorite: Favourite) = viewModelScope.launch { repository.updateFavourite(favorite) }
    fun deleteFavorite(favorite: Favourite) = viewModelScope.launch { repository.deleteFavourite(favorite) }
}

private fun <T> Flow<T>.collect(collector: FlowCollector<T>) {

}
