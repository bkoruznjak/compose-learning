package hr.flowable.composeplayground.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.flowable.composeplayground.compose.ui.theme.ComposePlaygroundTheme

/**
 * Following the tutorial from here:
 * https://developer.android.com/codelabs/jetpack-compose-basics#0
 */

@Composable
private fun Greeting(name: String) {
    Surface {
        Text(
            text = "Hello $name",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CellWithExpandingButton(name: String) {

    var expanded = rememberSaveable { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
        color = MaterialTheme.colors.primary
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        // negative padding will crash the app this makes sure its never negative
                        // spring animation used above may cause padding to be less than 0
                        bottom = extraPadding.coerceAtLeast(
                            0.dp
                        )
                    )
            ) {
                Text(
                    text = "Weclome back"
                )

                Text(
                    text = "Mr. $name", style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            IconButton(onClick = {
                expanded.value = !expanded.value
            }) {
                Image(
                    imageVector = if (expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded.value) "Show less" else "Shoe more"
                )
            }
        }

    }
}

@Composable
fun ExpandableButtonsList(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            CellWithExpandingButton(name = name)
        }
    }
}

@Composable
fun OnBoardingScreen(onClick: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the basics codelab", modifier = Modifier.padding(top = 16.dp))
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onClick
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true, widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES
)
fun JetpackComposeBasics() {

    // rememeberSaveable will hold the state on configuration change while remember will not
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    ComposePlaygroundTheme {
        if (shouldShowOnBoarding) {
            OnBoardingScreen { shouldShowOnBoarding = false }
        } else {
            ExpandableButtonsList()
        }

    }
}
