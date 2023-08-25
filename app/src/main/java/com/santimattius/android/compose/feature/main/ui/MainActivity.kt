package com.santimattius.android.compose.feature.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.santimattius.android.compose.core.ui.component.AppBar
import com.santimattius.android.compose.core.ui.component.DraggableGrid
import com.santimattius.android.compose.core.ui.theme.DragAndDropTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DragAndDropTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(topBar = { AppBar() }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            MainContent(
                state = state,
                onMove = viewModel::move
            )
        }
    }
}

@Composable
fun MainContent(state: MainUiState, onMove: (Int, Int) -> Unit) {
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.isFailure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error")
            }
        }

        else -> {
            val movies = state.data.toMutableStateList()
            MoviesContent(
                data = movies,
                onMove = onMove
            )
        }
    }
}

@Composable
fun MoviesContent(data: List<MovieUiModel>, onMove: (Int, Int) -> Unit) {
    DraggableGrid(items = data, onMove = onMove) { item, isDragging ->
        val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "elevation")
        CardGridItem(item = item, elevation = elevation)
    }
}

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
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .aspectRatio(ratio = IMAGE_ASPECT_RATIO),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DragAndDropTheme {
        MainScreen()
    }
}