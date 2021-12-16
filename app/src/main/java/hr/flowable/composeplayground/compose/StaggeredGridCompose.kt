package hr.flowable.composeplayground.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import hr.flowable.composeplayground.compose.ui.theme.ComposePlaygroundTheme
import kotlin.random.Random

@Composable
fun StaggeredCard(name: String, id: Int, numberOfShares: Int) {

    val loremPicsum = "https://picsum.photos/200/300?id=$id"

    Surface(
        color = Color.White,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxHeight()
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = rememberImagePainter(data = loremPicsum, builder = {
                        crossfade(true)
                    }),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(name, style = MaterialTheme.typography.h5)

                Spacer(Modifier.height(8.dp))

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text("$numberOfShares")
                }
            }
        }
    }
}


@Composable
fun HorizontalColumn() {
    val scrollState = rememberLazyListState()

    LazyRow(state = scrollState) {
        items(50) { index ->
            StaggeredCard("Lala", index, Random.nextInt(200))
        }
    }
}

@Composable
fun StaggeredRow(
    modifier: Modifier = Modifier,
    numberOfRows: Int = 3,
    content: @Composable () -> Unit
) {

    val itemSeparationSize = 8.dp.value.toInt()

    /**
     *  0 % 2 = 0  3 % 3 = 0  6 % 3 = 0
     *  1 % 2 = 1  4 % 3 = 1    ...
     *  2 % 3 = 2  5 % 3 = 2    ...
     */
    Layout(modifier = modifier, content = content) { measureables, constraints ->
        // set the initial value to be the margin
        val rowWidth = MutableList(numberOfRows) { itemSeparationSize }
        val columnHeight =
            MutableList((measureables.size / numberOfRows) + 1) { itemSeparationSize }

        val maxRowWidth = MutableList(numberOfRows) { itemSeparationSize }
        val maxColumnHeight =
            MutableList((measureables.size / numberOfRows) + 1) { itemSeparationSize }

        // THIS IS SUPER IN-EFFICIENT BUT ITS LATE NOW AND MY BRAIN CANNOT THINK ANYMORE
        val placeables = measureables.mapIndexed { index, measurable ->

            val placeable = measurable.measure(constraints)

            val rowIndex = index % numberOfRows
            val columnIndex = (index / numberOfRows)

            // update x and y
            maxRowWidth[rowIndex] += (placeable.width + itemSeparationSize)
            maxColumnHeight[columnIndex] += (placeable.height + itemSeparationSize)

            placeable
        }

        layout(maxRowWidth.maxOf { it }, maxColumnHeight.maxOf { it }) {

            placeables.forEachIndexed { index, placeable ->

                val rowIndex = index % numberOfRows
                val columnIndex = (index / numberOfRows)
                // fetch latest x and y
                val startX = rowWidth[rowIndex]
                val startY = columnHeight[columnIndex]

                // place item
                placeable.placeRelative(startX, startY)
                // update x and y
                rowWidth[rowIndex] += (placeable.width + itemSeparationSize)
                columnHeight[columnIndex] += (placeable.height + itemSeparationSize)
            }

        }

    }
}

@Preview
@Composable
fun StaggeredGridComposable() {

    val topics = listOf(
        "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
        "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
        "Religion", "Social sciences", "Technology", "TV", "Writing"
    )

    val scrollState = rememberScrollState()

    ComposePlaygroundTheme {
        Row(modifier = Modifier.horizontalScroll(scrollState)) {
            StaggeredRow {
                repeat(topics.size) { topicIndex ->
                    StaggeredCard(topics[topicIndex], topicIndex, Random.nextInt(2000))
                }
            }
        }

    }
}
