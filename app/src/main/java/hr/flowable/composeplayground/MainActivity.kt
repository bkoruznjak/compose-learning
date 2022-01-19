package hr.flowable.composeplayground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.unit.ExperimentalUnitApi
import hr.flowable.composeplayground.compose.AnimatedVisibilityLazyColumnDemo
import hr.flowable.composeplayground.compose.SmileyPicker

class MainActivity : AppCompatActivity() {

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmileyPicker()
        }
    }
}
