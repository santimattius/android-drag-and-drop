package com.santimattius.android.compose.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage


private const val IMAGE_ASPECT_RATIO = 0.67f

@Composable
fun CardGridItem(
    modifier: Modifier = Modifier,
    item: MovieUiModel,
    elevation: Dp,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation)
    ) {
        ItemImage(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .aspectRatio(ratio = IMAGE_ASPECT_RATIO),
            item = item
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListItem(
    modifier: Modifier = Modifier,
    item: MovieUiModel,
    elevation: Dp,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation)
    ) {
        ListItem(
            leadingContent = {
                ItemImage(
                    modifier = Modifier
                        .width(100.dp)
                        .background(Color.LightGray)
                        .aspectRatio(ratio = IMAGE_ASPECT_RATIO),
                    item = item
                )
            },
            headlineText = {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
            },
            supportingText = {
                Text(
                    text = item.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
    }
}

@Composable
private fun ItemImage(
    modifier: Modifier = Modifier,
    item: MovieUiModel,
) {
    SubcomposeAsyncImage(
        model = item.image,
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(32.dp)
                )
            }
        },
        contentDescription = item.title,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}