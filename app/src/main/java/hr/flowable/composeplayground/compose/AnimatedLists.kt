package hr.flowable.composeplayground.compose

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedLists() {

    AnimatedList()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
private fun AnimatedList() {
    var list by remember {
        mutableStateOf(
            listOf(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16
            )
        )
    }
    val scrollState = rememberLazyListState()


    LazyColumn(state = scrollState) {

        items(list) {
            AnimatableText("item $it", isSender = it % 2 == 0)
        }
    }


}

@Composable
private fun AnimatableTextWithHardcodedBox(text: String) {
    var slideAnimationState =
        MutableTransitionState(false)

    // imaginary box that will trigger that the item is visible since animated visibility does not trigger composition if its set to false
    Box(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            Log.d("žžž", "activating $text")
            slideAnimationState.targetState = true
        }) {
        AnimatedVisibility(
            visibleState = slideAnimationState,
            enter = slideInHorizontally()
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .onGloballyPositioned {
                        Log.d("žžž", "positioned $text")
                    }
                    .padding(24.dp)
                    .background(color = Color.LightGray)
            )
        }
    }
}

@Composable
private fun AnimatableText(text: String, isSender: Boolean = false) {
    val screenWidthInDp = LocalConfiguration.current.screenWidthDp.dp
    val offset: Dp = if (isSender) screenWidthInDp else -screenWidthInDp

    var startAnimation by remember { mutableStateOf(false) }

    val slide = animateDpAsState(
        targetValue = if (startAnimation) 0f.dp else offset,
        animationSpec = spring(
            dampingRatio = DampingRatioLowBouncy,
            stiffness = StiffnessVeryLow,
        ),
        finishedListener = { Log.d("žžž", "im done animating $text") }
    )

    // imaginary box that will trigger that the item is visible since animated visibility does not trigger composition if its set to false
    Box(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            Log.d("žžž", "activating $text")
            startAnimation = true
        }) {


        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .onGloballyPositioned {
                    Log.d("žžž", "positioned $text")
                }
                .padding(24.dp)
                .offset(x = slide.value)
                .background(color = Color.LightGray)
        )

    }
}
