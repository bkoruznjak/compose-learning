package hr.flowable.composeplayground.compose

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import hr.flowable.composeplayground.compose.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.launch

/**
 * Codelab link:
 *
 * https://developer.android.com/codelabs/jetpack-compose-layouts?skip_cache=true#0
 *
 * Main parts: Column, Row, Box (frame)
 */
@ExperimentalUnitApi
@Composable
fun PhotographerCard() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable { Log.d("žžž", "clicked") }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }

//        Image(
//            bitmap = ImageBitmap.imageResource(id = R.drawable.img_alfred_sisley),
//            contentDescription = "Alfred Sisley",
//            modifier = Modifier
//                .width(48.dp)
//                .height(48.dp)
//                .clip(CircleShape)
//        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Alfred Sisley", style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago")
            }
        }
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Hi there")
        Text("How are you")
    }
}

@Preview
@Composable
fun LayoutCodeLab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Layouts codelab") },
                actions = {
                    IconButton(onClick = { Log.d("žžž", "Clicked action") }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

@ExperimentalUnitApi
@Composable
fun ListThatScrolls() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        repeat(100) {
            CellWithExpandingButton("$it")
        }
    }
}

/**
 * Main difference between column is that the scroll state is passed to the composable
 * constructor instead of the modifier
 *
 * also does not support "repeat" but instead supports "items"
 */
@Composable
fun LazyListThatScrolls() {
    val scrollState = rememberLazyListState()

    val listSize = 100

    val listScope = rememberCoroutineScope()

    val loremPicsum = "https://picsum.photos/200/300?id="

    Column {
        Row(Modifier.padding(8.dp)) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    listScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }) {
                Text("Scroll to top")
            }

            Spacer(Modifier.width(8.dp))

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    listScope.launch {
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }) {
                Text("Scroll to bottom")
            }
        }

        LazyColumn(state = scrollState, modifier = Modifier.fillMaxWidth()) {
            items(listSize) { index ->
                val sizeOfImage = 128.dp
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(sizeOfImage)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = "$loremPicsum$index",
                                builder = {
                                    crossfade(500)
                                    transformations(CircleCropTransformation())
                                }),
                            contentDescription = null,
                            modifier = Modifier.size(sizeOfImage)
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Text("Item no: #$index", style = MaterialTheme.typography.h4)
                }
            }
        }
    }
}

/**
 * Custom modifier that gets created only after constraints are known because composables can be
 * measured only once due to performance
 */

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]
        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

@ExperimentalUnitApi
@Preview(showBackground = false)
@Composable
fun PhotographerCardPreview() {
    ComposePlaygroundTheme {
        PhotographerCard()
    }
}


