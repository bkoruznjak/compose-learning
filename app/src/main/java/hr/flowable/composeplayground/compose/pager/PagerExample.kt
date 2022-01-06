package hr.flowable.composeplayground.compose.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PagerExample() {
  Column(modifier = Modifier.fillMaxSize()) {
    val items =
      remember { listOf(Item("pounds"), Item("kilograms"), Item("stones"), Item("liters")) }
    val pagerState: PagerState = run {
      remember { PagerState(1, 0, items.size - 1) }
    }
    val selectedPage = remember { mutableStateOf(2) }

    PrepareSecondPager(pagerState = pagerState, items = items, selectedPage = selectedPage)
  }
}

@Composable
private fun ColumnScope.PrepareSecondPager(
  pagerState: PagerState,
  items: List<Item>,
  selectedPage: MutableState<Int>
) {
  Pager(state = pagerState, modifier = Modifier.height(200.dp)) {
    val item = items[commingPage]
    selectedPage.value = pagerState.currentPage
    TextBox(item.title)
  }
  Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun TextBox(text: String) {
  Box(
    modifier = Modifier
      .padding(8.dp)
      .background(Color.LightGray)
      .padding(12.dp)
  ) {
    Text(text)
  }
}