package com.zekierciyas.cache.model.position


import com.google.gson.annotations.SerializedName
import com.zekierciyas.cache.JSONConvertable

data class Position(
    @SerializedName("posX")
    val posX: Double,
    @SerializedName("posY")
    val posY: Double
): JSONConvertable