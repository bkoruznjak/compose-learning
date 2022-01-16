package hr.flowable.composeplayground.compose


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalTransitionApi::class)
@Composable
fun AnimatedVisibilityLazyColumnDemo() {
    Column {
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val model = remember { MyModel() }
        Row(Modifier.fillMaxWidth()) {
            Button(
                {
                    model.addNewItem()
                    coroutineScope.launch { scrollState.scrollToItem(0) }

                },
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f)
            ) {
                Text("Add")
            }
        }

        LaunchedEffect(model) {
            snapshotFlow {
                model.items.firstOrNull { it.visible.isIdle && !it.visible.targetState }
            }.collect {
                if (it != null) {
                    model.pruneItems()
                }
            }
        }



        LazyColumn(state = scrollState, reverseLayout = true) {
            items(model.items, key = { it.itemId }) { item ->
                AnimatedVisibility(
                    item.visible,
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .requiredHeight(90.dp)
                            .background(item.color)
                    ) {
                        Button(
                            { model.removeItem(item) },
                            modifier = Modifier
                                .align(CenterEnd)
                                .padding(15.dp)
                        ) {
                            Text("Remove")
                        }
                    }
                }
            }
        }

        Button(
            { model.removeAll() },
            modifier = Modifier
                .align(End)
                .padding(15.dp)
        ) {
            Text("Clear All")
        }
    }
}

private class MyModel {
    private val _items: MutableList<ColoredItem> = mutableStateListOf(
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            1
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            2
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            3
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            4
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            5
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            6
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            7
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            8
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            9
        ),
        ColoredItem(
            MutableTransitionState(false).apply { targetState = true },
            10
        ),

        )
    private var lastItemId = 10
    val items: List<ColoredItem> = _items

    class ColoredItem(val visible: MutableTransitionState<Boolean>, val itemId: Int) {
        val color: Color
            get() = turquoiseColors.let {
                it[itemId % it.size]
            }
    }


    fun addNewItem() {
        lastItemId++
        _items.add(
            0,
            ColoredItem(
                MutableTransitionState(false).apply { targetState = true },
                lastItemId
            )
        )
    }

    fun removeItem(item: ColoredItem) {
        item.visible.targetState = false
    }

    @OptIn(ExperimentalTransitionApi::class)
    fun pruneItems() {
        _items.removeAll(items.filter { it.visible.isIdle && !it.visible.targetState })
    }

    fun removeAll() {
        _items.forEach {
            it.visible.targetState = false
        }
    }
}

internal val turquoiseColors = listOf(
    Color(0xff07688C),
    Color(0xff1986AF),
    Color(0xff50B6CD),
    Color(0xffBCF8FF),
    Color(0xff8AEAE9),
    Color(0xff46CECA)
)
