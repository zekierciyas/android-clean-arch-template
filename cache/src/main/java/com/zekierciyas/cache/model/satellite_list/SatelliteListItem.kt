package com.zekierciyas.cache.model.satellite_list


import com.google.gson.annotations.SerializedName
import com.zekierciyas.cache.JSONConvertable

data class SatelliteListItem(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
): JSONConvertable