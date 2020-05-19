package com.example.doordashinterview.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doordashinterview.data.RestaurantsNetworkDataSource
import com.example.doordashinterview.data.toRestaurantUIModel
import kotlinx.coroutines.launch

class ListRestaurantsViewModel(
    private val restaurantsNetworkDataSource: RestaurantsNetworkDataSource = RestaurantsNetworkDataSource()
) : ViewModel() {
    private var isLoading = false

    val liveData = MutableLiveData<List<RestaurantUIModel>>()

    fun loadInitial() {
        viewModelScope.launch {
            isLoading = true
            val offset = 0
            val listRestaurants = restaurantsNetworkDataSource.getListRestaurants(10, offset).map {
                it.toRestaurantUIModel()
            }
            liveData.postValue(listRestaurants)
            isLoading = false
        }
    }

    fun loadMore( lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (isLoading) return

        if (lastVisibleItemPosition + 20  >= totalItemCount) {
            isLoading = true
            viewModelScope.launch {
                val listRestaurants = restaurantsNetworkDataSource.getListRestaurants(10, totalItemCount).map {
                    it.toRestaurantUIModel()
                }
                liveData.postValue(listRestaurants)
                isLoading = false
            }
        }
    }
}