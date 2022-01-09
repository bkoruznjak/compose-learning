package hr.flowable.composeplayground.compose

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun NestedScrolling() {
    BookingDetails(1)
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
private fun BookingDetails(chatMessageId: Long = 0) {
    val cardHeight = 250.dp

    val cardHeightInPx = with(LocalDensity.current) {
        cardHeight.roundToPx().toFloat()
    }

    val cardHeightOffsetInPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                Log.d("žžž", "delta $delta")
                val newOffset = cardHeightOffsetInPx.value - delta
                Log.d("žžž", "new offset $newOffset")
                cardHeightOffsetInPx.value = newOffset.coerceIn(-cardHeightInPx, 0f)
                Log.d("žžž", "cardHeightOffsetInPx ${cardHeightOffsetInPx.value}")
                return Offset.Zero
            }
        }
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState,
            reverseLayout = true
        ) {

            items(100) {

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.Cyan)
                ) {
                    Text("this is an item no: $it")
                }

                Spacer(modifier = Modifier.height(8.dp))

            }
        }

        // negative offset you go up, positive offset you go down
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .offset { IntOffset(x = 0, y = cardHeightOffsetInPx.value.roundToInt()) }
                .background(Color.Red)
        )
    }
}
