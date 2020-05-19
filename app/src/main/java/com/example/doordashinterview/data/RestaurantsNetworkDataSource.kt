package com.example.doordashinterview.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsNetworkDataSource {
    private val doordashService : DoordashService

    init {
        val client = Retrofit.Builder()
            .baseUrl("https://api.doordash.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        doordashService = client.create(DoordashService::class.java)
    }

    suspend fun getListRestaurants(limit: Int, offset: Int) : List<DoorDashRestaurantAPIResponse>{
        return withContext(Dispatchers.IO) {
            doordashService.getListRestaurants(limit, offset)
            //handle error conditions
        }
    }

}