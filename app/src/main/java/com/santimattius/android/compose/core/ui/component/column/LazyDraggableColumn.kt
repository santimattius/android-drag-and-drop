package com.santimattius.android.compose.core.ui.component.column

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> LazyDraggableColumn(
    items: List<T>,
    onMove: (Int, Int) -> Unit,
    content: @Composable (T, Boolean) -> Unit,
) {

    val listState = rememberLazyListState()
    val dragDropState = rememberDragDropState(listState, onMove)

    LazyColumn(
        modifier = Modifier.dragContainer(dragDropState),
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(items, key = { _, item -> item }) { index, item ->
            DraggableItem(dragDropState, index) { isDragging ->
                content(item, isDragging)
            }
        }
    }
}

private fun Modifier.dragContainer(lazyDraggableListState: LazyDraggableListState): Modifier {
    return pointerInput(lazyDraggableListState) {
        detectDragGesturesAfterLongPress(
            onDrag = { change, offset ->
                change.consume()
                lazyDraggableListState.onDrag(offset = offset)
            },
            onDragStart = { offset -> lazyDraggableListState.onDragStart(offset) },
            onDragEnd = { lazyDraggableListState.onDragInterrupted() },
            onDragCancel = { lazyDraggableListState.onDragInterrupted() }
        )
    }
}

@ExperimentalFoundationApi
@Composable
private fun LazyItemScope.DraggableItem(
    lazyDraggableListState: LazyDraggableListState,
    index: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(isDragging: Boolean) -> Unit,
) {
    val dragging = index == lazyDraggableListState.draggingItemIndex
    val draggingModifier = if (dragging) {
        Modifier
            .zIndex(1f)
            .graphicsLayer {
                translationY = lazyDraggableListState.draggingItemOffset
            }
    } else if (index == lazyDraggableListState.previousIndexOfDraggedItem) {
        Modifier
            .zIndex(1f)
            .graphicsLayer {
                translationY = lazyDraggableListState.previousItemOffset.value
            }
    } else {
        Modifier.animateItemPlacement()
    }
    Column(modifier = modifier.then(draggingModifier)) {
        content(dragging)
    }
}
