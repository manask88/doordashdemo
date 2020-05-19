package com.example.doordashinterview.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DoordashService {
    @GET("restaurant/?lat=37.422740&lng=-122.139956")
    suspend fun getListRestaurants(@Query("limit") limit: Int , @Query("offset") offset: Int): List<DoorDashRestaurantAPIResponse>
}