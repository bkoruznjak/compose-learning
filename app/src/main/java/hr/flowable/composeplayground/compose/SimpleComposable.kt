package hr.flowable.composeplayground.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.flowable.composeplayground.R

@Preview
@Composable
fun SimpleComposable() {
    Text("Hello world Bornaa")
}

@Preview
@Composable
fun CellItem() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.img_android_boy),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.size(50.dp))

        Column {
            SimpleComposable()
            SimpleComposable()
        }
    }
}

@Preview
@Composable
fun SimpleList() {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        repeat(50) {
            CellItem()
        }
    }
}
