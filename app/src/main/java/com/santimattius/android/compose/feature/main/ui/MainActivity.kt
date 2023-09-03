package com.santimattius.android.compose.feature.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.android.compose.R
import com.santimattius.android.compose.core.ui.component.AppBar
import com.santimattius.android.compose.core.ui.component.column.LazyDraggableColumn
import com.santimattius.android.compose.core.ui.component.grid.LazyDraggableVerticalGrid
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
    var isGridMode by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            AppBar(actions = {
                IconButton(onClick = { isGridMode = !isGridMode }) {
                    if (isGridMode) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "List view"
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_grid_view),
                            contentDescription = "Grid view"
                        )
                    }
                }
            })
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            MainContent(
                state = state,
                isGridMode = isGridMode,
                onMove = viewModel::move
            )
        }
    }
}

@Composable
fun MainContent(
    state: MainUiState,
    isGridMode: Boolean,
    onMove: (Int, Int) -> Unit,
) {
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.isFailure -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text("Error")
            }
        }

        else -> {
            val movies = state.data.toMutableStateList()
            MoviesContent(
                data = movies,
                gridMode = isGridMode,
                onMove = onMove
            )
        }
    }
}

@Composable
fun MoviesContent(
    data: List<MovieUiModel>,
    gridMode: Boolean = true,
    onMove: (Int, Int) -> Unit,
) {

    if (gridMode) {
        LazyDraggableVerticalGrid(items = data, onMove = onMove) { item, isDragging ->
            val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "elevation")
            CardGridItem(item = item, elevation = elevation)
        }

    } else {
        LazyDraggableColumn(items = data, onMove = onMove) { item, isDragging ->
            val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "elevation")
            CardListItem(item = item, elevation = elevation)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DragAndDropTheme {
        MainScreen()
    }
}