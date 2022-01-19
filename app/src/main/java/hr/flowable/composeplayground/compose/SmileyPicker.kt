package hr.flowable.composeplayground.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun SmileyPicker() {

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(1.76f)
      .background(color = Color(0xfff6f4ee))
  ) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current

    var selectedEmoji by remember { mutableStateOf<Emoji?>(null) }
    var selectedEmojiPage by remember { mutableStateOf(0) }

    val emojiSize = remember {
      with(density) {
        (screenWidth * 0.096f).dp.toSp()
      }
    }
    val emojiButtonSize = remember {
      with(density) {
        emojiSize.toDp() + 24f.dp
      }
    }

    val unselectedAlpha = 0.1f

    val itemsPerPage = 15
    val itemsPerRow = 5

    val values = Emoji.values()
    val firstRow = values
      .drop(if (selectedEmojiPage == 0) 0 else itemsPerPage)
      .take(itemsPerRow)

    val secondRow = values
      .drop(
        (if (selectedEmojiPage == 0) 0 else itemsPerPage) + itemsPerRow
      ).take(itemsPerRow)

    val thirdRow = values.drop(
      (if (selectedEmojiPage == 0) 0 else itemsPerPage) + itemsPerRow + itemsPerRow
    ).take(itemsPerRow)

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
      firstRow.forEach {
        EmojiButton(
          isSelected = selectedEmoji == it,
          emoji = it,
          emojiSize = emojiSize,
          size = emojiButtonSize
        ) {
          selectedEmoji = it
        }
      }
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
      secondRow.forEach {
        EmojiButton(
          isSelected = selectedEmoji == it,
          emoji = it,
          emojiSize = emojiSize,
          size = emojiButtonSize
        ) {
          selectedEmoji = it
        }
      }
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
      thirdRow.forEach {
        EmojiButton(
          isSelected = selectedEmoji == it,
          emoji = it,
          emojiSize = emojiSize,
          size = emojiButtonSize
        ) {
          selectedEmoji = it
        }
      }
    }
    Spacer(modifier = Modifier.weight(1f))

    // Emoji page navigation panel
    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(22.dp)
          .clickable(enabled = selectedEmojiPage == 1) {
            Log.d("žžž", "go back pressed")
            selectedEmojiPage = 0
          }) {
        Icon(
          imageVector = Icons.Default.ArrowBackIos,
          contentDescription = null,
          tint = Color.Black.copy(alpha = if (selectedEmojiPage == 0) unselectedAlpha else 1f)
        )
      }
      Spacer(modifier = Modifier.width(32.dp))
      Box(
        modifier = Modifier
          .size(8.dp)
          .background(
            color = Color.Black.copy(alpha = if (selectedEmojiPage == 0) 1f else unselectedAlpha),
            shape = CircleShape
          )
      )
      Spacer(modifier = Modifier.width(8.dp))
      Box(
        modifier = Modifier
          .size(8.dp)
          .background(
            color = Color.Black.copy(alpha = if (selectedEmojiPage == 0) unselectedAlpha else 1f),
            shape = CircleShape
          )
      )
      Spacer(modifier = Modifier.width(32.dp))
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(22.dp)
          .clickable(enabled = selectedEmojiPage == 0) {
            Log.d("žžž", "go forward pressed")
            selectedEmojiPage = 1
          }) {
        Icon(
          imageVector = Icons.Default.ArrowForwardIos,
          contentDescription = null,
          tint = Color.Black.copy(alpha = if (selectedEmojiPage == 0) 1f else unselectedAlpha)
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))
  }
}

@Composable
private fun EmojiButton(
  isSelected: Boolean,
  emoji: Emoji,
  emojiSize: TextUnit,
  size: Dp,
  onClick: () -> Unit
) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .size(size)
      .background(color = if (isSelected) Color.LightGray else Color(0xfff6f4ee))
      .clickable { onClick() }
  ) {
    Text("$emoji", fontSize = emojiSize)
  }
}

enum class Emoji(vararg utf16Encodings: Int) {

  GRINNING_FACE_WITH_SMILING_EYES(0xD83D, 0xDE04),
  GRINNING_FACE_WITH_SWEAT(0xD83D, 0xDE05),
  SMILING_FACE_WITH_HEART_EYES(0xD83D, 0xDE0D),
  ZANY_FACE(0xD83E, 0xDD2A),
  THINKING_FACE(0xD83E, 0xDD14),
  UNAMUSED_FACE(0xD83D, 0xDE12),
  SLEEPING_FACE(0xD83D, 0xDE34),
  PENSIVE_FACE(0xD83D, 0xDE14),
  EXPLODING_HEAD(0xD83E, 0xDD2F),
  LOUDLY_CRYING_FACE(0xD83D, 0xDE2D),
  PIZZA(0xD83C, 0xDF55),
  HAMBURGER(0xD83C, 0xDF54),
  TACO(0xD83C, 0xDF2E),
  COOKIE(0xD83C, 0xDF6A),
  POULTRY_LEG(0xD83C, 0xDF57),
  GREEN_SALAD(0xD83E, 0xDD57),
  RED_APPLE(0xD83C, 0xDF4E),
  BANANA(0xD83C, 0xDF4C),
  GRAPES(0xD83C, 0xDF47),
  CARROT(0xD83E, 0xDD55),
  CLAPPING_HANDS(0xD83D, 0xDC4F),
  FIRE(0xD83D, 0xDD25),
  PARTY_POPPER(0xD83C, 0xDF89),
  WOMAN_DANCING(0xD83D, 0xDC83),
  PILE_OF_POO(0xD83D, 0xDCA9),
  HUNDRED_POINTS(0xD83D, 0xDCAF),
  FLEXED_BICEPS(0xD83D, 0xDCAA),
  FOLDED_HANDS(0xD83D, 0xDE4F),
  MAN_LIFTING_WEIGHTS(0xD83C, 0xDFCB),
  BRAIN(0x0001F9E0);

  private val encodings: IntArray = utf16Encodings

  override fun toString(): String {
    val stringBuilder = StringBuilder()
    encodings.forEach {
      stringBuilder.append(Character.toChars(it))
    }

    return stringBuilder.toString()
  }
}
