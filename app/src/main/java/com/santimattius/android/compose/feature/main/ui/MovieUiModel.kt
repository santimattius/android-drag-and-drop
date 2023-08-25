package com.santimattius.android.compose.feature.main.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUiModel(
    val id: Long,
    val title: String,
    val image: String,
) : Parcelable
