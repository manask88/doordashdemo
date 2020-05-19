package com.example.doordashinterview.data

import com.example.doordashinterview.view.RestaurantUIModel

data class DoorDashRestaurantAPIResponse(
    val id : Int?,
    val status: String?,
    val business: Business?,
    val description: String?,
    val cover_img_url: String?
)

class Business (
    val id: Int?,
    val name: String?
)

fun DoorDashRestaurantAPIResponse.toRestaurantUIModel() : RestaurantUIModel {
    return RestaurantUIModel(
        title = business?.name ?: "",
        subtitle = description ?: "",
        status = status ?: "",
        imageUrl = cover_img_url ?: ""
    )
}
