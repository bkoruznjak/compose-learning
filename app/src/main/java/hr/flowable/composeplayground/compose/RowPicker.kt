package hr.flowable.composeplayground.compose


import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun RowPicker() {

  val offsets = remember {
    OffsetState(
      mutableMapOf(
        0 to 0,
        1 to 0,
        2 to 0
      )
    )
  }

  val currentDensity = LocalDensity.current

  val xOffsetState = remember {
    mutableStateOf(
      with(currentDensity) {
        offsets.elementOffsets[1]?.toDp()
      } ?: 0.dp
    )
  }

  var dragOffset by remember { mutableStateOf(0.dp) }

  val offsetAnimation: Dp by animateDpAsState(xOffsetState.value + dragOffset)

  Box(modifier = Modifier
    .fillMaxWidth()
    .pointerInput(Unit) {
      detectHorizontalDragGestures(
        onDragEnd = {
val combined = xOffsetState.value + dragOffset
          val combinedInPixels = with(currentDensity){
            combined.toPx()
          }

          val closestIndex = offsets.elementOffsets.map {
            val sum =   it.value - combinedInPixels
            it.key to sum
          }.minByOrNull { abs(it.second) }?.first ?: 0
          dragOffset = 0.dp
          xOffsetState.value = with(currentDensity){
            offsets.elementOffsets[closestIndex]?.toDp()
          } ?: 0.dp

                    },
        onDragCancel = { Log.d("žžž", "drag cancelled") }
      ) { change, dragAmount ->
        change.consumeAllChanges()
        val dragAmountInDp = with(currentDensity) { dragAmount.toDp() }
        val temp = dragOffset + dragAmountInDp
        if(temp > -offsets.totalDragAmount && temp < offsets.totalDragAmount){
          dragOffset += dragAmountInDp
        }
      }
    }


  ) {

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .offset(x = offsetAnimation), horizontalArrangement = Arrangement.Center
    ) {

      MeasurableRow(offsets,currentDensity) {
        TextBox("grams", modifier = Modifier.clickable {
          dragOffset = 0.dp
          xOffsetState.value = with(currentDensity) {
            offsets.elementOffsets[0]?.toDp()
          } ?: 0.dp
        })

        TextBox("kilograms", modifier = Modifier.clickable {
          dragOffset = 0.dp
          xOffsetState.value = with(currentDensity) {
            offsets.elementOffsets[1]?.toDp()
          } ?: 0.dp
        })

        TextBox("stone", modifier = Modifier.clickable {
          dragOffset = 0.dp
          xOffsetState.value = with(currentDensity) {
            offsets.elementOffsets[2]?.toDp()
          } ?: 0.dp
        })
      }
    }
  }


}

class OffsetState(
  val elementOffsets: MutableMap<Int, Int>,
  var totalDragAmount: Dp = 0.dp
)

@Composable
private fun TestBlock() {
  var sliderState2 by remember { mutableStateOf(20f) }


  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {

      TextBox("grams")

      TextBox("kilograms")

      TextBox("stone")
    }
    Spacer(modifier = Modifier.height(24.dp))

    Slider(value = sliderState2, modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
      valueRange = 0f..100f,
      steps = 5,
      onValueChange = { newValue ->
        sliderState2 = newValue
      }
    )
  }
}

@Composable
private fun TextBox(text: String, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .padding(8.dp)
      .background(Color.LightGray)
      .padding(12.dp)
  ) {
    Text(text)
  }
}

@Composable
private fun MeasurableRow(
  offsetState: OffsetState,
  currentDensity: Density,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Layout(modifier = modifier, content = content) { measureables, constraints ->

    var totalWidth = 0
    var height = 0
    val startOffsets = mutableListOf<Int>()

    val placeables = measureables.mapIndexed { index, measurable ->
      val placeable = measurable.measure(constraints)
      startOffsets.add(index = index, element = totalWidth)
      totalWidth += placeable.width
      if (placeable.height > height) {
        height = placeable.height
      }
      placeable
    }

    val center = totalWidth * 0.5
    startOffsets.forEachIndexed { index, value ->

      val placeableHalfWidth = placeables[index].width * 0.5
      val startOffsetToMiddle = value + placeableHalfWidth
      val absoluteDistanceToCenter = center - startOffsetToMiddle
      offsetState.elementOffsets[index] = absoluteDistanceToCenter.toInt()
    }
    offsetState.totalDragAmount = with(currentDensity) {
      center.toInt().toDp()
    }

    layout(height = height, width = totalWidth) {
      placeables.forEachIndexed { index, placeable ->
        placeable.placeRelative(startOffsets[index], 0)
      }
    }

  }

}