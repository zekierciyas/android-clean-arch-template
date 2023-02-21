package com.zekierciyas.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class SatelliteListArgModel(
    val active: Boolean,
    val id: Int,
    val name: String
): Parcelable