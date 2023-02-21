package com.zekierciyas.detail_screen.binding

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zekierciyas.cache.model.position.Position
import com.zekierciyas.cache.model.position.Positions
import com.zekierciyas.detail_screen.DetailScreenUiState
import com.zekierciyas.utility.extentions.hide
import com.zekierciyas.utility.extentions.show

object BindingListScreenAdapter {

    @BindingAdapter("bindSatelliteName")
    @JvmStatic
    fun bindSatelliteName(textView: TextView, uiState: DetailScreenUiState) {
        when(uiState) {
            is DetailScreenUiState.Loaded -> {
                textView.text = uiState.name
            }
            else -> {

            }
        }
    }

    @BindingAdapter("bindFirstFlightData")
    @JvmStatic
    fun bindFirstFlightData(textView: TextView, uiState: DetailScreenUiState) {
        when(uiState) {
            is DetailScreenUiState.Loaded -> {
                textView.text = uiState.detail.firstFlight
            }
            else -> {

            }
        }
    }

    @BindingAdapter("bindMassAmount")
    @JvmStatic
    fun bindMass(textView: TextView, uiState: DetailScreenUiState) {
        when(uiState) {
            is DetailScreenUiState.Loaded -> {
                textView.text = uiState.detail.mass.toString()
            }
            else -> {

            }
        }
    }

    @BindingAdapter("bindCost")
    @JvmStatic
    fun bindCost(textView: TextView, uiState: DetailScreenUiState) {
        when(uiState) {
            is DetailScreenUiState.Loaded -> {
                textView.text = uiState.detail.costPerLaunch.toString()
            }
            else -> {

            }
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("bindLastPosition")
    @JvmStatic
    fun bindLastPosition(textView: TextView, position: Position?) {
        if (position == null) return
        textView.text = "(${position.posX} , ${position.posY})"
    }

}