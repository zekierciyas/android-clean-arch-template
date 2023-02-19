package com.zekierciyas.cache.model.position


import com.google.gson.annotations.SerializedName
import com.zekierciyas.cache.JSONConvertable

data class PositionItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("positions")
    val positions: List<Position>
):JSONConvertable