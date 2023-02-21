package com.zekierciyas.cache.model.satellite_list


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.zekierciyas.cache.JSONConvertable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SatelliteListItem(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
): JSONConvertable, Parcelable