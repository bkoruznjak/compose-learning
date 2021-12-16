package hr.flowable.composeplayground.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

/**
 * https://developer.android.com/codelabs/jetpack-compose-layouts?skip_cache=true#9
 *
 * Other useful tricks:
 *
 * #1
 * Text("Text", Modifier.constrainAs(text) {
 *      top.linkTo(button1.bottom, margin = 16.dp)
 *      centerAround(button1.end)
 * })
 *
 * #2
 * val guideline = createGuidelineFromStart(fraction = 0.5f)
 *
 * #3
 * Modifier.constrainAs(text) {
 *      linkTo(guideline, parent.end)
 *      width = Dimension.preferredWrapContent
 * }
 *
 * or with
 * width = Dimension.preferredWrapContent.atLeast(100.dp)
 */
@Preview
@Composable
fun ComposableConstrainedButtonWithText() {

    ConstraintLayout(modifier = Modifier.background(Color.Cyan)) {

        val (button, text) = createRefs()


        Button(onClick = { Log.d("žžž", "something") }, modifier = Modifier.constrainAs(
            button
        ) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }) {
            Text("clickme")
        }

        Text("This is some label", modifier = Modifier.constrainAs(text) {
            top.linkTo(button.bottom)
            centerHorizontallyTo(button)
            bottom.linkTo(parent.bottom, margin = 16.dp)
        })

    }


}
