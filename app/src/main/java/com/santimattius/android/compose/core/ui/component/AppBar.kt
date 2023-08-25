package com.santimattius.android.compose.core.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.santimattius.android.compose.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String = stringResource(id = R.string.app_name),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    titleContentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = titleContentColor,
            actionIconContentColor = titleContentColor,
        ),
    )
}