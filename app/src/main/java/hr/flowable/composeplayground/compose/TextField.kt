package hr.flowable.composeplayground.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextField() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        NewMessageInput()
    }
}


@ExperimentalComposeUiApi
@Composable
private fun NewMessageInput() {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.background(MaterialTheme.colors.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFF6F6F6),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.DarkGray
                    ),
                    modifier = Modifier.weight(1f),
                    value = text,
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text(
                            text = "Send a message...",
                            color = Color(0xFF777777)
                        )
                    },
                    onValueChange = { text = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = Color(0xFFF6F6F6),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    onClick = { Log.d("žžž", "lcicked ahsdlk") }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
