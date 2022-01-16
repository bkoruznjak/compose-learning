package hr.flowable.composeplayground.compose

import androidx.compose.animation.core.AnimationConstants.DefaultDurationMillis
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

val data = listOf("Pounds", "Kilograms", "Stones","Babu","gabu","sahiibiri","lolo","jahsdsda")

private const val NO_INDEX = -1

data class MeasurementState(
  val selectedIndex: Int,
  val previousIndex: Int = NO_INDEX
)

@Composable
fun HorizontalRowPicker() {
  var state by remember {
    mutableStateOf(MeasurementState(5))
  }

  CenteredSelectionMenu(state.selectedIndex, state.previousIndex) {
    state = state.copy(
      selectedIndex = it,
      previousIndex = state.selectedIndex
    )
  }
}

@Composable
private fun CenteredSelectionMenu(
  selectedIndex: Int,
  previousIndex: Int,
  onItemClick: (Int) -> Unit
) {
  val density = LocalDensity.current

  val screenWidthInInPx = with(density) {
    LocalConfiguration.current.screenWidthDp.dp.toPx()
  }

  val halfScreenWidth = screenWidthInInPx * 0.5f

  val scrollState = rememberScrollState()

  val itemOffsetsFromParentCenterInPx by remember {
    mutableStateOf(
      mutableMapOf<Int, Float?>()
    )
  }

  val floatOffset by animateFloatAsState(
    animationSpec = tween(
      durationMillis = if (previousIndex == NO_INDEX) 0 else DefaultDurationMillis
    ),
    targetValue = when {
      itemOffsetsFromParentCenterInPx[selectedIndex] != null -> itemOffsetsFromParentCenterInPx[selectedIndex]!!
      else                                                   -> 0f
    }
  )

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .horizontalScroll(scrollState,false) //enable scroll once you figure out how to update offsets on drag
      .offset(x = floatOffset.dp),
    horizontalArrangement = Arrangement.Center
  ) {
    data.forEachIndexed { index, data ->
      Cell(
        text = data,
        isSelected = index == selectedIndex,
        onItemClick = { onItemClick(index) },
        modifier = Modifier.onGloballyPositioned {
          if (itemOffsetsFromParentCenterInPx[index] == null) {
            //Item center offsets from window center are calculated once and remembered
            val positionOnScreen = it.positionInWindow()
            val halfItemWidth = it.size.width * 0.5f
            val offsetInPixels = halfScreenWidth - (positionOnScreen.x + halfItemWidth)
            itemOffsetsFromParentCenterInPx[index] = with(density) {
              offsetInPixels.toDp().value
            }
          }
        })
    }

  }
}

@Composable
private fun Cell(
  text: String,
  isSelected: Boolean,
  onItemClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Text(text, modifier = modifier
    .clickable(
      indication = null,
      interactionSource = remember { MutableInteractionSource() }
    ) { onItemClick() }
    .padding(8.dp)
    .background(color = if (isSelected) Color.Red.copy(0.5f) else Color.LightGray)
    .padding(8.dp))
}