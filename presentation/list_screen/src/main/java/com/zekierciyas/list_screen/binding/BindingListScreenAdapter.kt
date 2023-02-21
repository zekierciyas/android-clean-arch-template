package com.zekierciyas.list_screen.binding

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zekierciyas.list_screen.view_model.ListScreenUiState
import com.zekierciyas.list_screen.R
import com.zekierciyas.list_screen.adapter.ListScreenAdapter
import com.zekierciyas.utility.extentions.hide
import com.zekierciyas.utility.extentions.show

object BindingListScreenAdapter {
    @BindingAdapter("activateCondition")
    @JvmStatic
    fun bindActivationText(textView: TextView, activateCondition: Boolean?) {
        if (activateCondition == null) return
        if (activateCondition) {
            textView.text = "Activate"
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.black))
        } else {
            textView.text = "Passive"
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.gray))
        }
    }

    @JvmStatic
    @BindingAdapter("bindActivationIcon")
    fun ImageView.bindActivationButton(active: Boolean) {
        if (active) this.setImageDrawable(
            ContextCompat.getDrawable(
                this.context,
                R.drawable.ic_circle_green
            )
        )
        else this.setImageDrawable(
            ContextCompat.getDrawable(
                this.context,
                R.drawable.ic_circle_red
            )
        )
    }

    @JvmStatic
    @BindingAdapter("bindLoaderStatus")
    fun ProgressBar.bindLoaderStatus(uiState: ListScreenUiState) {
        when (uiState) {
            is ListScreenUiState.Error -> {
                this.hide()
            }

            is ListScreenUiState.Loaded -> {
                this.hide()
            }

            is ListScreenUiState.Loading -> {
                this.show()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindData")
    fun <T> setRecyclerViewProperties(
        recyclerView: RecyclerView,
        uiState: ListScreenUiState
    ) {
        if (recyclerView.adapter is ListScreenAdapter) {
            when (uiState) {
                is ListScreenUiState.Loaded -> {
                    (recyclerView.adapter as ListScreenAdapter).initList(uiState.list)
                }
                else -> {

                }
            }
        }
    }
}