package com.zekierciyas.cache.model.detail


import com.google.gson.annotations.SerializedName

data class SatelliteDetailItem(
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int,
    @SerializedName("first_flight")
    val firstFlight: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mass")
    val mass: Int
)