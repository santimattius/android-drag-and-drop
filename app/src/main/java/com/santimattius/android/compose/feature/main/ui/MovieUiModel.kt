package com.santimattius.android.compose.feature.main.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiModel(
    val id: Long,
    val title: String,
    val image: String,
    val overview: String,
) : Parcelable
