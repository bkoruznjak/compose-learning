package hr.flowable.composeplayground.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import hr.flowable.composeplayground.R
import hr.flowable.composeplayground.compose.ui.theme.ComposePlaygroundTheme

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

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }

        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.img_alfred_sisley),
            contentDescription = "Alfred Sisley",
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .clip(CircleShape)
        )
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

@ExperimentalUnitApi
@Preview(showBackground = true)
@Composable
fun PhotographerCardPreview() {
    ComposePlaygroundTheme {
        PhotographerCard()
    }
}


