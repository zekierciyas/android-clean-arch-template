package com.zekierciyas.cache.model.position


import com.google.gson.annotations.SerializedName
import com.zekierciyas.cache.JSONConvertable

data class Positions(
    @SerializedName("list")
    val list: List<PositionItem>
): JSONConvertable