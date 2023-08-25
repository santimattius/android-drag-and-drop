package com.santimattius.android.compose.feature.main.ui

data class MainUiState(
    val isLoading: Boolean = false,
    val isFailure: Boolean = false,
    val data: List<MovieUiModel> = emptyList(),
)